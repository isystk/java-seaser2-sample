<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:if test="${!cmn:isEnvProduct()}">
<p class="headerSt">
<c:choose>
	<c:when test="${cmn:isEnvSt()}">ステージング環境</c:when>
	<c:when test="${cmn:isEnvUt()}">ローカル環境</c:when>
</c:choose>
Server:${ServiceCountForDebugActionInterceptor_TIME}(ms),
<span id="serviceCallCount" data-count="${ServiceCountForDebugActionInterceptor_COUNT}">ServiceCall:${ServiceCountForDebugActionInterceptor_COUNT}回</span>,
<span style="float: right;" onclick="javascript:$(this).closest('p').remove();">非表示</span>
</p>
</c:if>

<header class="header">
  <div class="logo">ギャラリー｜サンプルHTML</div>
    <div id="menu-btn">
      <a href="#"><i class="fas fa-bars"></i></a>
    </div>
	<jsp:include page="/WEB-INF/view/common/parts/freeword.jsp" />
</header>

<div id="pc-menu">
  <nav>
    <ul>
      <li><a href="${url:absolute(Url.userpcTop)}">トップ</a></li>
      <li class="page_item page-item-20"><a href="${url:absolute(Url.userpcMember)}">マイページ</a></li>
      <li class="page_item page-item-19"><a href="${url:absolute(Url.userpcMemberPeopleRegist)}">投稿する</a></li>
    </ul>
  </nav>
</div>