<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">

<tiles:put name="localScript" type="string" >
	<script src="${url:currentJsDir()}../common.js" type="text/javascript" ></script>
	<script src="${url:currentJsDir()}../input.js" type="text/javascript" ></script>
</tiles:put>

<tiles:put name="title" value="投稿フォーム" type="String" />
<tiles:put name="content" type="string">

<div class="content">
	<main data-preview="${isPreview}">
	    <article class="detail">

	        <div class="entry-header">
	          <h1 class="entry-title">投稿フォーム</h1>
			</div>
        	<div class="entry-content">

				<p>投稿する内容を下記に記入してください。</p>

				<s:form method="post" enctype="multipart/form-data" styleClass="couplePostForm">

					<html:hidden property="postId"/>
					<html:hidden property="version" />

					<%-- ↓↓フォーム --%>
					<div class="form" id="inputAddressArea">
						<%-- ↓↓↓入力 --%>
	            		<table class="formTable">
						<tbody>
							<tr>
								<th colspan="2"><span class="mark">タイトル</span><span class="required">【必須】</span></th>
								<td>
									<p><html:text property="title" styleClass="text size_m realtimecheck" errorStyleClass="text size_m error realtimecheck" styleId="title" maxlength="${MaxLength.T_POST_TITLE}"/></p>
									<div id="titleErrMessage">
									<html:errors header="false" footer="false" property="title" />
									</div>
								</td>
							</tr>
							<tr>
								<th colspan="2"><span class="mark">本文</span><span class="required">【必須】</span></th>
								<td>
								  	<div>
										<html:textarea property="text" cols="50" rows="6"  styleClass="formTextarea realtimecheck"  errorStyleClass="formTextarea realtimecheck error" />
										<p class="noticetxt"><span class="count"><label class="js-textCounter">${MaxLength.T_POST_TEXT}</label></span>文字&nbsp;/&nbsp;${MaxLength.T_POST_TEXT}文字</p>
									</div>
									<div id="textErrMessage">
									<html:errors header="false" footer="false" property="text" />
									</div>
								</td>
							</tr>
							<tr>
								<th colspan="2"><span class="mark">写真</span><span class="required">【必須】</span></th>
								<td>
								  	<label class="btn_formSelectImage"><input type="file" name="imageFile" class="js-upload-postImage-btn"  multiple="multiple" ></label>
									<ul class="selectedPhotoList">
										<c:forEach var="e2" items="${postImageId}" varStatus="s2">
										    <li><a href="#" class="close">
										    	<cmn:imageThumb imageId="${e2}" imageType="${ImageType.SQUARE_175}" />
												<html:hidden property="postImageId" value="${e2}" />
										    </a></li>
										</c:forEach>
									</ul>
									<div id="postImageErrMessage">
										<html:errors header="false" footer="false" property="postImageId" />
									</div>
								</td>
							</tr>
							<tr>
								<th colspan="2"><span class="mark">ハッシュタグ</span></th>
								<td>
								  	<button type="button" class="btn_formSelect js-tag-select" data-category-prefix="addCategoryList[${s.index}].categoryTagId" data-category-id="${addCategoryList.peopleCategoryId}">選択</button>
								  	<ul class="HotHashTag_area_list select-tag">
										<c:forEach var="e" items="${detailDto.postTagList}" varStatus="s">
											<html:hidden property="postTagId" value="${f:h(e.postTagId)}" />
											<li><a href="#" class="btn_hotHashTag">${f:h(e.name)}</a></li>
										</c:forEach>
									</ul>
									<div id="postTagIdErrMessage">
									<html:errors header="false" footer="false" property="postTagId" />
									</div>
								</td>
							</tr>
						</tbody>
					</table>

      				<p class="btnArea"><input type="submit" name="confirm" value="確認画面へ"></p>

				</div>

			</s:form>
		</article>
	</main>
</div>

</tiles:put>
</tiles:insert>
