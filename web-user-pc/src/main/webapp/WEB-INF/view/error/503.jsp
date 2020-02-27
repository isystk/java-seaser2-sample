<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">

<tiles:put name="title" value="503 Server Unavailable" type="String" />
<tiles:put name="content" type="string">

<div class="content">
	<main>
	    <article class="detail ">
	        <div class="entry-header">
	          <h1 class="entry-title">503 Server Unavailable</h1>
			</div>
        	<div class="entry-content">

				<p>
					<logic:present name="ERROR.MESSAGE" >
						<bean:write name="ERROR.MESSAGE" />
					</logic:present>
					<logic:notPresent name="ERROR.MESSAGE" >
						サーバが込みあっているか、メンテナンス中の可能性があります。<br />もう少し時間を置いてから再度お試しください。
					</logic:notPresent>
				</p>

				<p><a href="https://blog.isystk.com/contact/">サイトに関するお問合せ</a></p>
				<p><a href="/" class="default">サイトトップに戻る &raquo;</a></p>

				<jsp:include page="/WEB-INF/view/error/parts/detail.jsp" />

			</div>
	    </article>
	</main>
</div>
</tiles:put>
</tiles:insert>
