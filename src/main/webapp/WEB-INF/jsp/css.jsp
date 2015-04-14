@charset utf-8;

h1   { font: italic 1.5em "Times New Roman" !important; }
h1>b { font-weight:normal; color: red;}
h2   { font-size: 1.3em; }

.form-group { padding-right: 5px;  }

body {
  font-family: "Hiragino Kaku Gothic ProN", Meiryo, sans-serif;
  padding-top: 70px;
  padding-bottom:70px;
}

pre {
  font-family: "Hiragino Kaku Gothic ProN", Meiryo, sans-serif;
  padding: 5px 10px;
}

#search {
  background: beige;
  padding: 15px 15px 0px 15px;
  border-radius: 5px;
  border: 1px solid palegoldenrod;
}


#categories label {
  color: white;
  text-align: center;
  margin-bottom: 10px;
}

#categories label.gray { background: gray; border-color:gray; color:silver;}

#documents label { margin-right: 3px; }

.doc_author { color: gray; }

.doc_thumbnail {
  padding-left : 5px;
  text-align: center;
}

.doc_thumbnail img {
  height: 100%;
  width: 100%;
}

.download-link {
  position:relative;
  max-height: 135px;
  width: 100%;
}

.download-link p {
  position: absolute;
  top: 35%;
  text-align:center;
  width: 85%;
  z-index: 10;/*background:yellow;*/
  color: gray;
  font-size: 4em;
}

.download-link:hover p {
  color:#337ab7;

}

.glyphicon-download-alt { opacity: 0.7; }

#footer {
  color: gray;
  text-align: right;
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

