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
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">

    <title>FICKS: FSL Intranet Community Knowledge management System</title>
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
    <!--
    <link rel="stylesheet" href="/css/screen.css">
    -->
    <style>
    <jsp:include page="css.jsp"/>
    </style>
    <!--[if lt IE 9]>
    <script src="//cdn.jsdelivr.net/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="/js/lib/jquery-1.11.2.min.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
</head>
<body>

<header>
    <h1><a href="/documents"><img src="/img/FICKS.png" alt="FICKS:"></a> <b>F</b>SL <b>I</b>ntranet <b>C</b>ommunity <b>K</b>nowledge management <b>S</b>ystem</h1>
</header>

<div id="main">
<form:form id="search" action="/documents/search" method="GET">

    <div id="categories">
        <label>提案資料<form:checkbox path="category" value="A"/></label>
        <label>技術資料<form:checkbox path="category" value="B"/></label>
        <label>設定資料<form:checkbox path="category" value="C"/></label>
        <label>手順資料<form:checkbox path="category" value="D"/></label>
        <label>業務窓口<form:checkbox path="category" value="E"/></label>
    </div>

    <hr>

    <p>
        <label for="search_area">分野</label>
        <form:select id="search_area" path="area">
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
    <p>
        <label for="search_purpose">ドキュメント種類</label>
        <form:select id="search_purpose" path="purpose">
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
    <p>
        <label for="search_customer">顧客名</label>
        <form:input type="text" id="search_customer" path="customer"/>
    </p>
    <p>
        <label for="search_author">担当者</label>
        <form:input type="text" id="search_author" path="author"/>
    </p>
    <p>
        <label for="search_result">成否</label>
        <form:select id="search_result" path="result">
            <form:option value="*">すべて</form:option>
            <form:option value="1">成功</form:option>
            <form:option value="0">失敗</form:option>
            <form:option value="9">なし</form:option>
        </form:select>
    </p>
    <p>
        <label for="search_reason">成否要因</label>
        <form:select id="search_reason" path="reason">
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
    <p>
        <label for="search_published_from">発行日または期間</label>
        <form:input type="date" id="search_published_from" path="publishedFrom"/>～<form:input type="date" id="search_published_to" path="publishedTo"/>
    </p>
    <p>
        <label for="search_keyword">キーワード</label>
        <form:input type="search" id="search_keyword" path="keyword"/>
    </p>

    <p><button type="submit" id="searchButton">検索</button></p>
</form:form>

<hr>

<div class="container">
    <ul id="documents">

<c:forEach var="doc" items="${list}">
    <li>
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
            <a href="download?file=<c:out value="${doc.id}"/>"><img src="#" alt="NO IMAGE"></a>
        </li>
</ul>
</li>
</c:forEach>

    </ul>
</div>

<aside><iframe src="" frameborder="0"></iframe></aside>

<footer><iframe src="" frameborder="0"></iframe></footer>

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
})();
</script>
</div>
</body>
</html>