<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">

<tiles:put name="title" value="会員登録完了" type="String" />
<tiles:put name="content" type="string">

<div class="content">
	<main>
	    <article class="detail ">
	        <div class="entry-header">
	          <h1 class="entry-title">会員登録完了</h1>
			</div>
        	<div class="entry-content">

				<p>会員登録が完了しました！<br />引き続きお楽しみください。</p>

				<div class="form">
					<ul class="lists links">
						<li><a href="/member/">マイページに移動する</a></li>
						<li><a href="/">サイトトップに戻る</a></li>
					</ul>
				</div>
			</div>
	    </article>
	</main>
</div>
</tiles:put>
</tiles:insert>
