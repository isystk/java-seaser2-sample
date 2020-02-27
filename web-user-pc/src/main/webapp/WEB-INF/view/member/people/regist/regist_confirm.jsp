<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">

<tiles:put name="localScript" type="string" >
	<script src="${url:currentJsDir()}../common.js" type="text/javascript" ></script>
</tiles:put>

<tiles:put name="title" value="投稿内容確認" type="String" />
<tiles:put name="content" type="string">

<div class="content">
	<main data-preview="${isPreview}">
	    <article class="detail">

	        <div class="entry-header">
	          <h1 class="entry-title">投稿内容確認</h1>
			</div>
        	<div class="entry-content">

				<p>以下の内容で投稿します。問題なければ「投稿する」ボタンを押してください。</p>

				<s:form method="post" enctype="multipart/form-data" styleClass="couplePostForm">

					<html:hidden property="postId"/>
					<html:hidden property="title"/>
					<html:hidden property="text"/>
					<c:forEach var="e" items="${postImageId}" varStatus="s">
						<html:hidden property="postImageId" value="${e}"/>
					</c:forEach>
					<c:forEach var="e" items="${postTagId}" varStatus="s">
						<html:hidden property="postTagId" value="${e}"/>
					</c:forEach>
					<html:hidden property="version" />

					<%-- ↓↓フォーム --%>
					<div class="form" id="inputAddressArea">
						<%-- ↓↓↓入力 --%>
	            		<table class="formTable">
						<tbody>
							<tr>
								<th colspan="2"><span class="mark">タイトル</span><span class="required">【必須】</span></th>
								<td>
									<p>${f:h(title)}</p>
								</td>
							</tr>
							<tr>
								<th colspan="2"><span class="mark">本文</span><span class="required">【必須】</span></th>
								<td>
									<p>${f:h(text)}</p>
								</td>
							</tr>
							<tr>
								<th colspan="2"><span class="mark">写真</span><span class="required">【必須】</span></th>
								<td>
								  	<ul class="selectedPhotoList">
										<c:forEach var="e2" items="${postImageId}" varStatus="s2">
										    <li><a href="#">
										    	<cmn:imageThumb imageId="${e2}" imageType="${ImageType.SQUARE_175}" />
										    </a></li>
										</c:forEach>
									</ul>
								</td>
							</tr>
							<tr>
								<th colspan="2"><span class="mark">ハッシュタグ</span></th>
								<td>
								  	<ul class="HotHashTag_area_list select-tag">
										<c:forEach var="e" items="${detailDto.postTagList}" varStatus="s">
											<li><a href="#" class="btn_hotHashTag">${f:h(e.name)}</a></li>
										</c:forEach>
									</ul>
								</td>
							</tr>
						</tbody>
					</table>

					<p class="btnArea">
	      				<input type="submit" name="backFrom" value="戻る">&nbsp;
	      				<input type="submit" name="update" value="投稿する">
					</p>

				</div>

			</s:form>
		</article>
	</main>
</div>

</tiles:put>
</tiles:insert>

<%-- 一時保存メッセージ --%>
<c:if test="${draftSaveFlg}">
	<p class="draft-save-message display-none">下書き保存が完了しました。</p>
</c:if>

