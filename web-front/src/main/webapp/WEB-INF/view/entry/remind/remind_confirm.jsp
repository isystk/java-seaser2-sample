<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">

<tiles:put name="title" value="メールアドレス確認" type="String" />
<tiles:put name="content" type="string">

<div class="content">
	<main>
	    <article class="detail">
	        <div class="entry-header">
	          <h1 class="entry-title">パスワードリマインダ　メールアドレス確認</h1>
			</div>
        	<div class="entry-content">

				<%-- ▽エラー▽ --%>
				<div class="errortxt">
					<p><html:errors/></p>
				</div>
				<%-- △エラー△ --%>

				<p>
					下記のメールアドレスに、パスワード再設定ページのURLを記載したメールを送信します。<br />
					メール受信後24時間以内に、パスワード再設定を完了させてください。<br />
					24時間後にURLは無効になります。
				</p>

				<%-- ▼入力フォーム▼ --%>
				<s:form method="post">
           			<table class="formTable">
						<tbody>
							<tr>
								<th><span class="mark">メールアドレス</span></th>
								<td>
									<p>${f:h(mailAddress)}</p>
								</td>
							</tr>
						</tbody>
					</table>

      				<p class="btnArea">
      					<input type="submit" name="returnInput" value="戻って修正する">
      					<input type="submit" name="sendmail" value="送信する">
      				</p>

				</s:form>
				<%-- ▲入力フォーム▲ --%>

			</div>
	    </article>
	</main>
</div>
</tiles:put>
</tiles:insert>
