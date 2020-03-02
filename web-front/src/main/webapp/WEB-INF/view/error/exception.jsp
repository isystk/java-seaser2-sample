<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- アプリケーションで補足できないExceptionが発生した場合に、web.xmlから使用されるページ。Apacheのエラードキュメントにリダイレクトする --%>
<% response.sendRedirect("/error/500.html"); %>