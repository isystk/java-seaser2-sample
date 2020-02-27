<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">

<tiles:put name="title" value="会員仮登録メール送信完了" type="String" />
<tiles:put name="content" type="string">

<div class="content">
	<main>
	    <article class="detail ">
	        <div class="entry-header">
	          <h1 class="entry-title">会員登録メール送信完了</h1>
			</div>
        	<div class="entry-content">

				<p>ご登録いただいたメールアドレスに、会員登録用のメールをお送りしました。<br />メールに書かれているURLをクリックすると会員登録が完了します。</p>
				<p><s:link href="/" styleClass="default">サイトトップに戻る &raquo;</s:link></p>

			</div>
	    </article>
	</main>
</div>
</tiles:put>
</tiles:insert>