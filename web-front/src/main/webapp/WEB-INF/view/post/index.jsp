<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">

<tiles:put name="localScript" type="string" >
	<script src="${url:currentJsDir()}index.js" type="text/javascript" ></script>
</tiles:put>

<tiles:put name="title" value="投稿詳細" type="String" />
<tiles:put name="content" type="string">

<div class="content">
	<main >

			投稿詳細
    </main>
</div>
</tiles:put>
</tiles:insert>
