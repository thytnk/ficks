<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<!doctype html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">

  <title>FICKS: FSL Intranet Community Knowledge management System</title>
  <link rel="stylesheet" href="/css/bootstrap.min.css">
  <link rel="stylesheet" href="/css/bootstrap-theme.min.css">
  <%--
  <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
  --%>
  <link rel="shortcut icon" href="/favicon.ico">
  <style><jsp:include page="css.jsp"/></style>

  <!--[if lt IE 9]>
    <script src="/js/lib/html5shiv.min.js"></script>
    <script src="/js/lib/respond.min.js"></script>
  <![endif]-->
</head>
<body>

    <nav class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a href="/documents" class="navbar-brand"><img src="/img/FICKS1.png?a" alt="FICKS:"></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <h1 class="navbar-text"> <b>F</b>SL <b>I</b>ntranet <b>C</b>ommunity <b>K</b>nowledge management <b>S</b>ystem</h1>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="/documents" class="glyphicon glyphicon-home" aria-hidden="true"> HOME</a></li>
          </ul>
        </div>
      </div>
    </nav>
    <!--
<div id="header" class="navbar navbar-default navbar-fixed-top">
  <div class="container">
  <div class="navbar-header">

    <a href="/documents" class="navbar-brand"><img src="/img/FICKS.png" alt="FICKS:"></a></div>

  </div>
  <div id="navbar" class="navbar-collapse collapse">
  <h1 class="navbar-text col-xs-hidden"> <b>F</b>SL <b>I</b>ntranet <b>C</b>ommunity <b>K</b>nowledge management <b>S</b>ystem</h1>
  </div>
  </div>
      <div class="nav navbar-nav navbar-right">
        <a href="/documents" class="glyphicon glyphicon-home" aria-hidden="true"> HOME</a>
        <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
      </div>

</div>
-->
<div class="container">
<div class="row small">
  <form:form id="search" action="/documents/search" method="GET">

    <div id="categories" class="row container">
      <div class="col-sm-8 row btn-group">
      <label class="col-sm-2 btn btn-category-A"><form:checkbox path="category" value="A"/> 提案資料</label>
      <label class="col-sm-2 btn btn-category-B"><form:checkbox path="category" value="B"/> 技術資料</label>
      <label class="col-sm-2 btn btn-category-C"><form:checkbox path="category" value="C"/> 設定資料</label>
      <label class="col-sm-2 btn btn-category-D"><form:checkbox path="category" value="D"/> 手順資料</label>
      <label class="col-sm-2 btn btn-category-E"><form:checkbox path="category" value="E"/> 業務窓口</label>
      </div>
    </div>

    <div class="row">
      <p class="col-sm-2 form-group">
        <label class="control-label" for="search_area">分野</label>
        <form:select id="search_area" path="area" class="form-control input-sm">
          <form:option value="*">&nbsp;</form:option>
          <form:option value="1">小学校</form:option>
          <form:option value="2">中学校</form:option>
          <form:option value="3">高校</form:option>
          <form:option value="4">大学</form:option>
          <form:option value="5">公共</form:option>
          <form:option value="6">金融</form:option>
          <form:option value="7">産業</form:option>
          <form:option value="99">その他</form:option>
        </form:select>
      </p>
      <p class="col-sm-2 form-group">
        <label class="control-label" for="search_purpose">ドキュメント種類</label>
        <form:select id="search_purpose" path="purpose" class="form-control input-sm">
          <form:option value="*">&nbsp;</form:option>
          <form:option value="1">提案書</form:option>
          <form:option value="2">技術書</form:option>
          <form:option value="3">設定書</form:option>
          <form:option value="4">マニュアル</form:option>
          <form:option value="5">仕様書</form:option>
          <form:option value="6">事例集</form:option>
          <form:option value="7">手順書</form:option>
          <form:option value="99">その他</form:option>
        </form:select>
      </p>
      <p class="col-sm-3 form-group">
        <label class="control-label" for="search_customer">顧客名</label>
        <form:input type="text" id="search_customer" path="customer" class="form-control input-sm"/>
      </p>
      <p class="col-sm-2 form-group">
        <label class="control-label" for="search_author">担当者</label>
        <form:input type="text" id="search_author" path="author" class="form-control input-sm"/>
      </p>
      <p class="col-sm-1 form-group">
        <label class="control-label" for="search_result">成否</label>
        <form:select id="search_result" path="result" class="form-control input-sm">
          <form:option value="*">&nbsp;</form:option>
          <form:option value="1">成功</form:option>
          <form:option value="0">失敗</form:option>
          <form:option value="9">なし</form:option>
        </form:select>
      </p>
      <p class="col-sm-2 form-group">
        <label class="control-label" for="search_reason">成否要因</label>
        <form:select id="search_reason" path="reason" class="form-control input-sm">
          <form:option value="*">&nbsp;</form:option>
          <form:option value="1">価格</form:option>
          <form:option value="2">顧客要件</form:option>
          <form:option value="3">プレゼン力</form:option>
          <form:option value="4">機能・性能</form:option>
          <form:option value="5">サービス品質</form:option>
          <form:option value="6">差別化</form:option>
          <form:option value="7">政治的判断</form:option>
          <form:option value="8">総合的判断</form:option>
        </form:select>
      </p>
    </div>

    <div class="row">
      <div class="col-sm-4">
        <div class="form-group">
          <label class="control-label" for="search_published_to">発行日または期間</label>

            <div class="input-group">
              <form:input type="text" id="search_published_from" path="publishedFrom" class="form-control"/>
              <span class="input-group-addon">～</span>
              <form:input type="text" id="search_published_to" path="publishedTo" class="form-control"/>
            </div>

        </div>
      </div>

      <p class="col-sm-6 form-group">
        <label class="control-label" for="search_keyword">キーワード</label>
        <form:input type="search" id="search_keyword" path="keyword" class="form-control" autofocus="autofocus"/>
      </p>

   
      <p class="col-sm-2 form-group">
        <label class="control-label">&nbsp;</label>
        <button type="submit" id="searchButton" class="form-control btn btn-primary">検索</button>
      </p>
    </div>

  </form:form>
