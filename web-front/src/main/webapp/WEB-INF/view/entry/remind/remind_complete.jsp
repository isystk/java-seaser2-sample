<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">

<tiles:put name="title" value="新パスワード設定完了" type="String" />
<tiles:put name="content" type="string">

<div class="content">
	<main>
	    <article class="detail">
	        <div class="entry-header">
	          <h1 class="entry-title">パスワードリマインダ　新パスワード設定完了</h1>
			</div>
        	<div class="entry-content">

				<p>新しいパスワードの設定が完了しました。<br />今後はこの新しいパスワードでログインしてください。</p>

				<%-- ▼各ページへの動線▼ --%>
				<div class="form">
					<ul class="lists links">
						<li><s:link href="/login/">新しいパスワードでログインする</s:link></li>
						<li><a href="/">サイトトップに戻る</a></li>
					</ul>
				</div>
				<%-- ▲各ページへの動線▲ --%>

			</div>
	    </article>
	</main>
</div>
</tiles:put>
</tiles:insert>
