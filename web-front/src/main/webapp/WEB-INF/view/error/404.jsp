<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">

<tiles:put name="title" value="404 Not Found" type="String" />
<tiles:put name="content" type="string">

<div class="content">
	<main>
	    <article class="detail">
	        <div class="entry-header">
	          <h1 class="entry-title">404 Not Found</h1>
			</div>
        	<div class="entry-content">

				<p><a href="https://blog.isystk.com/contact/">サイトに関するお問合せ</a></p>
				<p><a href="/" class="default">サイトトップに戻る &raquo;</a></p>

				<ul>
					<li>URLをお間違えではないですか？</li>
					<li>メールでお送りしたURLが改行されて、正しくリンクされていないこともあります。</li>
				</ul>
			</div>
	    </article>
	</main>
</div>
</tiles:put>
</tiles:insert>
