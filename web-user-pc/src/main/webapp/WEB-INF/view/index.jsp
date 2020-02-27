<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">

<tiles:put name="localScript" type="string" >
	<script src="${url:currentJsDir()}index.js" type="text/javascript" ></script>
</tiles:put>

<tiles:put name="title" value="トップページ" type="String" />
<tiles:put name="content" type="string">

<div class="content">
	<main class="photogallery">

		<c:forEach var="e" items="${searchResultDtoList}">
			<div class="photo">
				<div class="inner">
					<div class="square">
						<c:forEach var="e2" items="${e.postImageIdList}">
							<cmn:imageThumb imageId="${f:h(e2)}" alt="" imageType="${ImageType.SQUARE_170}" styleClass="photo"/>
			    		</c:forEach>
					</div>
			    	<div class="txt">
			      		<p>${f:h(e.title)}&nbsp;<fmt:formatDate value="${e.registTime}" pattern="${Format.DATE_MMDDE}" /></p>
			   			<span>
							<c:forEach var="e2" items="${e.postTagNameList}" varStatus="s2">
								<c:if test="${s2.index ne 0}">/</c:if>
								${f:h(e2)}
				    		</c:forEach>
			   			</span>
			    	</div>
			  	</div>
	      	</div>
		</c:forEach>

    </main>
</div>
</tiles:put>
</tiles:insert>
