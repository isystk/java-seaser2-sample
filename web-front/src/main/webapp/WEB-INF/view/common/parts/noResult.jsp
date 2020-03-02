<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:if test="${pager == null}">
	<p class="none">${message:string(AppMessageNames.ERRORS_INPUTCONDITION.key)}</p>
</c:if>
<c:if test="${pager.countOver}">
	<p class="none">${message:string(AppMessageNames.ERRORS_COUNTOVER.key)}</p>
</c:if>
<c:if test="${pager.countZezo}">
	<p class="none">
	<c:choose>
		<c:when test="${param.target == 'wedding'}">
			${message:string1(AppMessageNames.ERRORS_SEARCH_NODATA.key, "式場")}
		</c:when>
		<c:when test="${param.target == 'fair'}">
			${message:string1(AppMessageNames.ERRORS_SEARCH_NODATA.key, "ブライダルフェア")}
		</c:when>
		<c:when test="${param.target == 'plan'}">
			${message:string1(AppMessageNames.ERRORS_SEARCH_NODATA.key, "プラン")}
		</c:when>
		<c:when test="${param.target == 'latestplan'}">
			${message:string1(AppMessageNames.ERRORS_SEARCH_NODATA.key, "直前オトクプラン")}
		</c:when>
		<c:when test="${param.target == 'people'}">
			${message:string1(AppMessageNames.ERRORS_SEARCH_NODATA.key, "体験レポート")}
		</c:when>
		<c:when test="${param.target == 'banquet'}">
			${message:string1(AppMessageNames.ERRORS_SEARCH_NODATA.key, "披露宴会場")}
		</c:when>
		<c:when test="${param.target == 'agent'}">
			${message:string1(AppMessageNames.ERRORS_SEARCH_NODATA.key, "手配会社")}
		</c:when>
		<c:when test="${param.target == 'hall'}">
			${message:string1(AppMessageNames.ERRORS_SEARCH_NODATA.key, "チャペル")}
		</c:when>
		<c:otherwise>
			${message:string(AppMessageNames.ERRORS_NODATA.key)}
		</c:otherwise>
	</c:choose>
	</p>
</c:if>
