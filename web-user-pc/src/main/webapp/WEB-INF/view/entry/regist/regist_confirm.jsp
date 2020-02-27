<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">

<tiles:put name="localScript" type="string" >
	<script src="${url:currentJsDir()}regist_confirm.js" type="text/javascript" ></script>
</tiles:put>

<tiles:put name="title" value="会員登録内容確認" type="String" />
<tiles:put name="content" type="string">

<div class="content">
	<main>
	    <article class="detail ">
	        <div class="entry-header">
	          <h1 class="entry-title">会員登録内容確認</h1>
			</div>
        	<div class="entry-content">

				<p>こちらの内容で会員登録してもよろしいでしょうか？</p>

				<%-- ▽エラー▽ --%>
				<div class="errortxt">
					<p><html:errors/></p>
				</div>
				<%-- △エラー△ --%>

				<s:form method="post">
					<%-- ↓↓フォーム --%>
					<div class="form" id="inputAddressArea">
						<%-- ↓↓↓入力 --%>
	            		<table class="formTable">
							<tbody>
								<%-- loop --%>
								<tr>
									<th><span class="mark">お名前</span></th>
									<td>
										<p>${f:h(familyName)}　${f:h(name)}</p>
									</td>
								</tr>
								<%--/ loop --%>
								<%-- loop --%>
								<tr>
									<th><span class="mark">お名前（フリガナ）</span></th>
									<td>
										<p>${f:h(familyNameKana)}　${f:h(nameKana)}</p>
									</td>
								</tr>
								<%--/ loop --%>
								<%-- loop --%>
								<tr>
									<th><span class="mark">メールアドレス</span></th>
									<td>
										<p>${f:h(mail)}</p>
									</td>
								</tr>
								<%--/ loop --%>
								<%-- loop --%>
								<tr>
									<th><span class="mark">パスワード</span></th>
									<td>
										<p>**********</p>
									</td>
								</tr>
								<%--/ loop --%>
								<%-- loop --%>
								<tr>
									<th><span class="mark">性別</span></th>
									<td>
										<c:forEach var="e" items="${Sex.ITEMS}">
											<c:if test="${e.code == sex}"><bean:write name="e" property="value"/></c:if>
										</c:forEach>
									</td>
								</tr>
								<%--/ loop --%>
								<%-- loop --%>
								<tr>
									<th><span class="mark">住所</span></th>
									<td>
										<p>
											<c:if test="${not empty zip}"><cmn:formatPostCode value="${zip}"/><br /></c:if>
											<c:forEach var="e" items="${Prefecture.ITEMS}">
												<c:if test="${e.code == prefectureId}"><bean:write name="e" property="value"/></c:if>
											</c:forEach>
											${f:h(area)}
											${f:h(address)}
											${f:h(building)}
										</p>
									</td>
								</tr>
								<%--/ loop --%>
								<%-- loop --%>
								<tr>
									<th><span class="mark">電話番号</span></th>
									<td>
										<p>${f:h(tel)}</p>
									</td>
								</tr>
								<%--/ loop --%>
								<%-- loop --%>
								<tr>
									<th><span class="mark">生年月日</span></th>
									<td>
										<p>
											<fmt:formatDate value="${f:date(birthday, Format.DATE_FORMANUALINPUT)}" pattern="${Format.DATE}"/>
										</p>
									</td>
								</tr>
								<%--/ loop --%>
							</tbody>
						</table>
						<%-- ↑↑↑入力 --%>

						<%-- ↓↓↓ボタン --%>
						<p class="btnArea">
							<input type="submit" name="moveBackFromConfirmToIndex" value="戻って修正する">&nbsp;
							<input type="submit" name="sendmail" value="上記の内容で登録する">
						</p>
					</div>
					<%-- ↑↑フォーム --%>
				</s:form>
			</div>
	    </article>
	</main>
</div>
</tiles:put>
</tiles:insert>
