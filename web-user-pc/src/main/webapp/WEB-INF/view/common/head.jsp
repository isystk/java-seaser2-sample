
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="content-language" content="ja" />
<meta http-equiv="content-Type" content="text/html; charset=utf-8" />
<title><tiles:insert attribute="title" ignore="true"/></title>
<link rel="shortcut icon" href="${f:url('/images/favicon.ico')}" />

<c:choose>
<c:when test="${cmn:isEnvUt()}">
<link href="${f:url('/css/normalize.css')}" rel="stylesheet" type="text/css" media="all" />
<link href="${f:url('/css/common.css')}" rel="stylesheet" type="text/css" media="all" />
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.1/css/all.min.css" rel="stylesheet" type="text/css" media="all" />
</c:when>
<c:otherwise>
<link href="${f:url('/css/all.css')}" rel="stylesheet" type="text/css" media="all" />
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.1/css/all.min.css" rel="stylesheet" type="text/css" media="all" />
</c:otherwise>
</c:choose>
