<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">

<tiles:put name="localScript" type="string" >
	<script src="${url:currentJsDir()}../common.js" type="text/javascript" ></script>
</tiles:put>

<tiles:put name="title" value="投稿完了" type="String" />
<tiles:put name="content" type="string">

<div class="content">
	<main>
	    <article class="detail">

	        <div class="entry-header">
	          <h1 class="entry-title">投稿完了</h1>
			</div>
        	<div class="entry-content">

				<p>登録が完了しました。<br>投稿ありがとうございました。</p>

			</div>

		</article>
	</main>
</div>

</tiles:put>
</tiles:insert>
