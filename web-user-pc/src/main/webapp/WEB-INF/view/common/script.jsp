<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- JSで使う定数 --%>
<jsp:include page="/WEB-INF/view/common/constants.jsp" />

<c:choose>
<c:when test="${cmn:isEnvUt()}">
<script type="text/javascript" src="${f:url('/js/lib/jquery.js')}"></script>
<script type="text/javascript" src="${f:url('/js/lib/jquery-plugins.js')}"></script>
<script type="text/javascript" src="${f:url('/js/lib/underscore.js')}"></script>
<script type="text/javascript" src="${f:url('/js/lib/common.js')}"></script>
</c:when>
<c:otherwise>
<script type="text/javascript" src="${f:url('/js/all.js')}"></script>
</c:otherwise>
</c:choose>

<%-- 各画面で固有のjsを読み込むことができます。--%>
<tiles:insert attribute="localScript" ignore="true" />
