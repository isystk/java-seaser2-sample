<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">

<tiles:put name="title" value="メールアドレス入力" type="String" />
<tiles:put name="content" type="string">

<div class="content">
	<main>
	    <article class="detail">
	        <div class="entry-header">
	          <h1 class="entry-title">パスワードリマインダ　メールアドレス入力</h1>
			</div>
        	<div class="entry-content">

				<%-- ▽エラー▽ --%>
				<div class="errortxt">
					<p><html:errors/></p>
				</div>
				<%-- △エラー△ --%>

				<p>ご登録いただいている内容を下記に記入してください。</p>

				<%-- ▼入力フォーム▼ --%>
				<s:form method="post">
           			<table class="formTable">
						<tbody>
							<tr>
								<th>
									<span class="mark">メールアドレス</span><span class="required">【必須】</span>
								</th>
								<td>
									<p>
										<html:text property="mailAddress" styleClass="text size_l realtimecheck" errorStyleClass="text size_l error realtimecheck" maxlength="${MaxLength.T_USER_MAIL}"/>
									</p>
									<div id="mailAddressErrMessage"></div>
								</td>
							</tr>
						</tbody>
					</table>

      				<p class="btnArea"><input type="submit" name="confirm" value="確認画面へ"></p>

				</s:form>
				<%-- ▲入力フォーム▲ --%>

			</div>
	    </article>
	</main>
</div>
</tiles:put>
</tiles:insert>
