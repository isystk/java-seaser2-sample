<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%--
	ページングを１５、３０、４５件で選択する場合
	<jsp:param name="quarter" value="true"/>
	クッキーでの管理をシない場合
	<jsp:param name="nocookie" value="true"/>
	検索画面などで、UrlRewriteを利用する場合
	<jsp:param name="rewriteurl" value="true"/>
 --%>
	
	
<!-- ▽ページング▽ -->
<div class="paging js-paging">
<p class="counter"><em>${f:h(pager.count)}</em>&nbsp;件中&nbsp;&nbsp;<em class="paging_fromto">${f:h(pager.startCount)}～${f:h(pager.endCount)}</em>&nbsp;件を表示
&nbsp;｜&nbsp;表示件数：
	<c:choose>
		<c:when test="${param.quarter}">
			<select class="js_pageLimit" data-rewriteurl="${param.rewriteurl}"  data-default="${PagingLimitQuarterSelectOption.DEFAULT.code}" data-nocookie="${param.nocookie}">
			<c:forEach var="pageLimit" items="${PagingLimitQuarterSelectOption.ITEMS}">
				<option value="${pageLimit.code}" ${(maxCount eq pageLimit.code) ? "selected=\"selected\"" : ""}><bean:write name="pageLimit" property="value"/></option>
			</c:forEach>
			</select>
		</c:when>
		<c:otherwise>
			<select class="js_pageLimit" data-rewriteurl="${param.rewriteurl}"  data-default="${PagingLimitSelectOption.DEFAULT.code}" data-nocookie="${param.nocookie}">
				<c:choose>
					<c:when  test="${!param.viewAllLimitOption}">
						<c:forEach var="pageLimit" items="${PagingLimitSelectOption.ITEMS}">
							<option value="${pageLimit.code}" ${(maxCount eq pageLimit.code) ? "selected=\"selected\"" : ""}><bean:write name="pageLimit" property="value"/></option>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach var="pageLimit" items="${PagingLimitSelectOption.ITEMS_ALL}">
							<option value="${pageLimit.code}" ${(maxCount eq pageLimit.code) ? "selected=\"selected\"" : ""}><bean:write name="pageLimit" property="value"/></option>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</select>
		</c:otherwise>
	</c:choose>
</p>

<c:if test="${pager.pageCount > 1 && !param.noPageCount}">
	<ul>
	<c:if test="${pager.hasPrev}">
		<li class="back"><a class="js_paging" data-page="${pager.current - 1}" data-rewriteurl="${param.rewriteurl}" href="#"><img class="imgover" src="/img/btn_paging_back.png" alt="前へ" width="55" height="24" /></a></li>
	</c:if>
	<c:forEach var="e" varStatus="s" begin="${pager.tipStartLiquid}"
		end="${pager.tipEndLiquid}">
		<c:choose>
			<c:when test="${e != pager.current}">
				<li class="num"><a class="js_paging" data-page="${e}" data-rewriteurl="${param.rewriteurl}" href="#">${f:h(e)}</a></li>
			</c:when>
			<c:otherwise>
				<li class="focus"><span>${f:h(e)}</span></li>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<c:if test="${pager.hasNext}">
		<li class="next "><a class="js_paging" data-page="${pager.current + 1}" data-rewriteurl="${param.rewriteurl}" href="#"><img class="imgover" src="/img/btn_paging_next.png" alt="次へ" width="55" height="24" /></a></li>
	</c:if>
	</ul>
</c:if>

<!-- /.paging --></div>
<!-- △ページング△ -->
	
