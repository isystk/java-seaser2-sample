<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">

<tiles:put name="localScript" type="string" >
	<script src="${url:currentJsDir()}regist_index.js" type="text/javascript" ></script>
</tiles:put>

<tiles:put name="title" value="会員登録入力" type="String" />
<tiles:put name="content" type="string">

<div class="content">
	<main>
	    <article class="detail ">
	        <div class="entry-header">
	          <h1 class="entry-title">会員登録</h1>
			</div>
        	<div class="entry-content">
				<p>会員情報を入力してください。この情報は後で変更することも出来ます。</p>

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
								<th colspan="2"><span class="mark">お名前</span><span class="required">【必須】</span></th>
								<td>
									<p><label>姓　 <html:text property="familyName" styleClass="text size_m realtimecheck" errorStyleClass="text size_m error realtimecheck" styleId="familyName" maxlength="${MaxLength.T_USER_FAMILYNAME}"/></label>　 <label>名　 <html:text property="name" styleClass="text size_m realtimecheck" errorStyleClass="text size_m error realtimecheck" styleId="name" maxlength="${MaxLength.T_USER_NAME}"/></label></p>
									<div id="familyNameErrMessage">
									<html:errors header="false" footer="false" property="familyName" />
									</div>
									<div id="nameErrMessage">
									<html:errors header="false" footer="false" property="name" />
									</div>
								</td>
							</tr>
							<%--/ loop --%>
							<%-- loop --%>
							<tr>
								<th colspan="2"><span class="mark">お名前（フリガナ）</span><span class="required">【必須】</span></th>
								<td>
									<p><label>セイ <html:text property="familyNameKana" styleClass="text size_m realtimecheck" errorStyleClass="text size_m error realtimecheck" styleId="familyNameKana" maxlength="${MaxLength.T_USER_FAMILYNAMEKANA}"/></label>　 <label>メイ <html:text property="nameKana" styleClass="text size_m realtimecheck" errorStyleClass="text size_m error realtimecheck" styleId="nameKana" maxlength="${MaxLength.T_USER_NAMEKANA}"/></label></p>
									<div id="familyNameKanaErrMessage">
										<html:errors header="false" footer="false" property="familyNameKana" />
									</div>
									<div id="nameKanaErrMessage">
										<html:errors header="false" footer="false" property="nameKana" />
									</div>
								</td>
							</tr>
							<%--/ loop --%>

							<%-- loop --%>
							<tr>
								<th colspan="2"><span class="mark">メールアドレス</span><span class="required">【必須】</span></th>
								<td>
									<p><html:text property="mail" styleClass="text size_l realtimecheck" errorStyleClass="text size_l error realtimecheck" maxlength="${MaxLength.T_USER_MAIL}" readonly="${ isEmailFixed }" /></p>
									<div id="mailErrMessage">
										<html:errors header="false" footer="false" property="mail" />
									</div>
								</td>
							</tr>
							<%--/ loop --%>
							<%-- loop --%>
							<tr>
								<th colspan="2"><span class="mark">パスワード</span><span class="required">【必須】</span></th>
								<td>
									<p><html:password property="password" styleClass="text size_l realtimecheck" errorStyleClass="text size_l error realtimecheck" maxlength="${MaxLength.PASSWORD_MAX}"/></p>
									<p class="sub">半角英数字8～15文字 / 記号は「.」「_」「-」のみ使用可能</p>
									<div id="passwordErrMessage">
										<html:errors header="false" footer="false" property="password" />
									</div>
								</td>
							</tr>
							<%--/ loop --%>
							<%-- loop --%>
							<tr>
								<th colspan="2"><span class="mark">パスワード（確認用）</span><span class="required">【必須】</span></th>
								<td>
									<p><html:password property="passwordConf" styleClass="text size_l realtimecheck" errorStyleClass="text size_l error realtimecheck" maxlength="${MaxLength.PASSWORD_MAX}"/></p>
									<p class="sub">半角英数字8～15文字 / 記号は「.」「_」「-」のみ使用可能</p>
									<div id="passwordConfErrMessage">
										<html:errors header="false" footer="false" property="passwordConf" />
									</div>
								</td>
							</tr>
							<%--/ loop --%>
							<%-- loop --%>
							<tr>
								<th colspan="2"><span class="mark">性別</span><span class="required">【必須】</span></th>
								<td>
						            <ul>
						            	<c:forEach var="sexRadio" items="${Sex.ITEMS}">
											<li>
												<label for="sex_c${sexRadio.code}">
													<html:radio property="sex" value="${sexRadio.code}" styleClass="radio" errorStyleClass="radio　error" styleId="sex_c${sexRadio.code}"/>
													<span><bean:write name="sexRadio" property="value"/></span>
												</label>
											</li>
										</c:forEach>
						            </ul>
										<html:errors header="false" footer="false" property="sex" />
								</td>
							</tr>
							<%--/ loop --%>
							<tr>
								<th rowspan="5"><span class="mark">住所</span></th>
								<th>郵便番号<!-- <span class="required">【必須】</span> --></th>
								<td>
									<p>
										<html:text property="zip" styleId="zip" styleClass="text size_m realtimecheck" errorStyleClass="text size_m error realtimecheck" maxlength="${MaxLength.POST_CODE}"/>
										<span><input type="button" value="住所検索" class="searchAddressBtn address_btn"></span>
									</p>
									<p class="sub">半角数字、ハイフン(-)／記入例：123-4567</p>
									<div id="zipErrMessage">
										<html:errors header="false" footer="false" property="zip" />
									</div>
								</td>
							</tr>
							<tr>
								<th>都道府県<!-- <span class="required">【必須】</span> --></th>
								<td>
									<p>
										<html:select property="prefectureId" styleId="prefectureId" errorStyleClass="error">
								            <html:option value="">${WebConstants$SelectOption.VOID_VALUE}</html:option>
								            <c:forEach var="prefectureSelection" items="${Prefecture.ITEMS}">
								                <html:option value="${f:h(prefectureSelection.code)}">${f:h(prefectureSelection.value)}</html:option>
								            </c:forEach>
								        </html:select>
									</p>
										<html:errors header="false" footer="false" property="prefectureId" />
								</td>
							</tr>
							<tr>
								<th>市区町村<!-- <span class="required">【必須】</span> --></th>
								<td>
									<p><html:text property="area" styleId="area" styleClass="text size_l realtimecheck" errorStyleClass="text size_l error realtimecheck" maxlength="${MaxLength.T_USER_AREA}"/></p>
									<div id="areaErrMessage">
										<html:errors header="false" footer="false" property="area" />
									</div>
								</td>
							</tr>
							<tr>
								<th>町名番地<!-- <span class="required">【必須】</span> --></th>
								<td>
									<p><html:text property="address" styleId="address" styleClass="text size_l realtimecheck" errorStyleClass="text size_l error realtimecheck" maxlength="${MaxLength.T_USER_ADDRESS}"/></p>
									<div id="addressErrMessage">
										<html:errors header="false" footer="false" property="address" />
									</div>
								</td>
							</tr>
							<tr>
								<th>建物名</th>
								<td>
									<p><html:text property="building" styleClass="text size_l realtimecheck" errorStyleClass="text size_l error realtimecheck" maxlength="${MaxLength.T_USER_BUILDING}"/></p>
									<div id="buildingErrMessage">
										<html:errors header="false" footer="false" property="building" />
									</div>
								</td>
							</tr>
							<%--/ loop --%>
							<%-- loop --%>
							<tr>
								<th colspan="2"><span class="mark">電話番号</span></th>
								<td>
									<p><html:text property="tel" styleClass="text size_l realtimecheck" errorStyleClass="text size_l error realtimecheck" maxlength="${MaxLength.T_USER_TEL}"/>　<span class="sub">半角数字、ハイフン(-)</span></p>
									<p class="noticetxt">例：090-1234-5678</p>
									<div id="telErrMessage">
										<html:errors header="false" footer="false" property="tel" />
									</div>
								</td>
							</tr>
							<%--/ loop --%>
							<%-- loop --%>
							<tr>
								<th colspan="2"><span class="mark">生年月日</span></th>
								<td>
									<%-- ↓↓↓↓生年月日(年) --%>
									<html:select property="birthdayYyyy" styleId="birthdayYyyy" errorStyleClass="error">
										<option value="" >${WebConstants$SelectOption.VOID_VALUE}</option>
										<c:forEach var="e" items="${listYyyy}">
											<c:choose>
												<c:when test="${e.isDefault}">
													<option value="${e.id}" selected="selected" >${f:h(e.value)}</option>
												</c:when>
												<c:otherwise>
													<option value="${e.id}">${f:h(e.value)}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</html:select><label>年</label>
									<%-- ↑↑↑↑生年月日(年) --%>
									<%-- ↓↓↓↓生年月日(月) --%>
									<html:select property="birthdayMm" styleId="birthdayMm" errorStyleClass="error">
										<option value="" >${WebConstants$SelectOption.VOID_VALUE}</option>
										<c:forEach var="e" items="${listMm}">
											<c:choose>
												<c:when test="${e.isDefault}">
													<option value="${e.id}" selected="selected" >${f:h(e.value)}</option>
												</c:when>
												<c:otherwise>
													<option value="${e.id}">${f:h(e.value)}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</html:select><label>月</label>
									<%-- ↑↑↑↑生年月日(月) --%>
									<%-- ↓↓↓↓生年月日(日) --%>
									<html:select property="birthdayDd" styleId="birthdayDd"  errorStyleClass="error">
										<option value="" >${WebConstants$SelectOption.VOID_VALUE}</option>
										<c:forEach var="e" items="${listDd}">
											<c:choose>
												<c:when test="${e.isDefault}">
													<option value="${e.id}" selected="selected" >${f:h(e.value)}</option>
												</c:when>
												<c:otherwise>
													<option value="${e.id}">${f:h(e.value)}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</html:select><label>日</label>
									<%-- ↑↑↑↑生年月日(日) --%>
								</td>
							</tr>
							<%--/ loop --%>
						</tbody>
						</table>
						<%-- ↑↑↑入力 --%>

						<%-- ↓↓↓ボタン --%>
	       				<p class="btnArea"><input type="submit" name="confirm" value="確認画面へ"></p>
						<%-- ↑↑↑ボタン --%>
					</div>

				</div>
				<%-- ↑↑フォーム --%>
			</s:form>
	    </article>
	</main>
</div>
</tiles:put>
</tiles:insert>