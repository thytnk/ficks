@charset utf-8;

h1 {
  font: italic 1.4em "Times New Roman";
}
h2 {
   font-size: 1.5em;
}
.col-sm-1, .col-sm-2, .col-sm-3, .col-sm-4, .col-sm-5
{ padding-left: 7px; padding-right: 7px; }

h1 b { font-weight:normal; color: red;}

body {
  font-family: "Hiragino Kaku Gothic ProN", Meiryo, sans-serif;
  padding-top: 70px;
  padding-bottom:70px;
}

pre {
  font-family: "Hiragino Kaku Gothic ProN", Meiryo, sans-serif;
}

#search {
  background: beige;
  padding: 15px 15px 0px 15px;
  border-radius: 5px;
  border: 1px solid palegoldenrod;
}

li { list-style: none; }
iframe { height: 100%; width: 100%; border-radius: 5px;}

#categories label {
    color: white;
/*    height: 54px;
    padding-top: 15px;*/
    text-align: center;
/*    width: 96px;*/
  margin-bottom: 10px;
}

.category-A { background:crimson; }
/*
#categories label:nth-child(4n+1) { background:crimson;  }
#categories label:nth-child(4n+2) { background:darkslateblue; }
#categories label:nth-child(4n+3) { background:darkorange;}
#categories label:nth-child(4n+0) { background:limegreen;}
*/
#categories label.gray { background: gray; border-color:gray; color:silver;}

/*
aside>iframe { width: 100%; }
*/

.doc_author { color: gray; }

.doc_thumbnail {
    background: silver;
    height: 90px;
    width: 160px;
    text-align: center;
}

.doc_thumbnail a {
    height: 100%;
    width: 100%;
}

.doc_thumbnail img {
    max-height: 144px;
    max-width: 256px;
}

/*----------------------------------------------------------------------*/
.btn-category-A {
  color: #ffffff;
  background-color: #337ab7;
  border-color: #2e6da4;
}
.btn-category-A:hover,
.btn-category-A:focus,
.btn-category-A.focus,
.btn-category-A:active,
.btn-category-A.active,
.open > .dropdown-toggle.btn-category-A {
  color: #ffffff;
  background-color: #286090;
  border-color: #204d74;
}
.btn-category-A:active,
.btn-category-A.active,
.open > .dropdown-toggle.btn-category-A {
  background-image: none;
}
.btn-category-B {
  color: #ffffff;
  background-color: #5cb85c;
  border-color: #4cae4c;
}
.btn-category-B:hover,
.btn-category-B:focus,
.btn-category-B.focus,
.btn-category-B:active,
.btn-category-B.active,
.open > .dropdown-toggle.btn-category-B {
  color: #ffffff;
  background-color: #449d44;
  border-color: #398439;
}
.btn-category-B:active,
.btn-category-B.active,
.open > .dropdown-toggle.btn-category-B {
  background-image: none;
}
.btn-category-C {
  color: #ffffff;
  background-color: #5bc0de;
  border-color: #46b8da;
}
.btn-category-C:hover,
.btn-category-C:focus,
.btn-category-C.focus,
.btn-category-C:active,
.btn-category-C.active,
.open > .dropdown-toggle.btn-category-C {
  color: #ffffff;
  background-color: #31b0d5;
  border-color: #269abc;
}
.btn-category-C:active,
.btn-category-C.active,
.open > .dropdown-toggle.btn-category-C {
  background-image: none;
}
.btn-category-D {
  color: #ffffff;
  background-color: #f0ad4e;
  border-color: #eea236;
}
.btn-category-D:hover,
.btn-category-D:focus,
.btn-category-D.focus,
.btn-category-D:active,
.btn-category-D.active,
.open > .dropdown-toggle.btn-category-D {
  color: #ffffff;
  background-color: #ec971f;
  border-color: #d58512;
}
.btn-category-D:active,
.btn-category-D.active,
.open > .dropdown-toggle.btn-category-D {
  background-image: none;
}
.btn-category-E {
  color: #ffffff;
  background-color: #d9534f;
  border-color: #d43f3a;
}
.btn-category-E:hover,
.btn-category-E:focus,
.btn-category-E.focus,
.btn-category-E:active,
.btn-category-E.active,
.open > .dropdown-toggle.btn-category-E {
  color: #ffffff;
  background-color: #c9302c;
  border-color: #ac2925;
}
.btn-category-E:active,
.btn-category-E.active,
.open > .dropdown-toggle.btn-category-E {
  background-image: none;
}


.label-category-A {
  background-color: #337ab7;
}
.label-category-A[href]:hover,
.label-category-A[href]:focus {
  background-color: #286090;
}
.label-category-B {
  background-color: #5cb85c;
}
.label-category-B[href]:hover,
.label-category-B[href]:focus {
  background-color: #449d44;
}
.label-category-C {
  background-color: #5bc0de;
}
.label-category-C[href]:hover,
.label-category-C[href]:focus {
  background-color: #31b0d5;
}
.label-category-D {
  background-color: #f0ad4e;
}
.label-category-D[href]:hover,
.label-category-D[href]:focus {
  background-color: #ec971f;
}
.label-category-E {
  background-color: #d9534f;
}
.label-category-E[href]:hover,
.label-category-E[href]:focus {
  background-color: #c9302c;
}

