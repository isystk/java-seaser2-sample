<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">

<tiles:put name="title" value="確認メール送信完了" type="String" />
<tiles:put name="content" type="string">

<div class="content">
	<main>
	    <article class="detail">
	        <div class="entry-header">
	          <h1 class="entry-title">パスワードリマインダ　確認メール送信完了</h1>
			</div>
        	<div class="entry-content">

				<p>
					メールアドレスにパスワード再設定ページのURLを記載したメールを送信しました。<br />
					メールに記載されているURLをクリックして、新パスワードを登録してください。
				</p>
				<p class="sub">
					※isystk.comからのメールが届かない場合は、お手数ですが再度お手続きください。
				</p>

			</div>
	    </article>
	</main>
</div>
</tiles:put>
</tiles:insert>
