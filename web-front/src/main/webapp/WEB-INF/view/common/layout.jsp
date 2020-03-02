<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ja">
<head>
<jsp:include page="/WEB-INF/view/common/head.jsp" />
</head>
<body>
<%--
	<div id="site_loader_overlay"><div class="site_loader_spinner" ></div></div>
 --%>
	<div class="wrap">
		<jsp:include page="header.jsp" flush="true" />
		<tiles:insert attribute="content" />
		<jsp:include page="footer.jsp" flush="true" />
	</div>

	<%-- JSはbody下部に --%>
	<jsp:include page="/WEB-INF/view/common/script.jsp" />

</body>
</html>