package com.isystk.sample.batch.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.seasar.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.google.inject.internal.Lists;
import com.isystk.sample.common.config.AppConfigNames;
import com.isystk.sample.common.config.Config;
import com.isystk.sample.common.constants.Prefecture;
import com.isystk.sample.common.s2.entity.MPostTag;
import com.isystk.sample.common.s2.entity.TPost;
import com.isystk.sample.common.s2.entity.TPostTag;
import com.isystk.sample.common.s2.entity.TUser;
import com.isystk.sample.common.s2.service.MPostTagService;
import com.isystk.sample.common.s2.service.TPostService;
import com.isystk.sample.common.s2.service.TUserService;
import com.isystk.sample.common.s2.solr.SolrIndexerServer;
import com.isystk.sample.common.s2.solr.dto.PostSearchDto;
import com.isystk.sample.common.s2.solr.logic.PostIndexerLogic;
import com.isystk.sample.common.util.DateUtils;

/**
 * BAT100_インデックス更新
 *
 * @author iseyoshitaka
 *
 */
public class Bat100Logic {

	@Resource
	protected TUserService tUserService;

	@Resource
	protected TPostService tPostService;

	@Resource
	protected MPostTagService mPostTagService;

	@Resource
	protected PostIndexerLogic postIndexerLogic;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	// インデクス全件更新フラグ
	protected Boolean ALL_UPDATE_FLG = true;

	// インデクス更新データを保持するList
	private List<PostSearchDto> postList = new ArrayList<PostSearchDto>();

