<%@ page contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><!doctype html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"
%><%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%><html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">

  <title>FICKS(プロトタイプ): FSL Intranet Community Knowledge management System</title>
  <link rel="stylesheet" href="/css/bootstrap.min.css">
  <link rel="stylesheet" href="/css/bootstrap-theme.min.css">
  <link rel="stylesheet" href="/css/lib/pnotify.custom.min.css">
  <%--
  <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
  --%>
  <link rel="shortcut icon" href="favicon.ico">
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
          <a href="/documents" class="navbar-brand"><img src="/img/logo-mid.png" alt="FICKS:"></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <h1 class="navbar-text"> <b>F</b>SL <b>I</b>ntranet <b>C</b>ommunity <b>K</b>nowledge management <b>S</b>ystem (プロトタイプ)</h1>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="/documents" class="glyphicon glyphicon-home" aria-hidden="true"> HOME</a></li>
          </ul>
        </div>
      </div>
    </nav>
<div class="container">
<div class="row small">
  <form:form id="add" action="/documents" method="POST">

    <div id="categories" class="row container">
      <div class="col-sm-8 row btn-group">
      <label class="col-sm-2 btn btn-category-A"><form:radiobutton path="category" value="A"/> 提案資料</label>
      <label class="col-sm-2 btn btn-category-B"><form:radiobutton path="category" value="B"/> 技術資料</label>
      <label class="col-sm-2 btn btn-category-C"><form:radiobutton path="category" value="C"/> 設定資料</label>
      <label class="col-sm-2 btn btn-category-D"><form:radiobutton path="category" value="D"/> 手順資料</label>
      <label class="col-sm-2 btn btn-category-E"><form:radiobutton path="category" value="E"/> 業務関連</label>
      </div>
    </div>

    <div class="row">
      <p class="col-sm-2 form-group">
        <label class="control-label" for="search_area">分野</label>
        <form:select id="search_area" path="area" class="form-control input-sm">
          <form:options items="${areas}"/>
        </form:select>
      </p>
      <p class="col-sm-2 form-group">
        <label class="control-label" for="search_purpose">ドキュメント種類</label>
        <form:select id="search_purpose" path="purpose" class="form-control input-sm">
          <form:options items="${purposes}"/>
        </form:select>
      </p>
      <p class="col-sm-3 form-group">
        <label class="control-label" for="search_customer">顧客名</label>
        <form:input type="text" id="search_customer" path="customerName" class="form-control input-sm"/>
      </p>
      <p class="col-sm-2 form-group">
        <label class="control-label" for="search_author">担当者</label>
        <form:input type="text" id="search_author" path="authorName" class="form-control input-sm"/>
      </p>
      <p class="col-sm-1 form-group">
        <label class="control-label" for="search_result">成否</label>
        <form:select id="search_result" path="result" class="form-control input-sm">
          <form:option value="*">&nbsp;</form:option>
          <form:options items="${results}"/>
        </form:select>
      </p>
      <p class="col-sm-2 form-group">
        <label class="control-label" for="search_reason">成否要因</label>
        <form:select id="search_reason" path="reason" class="form-control input-sm">
          <form:option value="*">&nbsp;</form:option>
          <form:options items="${reasons}"/>
        </form:select>
      </p>
    </div>

    <div class="row">
      <div class="col-sm-3">
        <label class="control-label" for="empNumber">従業員番号</label>
        <form:input type="text" id="empNumber" path="empNumber" class="form-control"/>
      </div>

      <div class="col-sm-3">
        <label class="control-label" for="description">コメント</label>
        <form:textarea id="description" path="description" class="form-control"/>
        <form:errors path="description"/>
      </div>

      <div class="col-sm-3">
        <label class="control-label" for="search_published_to">発行日</label>
        <form:input type="text" id="search_published_from" path="publishedDate" class="form-control"/>
      </div>

      <p class="col-sm-2 form-group">
        <label class="control-label">&nbsp;</label>
        <button type="submit" id="searchButton" class="form-control btn btn-primary">登録</button>
      </p>
    </div>

  </form:form>
</div>

</div>

<div id="footer" class="navbar navbar-default navbar-fixed-bottom">
</div>
</div>
<script src="/js/lib/modernizr.js"></script>
<script src="/js/lib/jquery-1.11.2.min.js"></script>
<script src="/js/lib/bootstrap.min.js"></script>
<script src="/js/lib/bootstrap-datepicker.min.js"></script>
<script src="/js/lib/bootstrap-datepicker.ja.min.js"></script>
<script src="/js/lib/pnotify.custom.min.js"></script>
<script>
$(function() {
  $.get("/link.jsp?" + Math.random(), function(data) {
    $("#site-link").html(data);
  });

  $.get("/footer.jsp?e", function(data) {
    $("#footer").html(data);
  });

  $('#search_published_from').datepicker({
    format: 'yyyy/mm/dd',
    language: 'ja'
  });

  $('#search_published_to').datepicker({
    format: 'yyyy/mm/dd',
    language: 'ja'
  });

});
</script>
</body>
</html>