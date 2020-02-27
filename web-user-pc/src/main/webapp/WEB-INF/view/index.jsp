<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">

<tiles:put name="localScript" type="string" >
	<script src="${url:currentJsDir()}index.js" type="text/javascript" ></script>
</tiles:put>

<tiles:put name="title" value="トップページ" type="String" />
<tiles:put name="content" type="string">

<div class="content">
    <main class="photogallery">

      <div class="photo">
        <div class="inner">
          <div class="square">
            <img src="../images/sample1.jpg" alt="">
          </div>
          <div class="txt">
            <p>XXXXXXXXXXXXXXXXXXXXXXXXXXXXX&nbsp;(2/14)</p>
            <span>タグA/タグB/タグC</span>
          </div>
        </div>
      </div>

      <div class="photo">
        <div class="inner">
          <div class="square">
            <img src="../images/sample1.jpg" alt="">
          </div>
        </div>
      </div>

      <div class="photo">
        <div class="inner">
          <div class="square">
            <img src="../images/sample1.jpg" alt="">
          </div>
        </div>
      </div>

      <div class="photo">
        <div class="inner">
          <div class="square">
            <img src="../images/sample1.jpg" alt="">
          </div>
        </div>
      </div>

      <div class="photo">
        <div class="inner">
          <div class="square">
            <img src="../images/sample1.jpg" alt="">
          </div>
        </div>
      </div>

      <div class="photo">
        <div class="inner">
          <div class="square">
            <img src="../images/sample1.jpg" alt="">
          </div>
        </div>
      </div>

      <div class="photo">
        <div class="inner">
          <div class="square">
            <img src="../images/sample1.jpg" alt="">
          </div>
        </div>
      </div>

      <div class="photo">
        <div class="inner">
          <div class="square">
            <img src="../images/sample1.jpg" alt="">
          </div>
        </div>
      </div>

      <div class="photo">
        <div class="inner">
          <div class="square">
            <img src="../images/sample1.jpg" alt="">
          </div>
        </div>
      </div>

      <div class="photo">
        <div class="inner">
          <div class="square">
            <img src="../images/sample1.jpg" alt="">
          </div>
        </div>
      </div>

    </main>
</div>
</tiles:put>
</tiles:insert>
