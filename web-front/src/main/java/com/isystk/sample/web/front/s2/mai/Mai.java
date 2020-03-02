package com.isystk.sample.web.front.s2.mai;

import com.isystk.sample.web.front.s2.mai.dto.EntryRegistMailDto;
import com.isystk.sample.web.front.s2.mai.dto.PasswordRemindMailDto;

/**
 * Maiインターフェース
 *
 * @author iseyoshitaka
 *
 */
public interface Mai {

	/** 会員仮登録完了メール */
	void entryRegistF01(EntryRegistMailDto mail);

	/** 会員本登録完了メール */
	void entryRegistF02(EntryRegistMailDto mail);

	/** パスワードリマインド(ワンタイムURL記載メール) */
	void passwordRemindF03(PasswordRemindMailDto mail);

}
