<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">

<tiles:put name="title" value="新パスワード設定" type="String" />
<tiles:put name="content" type="string">

<div class="content">
	<main>
	    <article class="detail">
	        <div class="entry-header">
	          <h1 class="entry-title">パスワードリマインダ　新パスワード設定</h1>
			</div>
        	<div class="entry-content">

				<%-- ▽エラー▽ --%>
				<div class="errortxt">
					<p><html:errors/></p>
				</div>
				<%-- △エラー△ --%>

				<p><span class="sub">※ パスワードは完了画面に表示されませんので、ご自身で控えていただくようお願いいたします。</span></p>

				<%-- ▼入力フォーム▼ --%>
				<s:form method="post">
           			<table class="formTable">
						<tbody>
							<tr>
								<th>
									<span class="mark">新しいパスワード</span><span class="required">【必須】</span>
								</th>
								<td>
									<p>
										<html:password property="password" styleClass="text size_l realtimecheck" errorStyleClass="text size_l error realtimecheck" maxlength="${MaxLength.PASSWORD_MAX}"/>
									</p>
									<div id="passwordErrMessage"></div>
									<p class="sub">半角英数字8～15文字 / 記号は「.」「_」「-」のみ使用可能</p>
								</td>
							</tr>
							<tr>
								<th>
									<span class="mark">新しいパスワード(確認用)</span><span class="required">【必須】</span>
								</th>
								<td>
									<p>
										<html:password property="passwordConfirm" styleClass="text size_l realtimecheck" errorStyleClass="text size_l error realtimecheck" maxlength="${MaxLength.PASSWORD_MAX}"/>
									</p>
									<div id="passwordConfirmErrMessage"></div>
									<p class="sub">半角英数字8～15文字 / 記号は「.」「_」「-」のみ使用可能</p>
								</td>
							</tr>
						</tbody>
					</table>

					<p><span class="sub">※ パスワードは完了画面に表示されませんので、ご自身で控えていただくようお願いいたします。</span></p>

      				<p class="btnArea">
      					<input type="complete" name="returnInput" value="上記の内容で登録する">
      				</p>

				</s:form>
				<%-- ▲入力フォーム▲ --%>

			</div>
	    </article>
	</main>
</div>
</tiles:put>
</tiles:insert>
