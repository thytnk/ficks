var app = app || {};
app.Documents = Backbone.Collection.extend({
    model: app.Document,
    url: "/rest/documents/?text=虫"
//    localStorage: new Backbone.LocalStorage("ficks-backbone"),
});
