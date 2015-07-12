$(function() {
/*
  $.get("/link.jsp?" + Math.random(), function(data) {
    $("#site-link").html(data);
  });

  $.get("/footer.jsp?e", function(data) {
    $("#footer").html(data);
  });
*/
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

  $('#searchButton').click(function() {
    $('#page').val(0);
  });

  $('.next-page').click(function() {
    $('#page').val(parseInt($('#page').val(), 10) + 1);
    $('#search').submit();
  });

  $('.prev-page').click(function() {
    $("#page").val(parseInt($("#page").val(), 10) - 1);
    $('#search').submit();
  });

  $('#area-facet a').click(function() {
    $('#search_area').val($(this).attr("data-facet-value"));
    $('#searchButton').click();
  });

  $('#purpose-facet a').click(function() {
    $('#search_purpose').val($(this).attr("data-facet-value"));
    $('#searchButton').click();
  });
});