<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">

<tiles:put name="title" value="500 Internal Server Error" type="String" />
<tiles:put name="content" type="string">

<div class="content">
	<main>
	    <article class="detail ">
	        <div class="entry-header">
	          <h1 class="entry-title">500 Internal Server Error</h1>
			</div>
        	<div class="entry-content">

				<logic:present name="ERROR.MESSAGE" >
				<p>
					<bean:write name="ERROR.MESSAGE" />
				</p>
				</logic:present>

				<p><a href="https://blog.isystk.com/contact/">サイトに関するお問合せ</a></p>
				<p><a href="/" class="default">サイトトップに戻る &raquo;</a></p>

				<jsp:include page="/WEB-INF/view/error/parts/detail.jsp" />

			</div>
	    </article>
	</main>
</div>
</tiles:put>
</tiles:insert>