	/**
	 * インデクス更新処理を実行します
	 */
	public void execute() {

		SolrIndexerServer postServer = null;

		logger.info("バッチ実行日時を取得します。");
		Date batchExecDate = DateUtils.getNow();
		logger.info("バッチ実行日を取得します。");

		logger.info("==========updateIndex start==========");

		try {
			logger.info("更新対象のユーザーIDを取得します。");
			List<Integer> userIdList = findEnableUserIdList(batchExecDate);

			if (userIdList != null && userIdList.size() > 0) {

				idListLogger("→更新対象ユーザー号ID：", userIdList);

				logger.info("serverインスタンスを設定します。（投稿）");
				postServer = new SolrIndexerServer(new HttpSolrServer(
						Config.getString(AppConfigNames.SOLR_MASTER_URL.getKey()) + PostSearchDto.CORE_NAME));

				logger.info("インデクス削除用のクエリを取得します。");
				String deleteIndexQuery = getDeleteIndexQuery(userIdList);

				logger.info("インデクスを削除します。（投稿）query : " + deleteIndexQuery);
				postServer.delete(deleteIndexQuery);

				final int USER_SUB_CNT = 10;
				for (int i = 0; i < userIdList.size(); i += USER_SUB_CNT) {
					// i件～toCnt件の屋号を処理する
					int toCnt = userIdList.size() < i + USER_SUB_CNT ? userIdList.size() : i + USER_SUB_CNT;

					List<TUser> tUserList = tUserService.findUserListByUserId(userIdList.subList(i, toCnt));
					if (tUserList == null || tUserList.size() <= 0) {
						logger.info("更新対象として有効なユーザーがありません。");
						continue;
					}

					logger.info("インデクス更新に必要なパラメータを取得します。");
					setUpdateIndexList(batchExecDate, tUserList);

					logger.info("インデクスを生成します。（投稿）");
					if (postList != null && postList.size() > 0) {
						postServer.update(postList);
					} else {
						logger.info("→更新対象がありません。");
					}

					logger.info("更新完了屋号数：" + toCnt + "件");
				}

				logger.info("インデクスをコミットします。（投稿）");
				postServer.commit();

				// 最適化実行判定
				boolean optimizeFlg = false;
				if (ALL_UPDATE_FLG) {
					// 全件更新なら最適化
					optimizeFlg = true;
				} else {

					// 差分更新であっても、バッチ実行日時が指定時間の5分以内であれば最適化
					String batchExecDateStr = DateUtils.toFormatString(batchExecDate, "HHmm");
					if (StringUtil.isNotEmpty(batchExecDateStr)) {
						Integer batchExecDateInt = Integer.valueOf(batchExecDateStr);

						final Integer[] OPTIMIZE_TIMES = { 0, 600, 1200, 1800 };
						for (Integer optimizeTime : OPTIMIZE_TIMES) {
							if (optimizeTime <= batchExecDateInt && batchExecDateInt <= optimizeTime + 5) {
								optimizeFlg = true;
								break;
							}
						}
					}

				}

				// 最適化の間隔が空くとレプリケーションに失敗し始めたため、必ず最適化するようにしました。
				optimizeFlg = true;
				if (optimizeFlg) {
					logger.info("インデクスを最適化します。（投稿）");
					postServer.optimize();
				} else {
					logger.info("インデクスの最適化はしません。");
				}

			} else if (ALL_UPDATE_FLG) {
				// 全件更新時に有効なユーザーが0件の場合は全削除を行う。
				// ※データ初期化時にしか実行されない想定
				logger.info("serverインスタンスを設定します。（投稿）");
				postServer = new SolrIndexerServer(new HttpSolrServer(
						Config.getString(AppConfigNames.SOLR_MASTER_URL.getKey()) + PostSearchDto.CORE_NAME));

				logger.info("インデクス削除用のクエリを取得します。");
				String deleteIndexQuery = getDeleteIndexQuery(userIdList);

				logger.info("インデクスを削除します。（投稿）query : " + deleteIndexQuery);
				postServer.delete(deleteIndexQuery);

				logger.info("インデクスをコミットします。（投稿）");
				postServer.commit();

				logger.info("インデクスを最適化します。（投稿）");
				postServer.optimize();

			} else {
				// 差分更新かつuserIdListが0件の場合のみ入る処理
				logger.info("→更新対象のユーザーがありません。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("updateIndex error : " + e.toString(), e);
			if (postServer != null) {
				logger.info("インデクスをロールバックします。（投稿）");
				try {
					postServer.rollback();
				} catch (Exception e2) {
					logger.info("serverへの接続自体に失敗しているためスルーします。");
				}
			}
		}
		logger.info("==========updateIndex end==========");
	}

	/**
	 * インデクス更新対象のユーザーID一覧(userIdList)を設定する
	 *
	 * @param batchExecDate
	 *            バッチ更新日時
	 */
	protected List<Integer> findEnableUserIdList(Date batchExecDate) {
		List<Integer> userIdList = tUserService.findEnableUserIdList();
		return userIdList != null ? userIdList : new ArrayList<Integer>();

	}

	/**
	 * インデクス更新対象のデータを削除する
	 *
	 * @param userIdList
	 *            更新対象のユーザーID一覧
	 */
	protected String getDeleteIndexQuery(List<Integer> userIdList) {
		return "*:*";
	}

	/**
	 * インデクス更新対象のデータを削除する
	 *
	 * @param userIdList
	 *            更新対象の屋号ID一覧
	 */
	protected String getDeleteChapelIndexQuery(List<Integer> userIdList) {
		return "*:*";
	}

	/**
	 * 全件・差分の共通処理
	 *
	 * @param batchExecDate
	 * @param tUserList
	 */
	private void setUpdateIndexList(Date batchExecDate, List<TUser> tUserList) {

		List<Integer> userIdList = Lists.newArrayList();

		// ユーザーID毎の共通フリーワードを格納するマップ（各for文の中でaddする
		Map<Integer, List<String>> freewordMap = Maps.newHashMap();
		// ユーザーID毎の投稿情報を格納するマップ
		Map<Integer, List<TPost>> tPostMap = Maps.newHashMap();
		// 投稿タグID毎のタグ情報を格納するマップ
		Map<Integer, MPostTag> mPostTagMap = Maps.newHashMap();

		Map<Integer, Prefecture> mPrefectureMap = new HashMap<Integer, Prefecture>();
		for (Prefecture mPrefecture : Prefecture.ITEMS) {
			mPrefectureMap.put(mPrefecture.getCode(), mPrefecture);
		}

		for (TUser tUser : tUserList) {
			userIdList.add(tUser.userId);
			tPostMap.put(tUser.userId, new ArrayList<TPost>());
			freewordMap.put(tUser.userId, new ArrayList<String>());
		}

		// 投稿タグ情報を取得
		List<MPostTag> mPostTagList = mPostTagService.findEnablePostTagList();
		if (mPostTagList != null && mPostTagList.size() > 0) {
			for (MPostTag mPostTag : mPostTagList) {
				mPostTagMap.put(mPostTag.postTagId, mPostTag);
			}
		}

		// 投稿情報を取得
		List<TPost> tPostList = tPostService.findPostListByUserId(userIdList);
		if (tPostList != null && tPostList.size() > 0) {
			for (TPost tPost : tPostList) {
				tPostMap.get(tPost.userId).add(tPost);

				freewordMap.get(tPost.userId).add(tPost.title);
				freewordMap.get(tPost.userId).add(tPost.text);
				freewordMap.get(tPost.userId).add(tPost.title);

				if (!CollectionUtils.isEmpty(tPost.TPostTagList)) {
					for (TPostTag tPostTag : tPost.TPostTagList) {
						MPostTag mPostTag = mPostTagMap.get(tPostTag.getPostTagId());
						freewordMap.get(tPost.userId).add(mPostTag.getName());
					}
				}
			}
		}

		postList = postIndexerLogic.getIndexDtoList(tUserList, tPostMap, mPostTagMap, mPrefectureMap, freewordMap, batchExecDate);

	}

	/**
	 * id一覧のログを出力します。
	 *
	 * @param title
	 * @param idList
	 */
	protected void idListLogger(String title, List<Integer> idList) {

		if (idList != null && idList.size() > 0) {
			StringBuilder sb = new StringBuilder(title);
			for (Integer id : idList) {
				sb.append(id);
				sb.append(", ");
			}
			logger.info(sb.toString());
		} else {
			logger.info(title + " idListが空です");
		}
	}

}
