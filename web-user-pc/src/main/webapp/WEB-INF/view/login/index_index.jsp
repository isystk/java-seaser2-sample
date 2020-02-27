<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">

<tiles:put name="title" value="ログイン画面" type="String" />
<tiles:put name="content" type="string">

<div class="content">
	<main>
	    <article class="detail">
	        <div class="entry-header">
	          <h1 class="entry-title">ログイン画面</h1>
			</div>
        	<div class="entry-content">

				<%-- ▽エラー▽ --%>
				<div class="errortxt">
					<p><html:errors/></p>
				</div>
				<%-- △エラー△ --%>

				<p>ご登録のメールアドレス・パスワードでログイン</p>

				<s:form method="post">
					<html:hidden property="backUrl" />
					<%-- ↓↓↓入力 --%>
	           		<table class="formTable">
					<tbody>
					<!-- loop -->
					<tr class="rowspan">
					<th><span class="mark">メールアドレス</span><span class="required">【必須】</span></th>
					</tr>
					<tr>
					<td>
					<p><html:text property="loginUserName" styleClass="text size_l" errorStyleClass="error text size_l" maxlength="${MaxLength.T_USER_MAIL}" /></p>
					<p class="sub">記入例：xxxx@bbb.cc</p>
					</td>
					</tr>
					<!--/ loop -->
					<!-- loop -->
					<tr class="rowspan">
					<th><span class="mark">パスワード</span><span class="required">【必須】</span></th>
					</tr>
					<tr>
					<td>
					<p><html:password property="loginUserPassword" styleClass=" text size_l" errorStyleClass="error text size_l" maxlength="${MaxLength.T_USER_PASSWORD}" /></p>
					</td>
					</tr>
					<!--/ loop -->
					</tbody>
					</table>
					<!-- ↑↑↑入力 -->

					<p class="sub"><a href="${url:absolute(Url.userpcEntryRegist)}">新規会員登録はこちら</a></p>
					<p class="sub"><a href="${url:absolute(Url.userpcEntyRemind)}">パスワードをお忘れの方はこちら</a></p>

					<%-- ↓↓↓ボタン --%>
       				<p class="btnArea"><input type="submit" name="login" value="ログイン"></p>
					<%-- ↑↑↑ボタン --%>

				</s:form>

			</div>
	    </article>
	</main>
</div>
</tiles:put>
</tiles:insert>
