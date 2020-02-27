<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<footer class="footer">
  <section >© 2020 isystk.com</section>
</footer>


<div id="menu" >
    <div>
      <a href="" class="menu-close-btn"><i class="far fa-times-circle"></i></a>
   </div>
	<jsp:include page="/WEB-INF/view/common/parts/freeword.jsp" />

    <nav>
		<ul>
	      <li><a href="${url:absolute(Url.userpcTop)}">トップ</a></li>
	      <li class="page_item page-item-20"><a href="${url:absolute(Url.userpcMember)}">マイページ</a></li>
	      <li class="page_item page-item-19"><a href="${url:absolute(Url.userpcMemberPostRegist)}">投稿する</a></li>
		</ul>
    </nav>

</div>
