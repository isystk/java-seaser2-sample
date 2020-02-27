<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:if test="${cmn:isEnvUt()}">
<logic:present name="ERROR.REFERER" >
<logic:present name="ERROR.COOKIE" >
<logic:present name="ERROR.TRACE" >
<div id="nav">
<h2 class="heading">デバッグ情報 <span class="required">(※ローカル、社内開発環境でのみ表示されます)</span></h2>
</div>
</logic:present>
</logic:present>
</logic:present>

<logic:present name="ERROR.REFERER" >
<div class="debugBox">
<h2 class="mark">遷移元の画面</h2>
<p><a href='<bean:write name="ERROR.REFERER"/>'><bean:write name="ERROR.REFERER"/></a></p>
</div>
</logic:present>

<logic:present name="ERROR.COOKIE" >
<div class="debugBox">
<p><bean:write name="ERROR.COOKIE"/></p>
<h2 class="mark">クッキー</h2>
</div>
</logic:present>

<logic:present name="ERROR.TRACE" >
<div class="debugBox">
<h2 class="mark">スタックトレース</h2>
<p><bean:write name="ERROR.TRACE"/></p>
</div>
</logic:present>


</c:if>