</div>

<hr>

<div class="row">

  <div id="site-link" class="col-sm-2 hidden-xs "><div class="list-group"></div></div>

  <div class="col-sm-10 col-xs-12">
    <c:choose>
    <c:when test="${mode=='new'}">
    <h2 class="row">新着情報</h2>
    </c:when>
    <c:when test="${mode=='search'}">
    <div class="alert alert-info row">
      検索結果:<c:out value="${list.totalElements}"/>件
    </div>
    </c:when>
    </c:choose>

    <ul id="documents" class="list-group">
      <c:forEach var="doc" items="${list.content}">
      <li class="list-group-item row">
      <div class="col-sm-12">
        <div class="row">
          <div class="col-sm-9">
            <div class="row">
              <p class="col-sm-12">
                <label title="文書コード"><c:out value="${doc.code}"/>:</label>
                <span title="ファイル名"><c:out value="${doc.fileName}"/></span>
              </p>
            </div>
            <div class="row">
              <p class="col-sm-12">
                <span class="label label-category-${doc.category}"><c:out value="${categories[doc.category]}"/></span>
                <span title="分野"><c:out value="${areas[doc.area]}"/></span> /
                <span title="ドキュメント種類"><c:out value="${purposes[doc.purpose]}"/></span> /
                <span title="成否"><c:out value="${results[doc.result]}"/><span>
                <c:if test="${doc.reason != null}"><span title="成否要因">（<c:out value="${reasons[doc.reason]}"/>）</span></c:if>
              </p>
            </div>
            <c:if test="${doc.customerName != null}">
            <div class="row">
              <p class="col-sm-12" title="顧客名"><label>顧客名:</label><c:out value="${doc.customerName}"/></p>
            </div>
            </c:if>
            <c:if test="${doc.description != null}">
            <div class="row" title="コメント"><pre class="col-sm-12 small"><c:out value="${doc.description}"/></pre>
            </div>
            </c:if>
          </div>
          <div class="col-sm-3">
            <div class="row small doc_author">
              <span class="col-sm-12" title="担当者・発行日"><c:out value="${doc.authorName}"/>・<fmt:formatDate value="${doc.publishedDate}"/></span>
            </div>
            <div class="row small doc_thumbnail">
              <a class="col-sm-12 download-link" href="/documents/<c:out value="${doc.code}"/>/download" title="ダウンロード">
                <img src="/img/thumbnail/${doc.code}.jpg" alt="NO IMAGE" class="img-thumbnail">
                <p><span class="glyphicon glyphicon-download-alt"></span></p>
              </a>
            </div>
          </div>
        </div>
      </div>
      </li>

      </c:forEach>
    </ul>
  </div>
</div>

<div id="footer" class="navbar navbar-default navbar-fixed-bottom container">
</div>

<script src="/js/lib/modernizr.js"></script>
<script src="/js/lib/jquery-1.11.2.min.js"></script>
<script src="/js/lib/bootstrap.min.js"></script>
<script src="/js/lib/bootstrap-datepicker.min.js"></script>
<script src="/js/lib/bootstrap-datepicker.ja.min.js"></script>
<script>
$(function() {
  $.get("/link.jsp?" + Math.random(), function(data) {
    $("#site-link").html(data);
  });

  $.get("/footer.jsp", function(data) {
    $("#footer").html(data);
  });

  $("#categories input[type='checkbox']").each(function() {
    if (this.checked) {
      $(this).parent().removeClass('gray');
    } else {
      $(this).parent().addClass('gray');
    }
  });

  $("#categories input[type='checkbox']").click(function() {
    if (this.checked) {
      $(this).parent().removeClass('gray');
    } else {
      $(this).parent().addClass('gray');
    }
  });

  //if (!Modernizr.inputtypes.date) {
  $('#search_published_from').datepicker({
    format: 'yyyy/mm/dd',
    language: 'ja'
  });

  $('#search_published_to').datepicker({
    format: 'yyyy/mm/dd',
    language: 'ja'
  });
  //}
});
</script>

</body>
</html>