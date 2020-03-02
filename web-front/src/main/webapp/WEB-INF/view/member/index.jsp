<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">

<tiles:put name="localScript" type="string" >
	<script src="${url:currentJsDir()}index.js" type="text/javascript" ></script>
</tiles:put>

<tiles:put name="title" value="マイページトップ" type="String" />
<tiles:put name="content" type="string">

<div class="content">
	<main class="photogallery">

		<h2 class="entry-title">マイページ</h2>
		<form name="form" method="get" action="#">
		  <table class="searchTable">
		  <tbody>
		  <tr>
		    <th>ユーザー名</th>
		    <td>
		      <input type="text" name="userName" maxlength="100" value="" />
		    </td>
		  </tr>
		  <tr>
		    <th>ログイン日時</th>
		    <td>
		      <input type="text" name="loginDateFrom" maxlength="10" value="" class="datepicker txtSmall">～
		      <input type="text" name="loginDateTo" maxlength="10" value="" class="datepicker txtSmall">
		    </td>
		  </tr>
		  </tbody></table>
		  <p class="btnArea"><input type="submit" name="search" value="検索する"></p>
		</form>

		<div class="paging clearfix">
		  <p><span>100</span>件中&nbsp;<span>1</span>件～<span>20</span>件を表示
		  &nbsp;｜&nbsp;並び順：
		    <select name="sortKey" class="sort">
		      <option value="1">ユーザー名の降順</option>
		      <option value="2">ログイン日の降順</option>
		    </select>
		  </p>
		  <ul>
		    <li class="focus"><span>1</span></li>
		    <li class="num"><a class="js_paging" data-page="2" href="#">2</a></li>
		    <li class="num"><a class="js_paging" data-page="3" href="#">3</a></li>
		    <li class="num"><a class="js_paging" data-page="4" href="#">4</a></li>
		    <li class="num"><a class="js_paging" data-page="5" href="#">5</a></li>
		    <li class="next "><a class="js_paging" data-page="2" href="#">次へ &gt;&gt; </a></li>
		  </ul>
		</div>

		<table class="listTable">
		  <tbody><tr>
		    <th>ユーザーID</th>
		    <th>ユーザー名</th>
		    <th>最終ログイン日時</th>
		    <th>&nbsp;</th>
		  </tr>
		  <tr>
		    <td>1122</td>
		    <td>XXXXXXX</td>
		    <td>
		      2020年1月21日(火) 10:22</td>
		    <td class="btn">
		      <input type="button" value="詳細" /><br>
		      <input type="button" value="修正" class="js-overlay" data-panel="#sample" /><br>
		      <input type="button" value="削除"><br>
		    </td>
		  </tr>
		  <tr>
		    <td>1122</td>
		    <td>XXXXXXX</td>
		    <td>
		      2020年1月21日(火) 10:22</td>
		    <td class="btn">
		      <input type="button" value="詳細" /><br>
		      <input type="button" value="修正" class="js-overlay" data-panel="#sample" /><br>
		      <input type="button" value="削除"><br>
		    </td>
		  </tr>
		  <tr>
		    <td>1122</td>
		    <td>XXXXXXX</td>
		    <td>
		      2020年1月21日(火) 10:22</td>
		    <td class="btn">
		      <input type="button" value="詳細" /><br>
		      <input type="button" value="修正" class="js-overlay" data-panel="#sample" /><br>
		      <input type="button" value="削除"><br>
		    </td>
		  </tr>
		  <tr>
		    <td>1122</td>
		    <td>XXXXXXX</td>
		    <td>
		      2020年1月21日(火) 10:22</td>
		    <td class="btn">
		      <input type="button" value="詳細" /><br>
		      <input type="button" value="修正" class="js-overlay" data-panel="#sample" /><br>
		      <input type="button" value="削除"><br>
		    </td>
		  </tr>
		  <tr>
		    <td>1122</td>
		    <td>XXXXXXX</td>
		    <td>
		      2020年1月21日(火) 10:22</td>
		    <td class="btn">
		      <input type="button" value="詳細" /><br>
		      <input type="button" value="修正" class="js-overlay" data-panel="#sample" /><br>
		      <input type="button" value="削除"><br>
		    </td>
		  </tr>
		  <tr>
		    <td>1122</td>
		    <td>XXXXXXX</td>
		    <td>
		      2020年1月21日(火) 10:22</td>
		    <td class="btn">
		      <input type="button" value="詳細" /><br>
		      <input type="button" value="修正" class="js-overlay" data-panel="#sample" /><br>
		      <input type="button" value="削除"><br>
		    </td>
		  </tr>
		  <tr>
		    <td>1122</td>
		    <td>XXXXXXX</td>
		    <td>
		      2020年1月21日(火) 10:22</td>
		    <td class="btn">
		      <input type="button" value="詳細" /><br>
		      <input type="button" value="修正" class="js-overlay" data-panel="#sample" /><br>
		      <input type="button" value="削除"><br>
		    </td>
		  </tr>
		  <tr>
		    <td>1122</td>
		    <td>XXXXXXX</td>
		    <td>
		      2020年1月21日(火) 10:22</td>
		    <td class="btn">
		      <input type="button" value="詳細" /><br>
		      <input type="button" value="修正" class="js-overlay" data-panel="#sample" /><br>
		      <input type="button" value="削除"><br>
		    </td>
		  </tr>
		  <tr>
		    <td>1122</td>
		    <td>XXXXXXX</td>
		    <td>
		      2020年1月21日(火) 10:22</td>
		    <td class="btn">
		      <input type="button" value="詳細" /><br>
		      <input type="button" value="修正" class="js-overlay" data-panel="#sample" /><br>
		      <input type="button" value="削除"><br>
		    </td>
		  </tr>
		  <tr>
		    <td>1122</td>
		    <td>XXXXXXX</td>
		    <td>
		      2020年1月21日(火) 10:22</td>
		    <td class="btn">
		      <input type="button" value="詳細" /><br>
		      <input type="button" value="修正" class="js-overlay" data-panel="#sample" /><br>
		      <input type="button" value="削除"><br>
		    </td>
		  </tr>
		  <tr>
		    <td>1122</td>
		    <td>XXXXXXX</td>
		    <td>
		      2020年1月21日(火) 10:22</td>
		    <td class="btn">
		      <input type="button" value="詳細" /><br>
		      <input type="button" value="修正" class="js-overlay" data-panel="#sample" /><br>
		      <input type="button" value="削除"><br>
		    </td>
		  </tr>
		  <tr>
		    <td>1122</td>
		    <td>XXXXXXX</td>
		    <td>
		      2020年1月21日(火) 10:22</td>
		    <td class="btn">
		      <input type="button" value="詳細" /><br>
		      <input type="button" value="修正" class="js-overlay" data-panel="#sample" /><br>
		      <input type="button" value="削除"><br>
		    </td>
		  </tr>
		  <tr>
		    <td>1122</td>
		    <td>XXXXXXX</td>
		    <td>
		      2020年1月21日(火) 10:22</td>
		    <td class="btn">
		      <input type="button" value="詳細" /><br>
		      <input type="button" value="修正" class="js-overlay" data-panel="#sample" /><br>
		      <input type="button" value="削除"><br>
		    </td>
		  </tr>
		  <tr>
		    <td>1122</td>
		    <td>XXXXXXX</td>
		    <td>
		      2020年1月21日(火) 10:22</td>
		    <td class="btn">
		      <input type="button" value="詳細" /><br>
		      <input type="button" value="修正" class="js-overlay" data-panel="#sample" /><br>
		      <input type="button" value="削除"><br>
		    </td>
		  </tr>
		  <tr>
		    <td>1122</td>
		    <td>XXXXXXX</td>
		    <td>
		      2020年1月21日(火) 10:22</td>
		    <td class="btn">
		      <input type="button" value="詳細" /><br>
		      <input type="button" value="修正" class="js-overlay" data-panel="#sample" /><br>
		      <input type="button" value="削除"><br>
		    </td>
		  </tr>
		  <tr>
		    <td>1122</td>
		    <td>XXXXXXX</td>
		    <td>
		      2020年1月21日(火) 10:22</td>
		    <td class="btn">
		      <input type="button" value="詳細" /><br>
		      <input type="button" value="修正" class="js-overlay" data-panel="#sample" /><br>
		      <input type="button" value="削除"><br>
		    </td>
		  </tr>
		  <tr>
		    <td>1122</td>
		    <td>XXXXXXX</td>
		    <td>
		      2020年1月21日(火) 10:22</td>
		    <td class="btn">
		      <input type="button" value="詳細" /><br>
		      <input type="button" value="修正" class="js-overlay" data-panel="#sample" /><br>
		      <input type="button" value="削除"><br>
		    </td>
		  </tr>
		  <tr>
		    <td>1122</td>
		    <td>XXXXXXX</td>
		    <td>
		      2020年1月21日(火) 10:22</td>
		    <td class="btn">
		      <input type="button" value="詳細" /><br>
		      <input type="button" value="修正" class="js-overlay" data-panel="#sample" /><br>
		      <input type="button" value="削除"><br>
		    </td>
		  </tr>
		  <tr>
		    <td>1122</td>
		    <td>XXXXXXX</td>
		    <td>
		      2020年1月21日(火) 10:22</td>
		    <td class="btn">
		      <input type="button" value="詳細" /><br>
		      <input type="button" value="修正" class="js-overlay" data-panel="#sample" /><br>
		      <input type="button" value="削除"><br>
		    </td>
		  </tr>
		  <tr>
		    <td>1122</td>
		    <td>XXXXXXX</td>
		    <td>
		      2020年1月21日(火) 10:22</td>
		    <td class="btn">
		      <input type="button" value="詳細" /><br>
		      <input type="button" value="修正" class="js-overlay" data-panel="#sample" /><br>
		      <input type="button" value="削除"><br>
		    </td>
		  </tr>
		  </tbody></table>

		  <div class="paging clearfix">
		    <p><span>100</span>件中&nbsp;<span>1</span>件～<span>20</span>件を表示
		    &nbsp;｜&nbsp;並び順：
		      <select name="sortKey" class="sort">
		        <option value="1">ユーザー名の降順</option>
		        <option value="2">ログイン日の降順</option>
		      </select>
		    </p>
		    <ul>
		      <li class="focus"><span>1</span></li>
		      <li class="num"><a class="js_paging" data-page="2" href="#">2</a></li>
		      <li class="num"><a class="js_paging" data-page="3" href="#">3</a></li>
		      <li class="num"><a class="js_paging" data-page="4" href="#">4</a></li>
		      <li class="num"><a class="js_paging" data-page="5" href="#">5</a></li>
		      <li class="next "><a class="js_paging" data-page="2" href="#">次へ &gt;&gt; </a></li>
		    </ul>
		  </div>

    </main>
</div>
</tiles:put>
</tiles:insert>
