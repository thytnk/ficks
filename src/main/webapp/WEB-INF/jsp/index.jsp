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
  <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
  <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
  <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

  <style><jsp:include page="css.jsp"/></style>

  <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body>
<div class="container">
<div id="header">
  <h1><a href="/documents"><img src="/img/FICKS.png" alt="FICKS:"></a> <b>F</b>SL <b>I</b>ntranet <b>C</b>ommunity <b>K</b>nowledge management <b>S</b>ystem</h1>
</div>

<hr>

<div class="row">
  <form:form id="search" action="/documents/search" method="GET">
  
    <div id="categories" class="row container btn-group">
      <label class="col-sm-1 btn btn-primary">提案資料<form:checkbox path="category" value="A" class="hidden"/></label>
      <label class="col-sm-1 btn btn-success">技術資料<form:checkbox path="category" value="B" class="hidden"/></label>
      <label class="col-sm-1 btn btn-info">設定資料<form:checkbox path="category" value="C" class="hidden"/></label>
      <label class="col-sm-1 btn btn-warning">手順資料<form:checkbox path="category" value="D" class="hidden"/></label>
      <label class="col-sm-1 btn btn-danger">業務窓口<form:checkbox path="category" value="E" class="hidden"/></label>
    </div>
  
    <div class="row">
      <p class="col-sm-2 form-group">
        <label class="control-label" for="search_area">分野</label>
        <form:select id="search_area" path="area" class="form-control input-sm">
          <form:option value="*">すべて</form:option>
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
          <form:option value="*">すべて</form:option>
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
          <form:option value="*">すべて</form:option>
          <form:option value="1">成功</form:option>
          <form:option value="0">失敗</form:option>
          <form:option value="9">なし</form:option>
        </form:select>
      </p>
      <p class="col-sm-2 form-group">
        <label class="control-label" for="search_reason">成否要因</label>
        <form:select id="search_reason" path="reason" class="form-control input-sm">
          <form:option value="*">すべて</form:option>
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
        <div>
          <div class="input-group">
            <form:input type="date" id="search_published_from" path="publishedFrom" class="form-control"/>
            <span class="input-group-addon">～</span>
            <form:input type="date" id="search_published_to" path="publishedTo" class="form-control"/>
          </div>
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


    <div class="row">
    </div>            

  </form:form>
</div>

<hr>

<div class="row">

  <div class="col-sm-2"><iframe src=""></iframe></div>

  <div class="col-sm-10">
    <div class="alert alert-info">
      検索結果:<c:out value="${list.totalElements}"/>件
    </div>

    <ul id="documents" class="list-group">

      <c:forEach var="doc" items="${list.content}">
      <li class="list-group-item row">
        <ul>
          <li class="doc_code">
            <p class="caption">管理番号:</p>
            <p><c:out value="${doc.code}"/></p>
          </li>
  
          <li class="doc_filename">
            <p class="caption">ドキュメント名:</p>
            <p class="doc_filename"><c:out value="${doc.fileName}"/></p>
          </li>
  
          <li class="doc_published_date">
            <p class="caption">発行日:</p>
            <p class="doc_published_date"><fmt:formatDate value="${doc.publishedDate}"/></p>
          </li>
  
          <li class="doc_category">
            <p class="caption">種別:</p>
            <p><c:out value="${categories[doc.category]}"/></p>
          </li>
  
          <li class="doc_area">
            <p class="caption">分野:</p>
            <p><c:out value="${areas[doc.area]}"/></p>
          </li>
          <li class="doc_purpose">
            <p class="caption">ドキュメント種類:</p>
            <p><c:out value="${purposes[doc.purpose]}"/></p>
          </li>
  
          <li class="doc_customer_name">
            <p class="caption">顧客名:</p>
            <p><c:out value="${doc.customerName}"/></p>
          </li>
  
          <li class="doc_author_name">
            <p class="caption">担当者:</p>
            <p><c:out value="${doc.authorName}"/></p>
          </li>
  
          <li class="doc_result">
            <p class="caption">成否:</p>
            <p><c:out value="${results[doc.result]} (${reasons[doc.reason]})"/></p>
          </li>
  
          <li class="doc_comment">
            <p class="caption">コメント:</p>
            <p><c:out value="${doc.description}"/></p>
          </li>
  
          <li class="doc_thumbnail">
            <a href="download?file=<c:out value="${doc.id}"/>"><img src="#" alt="NO IMAGE" class="img-thumbnail"></a>
          </li>
        </ul>
      </li>
      </c:forEach>
    </ul>
  </div>
</div>

<div id="footer" class="row">
  <iframe src=""></iframe>
</div>
</div>

<script src="/js/lib/jquery-1.11.2.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script src="/js/lib/bootstrap-datepicker.min.js"></script>
<script src="/js/lib/bootstrap-datepicker.ja.min.js"></script>
<script>
$(function() {
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
/*
  $('#search_published_from').datepicker({
    format: 'yyyy/mm/dd',
    language: 'ja'
  });

  $('#search_published_to').datepicker({
    format: 'yyyy/mm/dd',
    language: 'ja'
  });*/
});
</script>

</body>
</html>