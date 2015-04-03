var app = app || {};
app.AppView = Backbone.View.extend({
   	el: '#main',

    events: {
        "click #searchButton": "onSearch"
    },

    initialize: function () {
//            _.bindAll(this);
//            this.$title = $("#addForm [name='title']");
//            this.$content = $("#addForm [name='content']");
        this.$area     = this.$('#search_area');
        this.$purpose  = this.$('#search_purpose');
        this.$customer = this.$('#search_customer');
        this.$author   = this.$('#search_author');
        this.$result   = this.$('#search_result');
        this.$reason   = this.$('#search_reason');
        this.$keyword = this.$('#search_keyword');

        this.collection = new app.Documents();
        this.documentsView = new app.DocumentsView({el: $("#documents"), collection: this.collection});
        this.render();
    },

    render: function () {
//            this.$title.val('');
//            this.$content.val('');
    },

    /* methods */

    onSearch: function () {
    this.collection.url = "/rest/documents?text=" + this.$keyword.val();
//    this.documentsView.remove();
//    this.collection.remove(this.collection.models);
    this.collection.fetch();
    this.documentsView.empty();
    //this.documentsView = new app.DocumentsView({el: $("#documents"), collection: this.collection});
    /*
        var searchUrl = "/rest/documents?text=" + this.$keyword.val();
        $.ajax({
            url: searchUrl,
            type: "GET",
            context: this,
            dataType: "json",
            success: function (response) {
                console.log('onSearch success');

this.collection.reset([]);
                _.each(response, function (item) {
                    this.collection.add(new app.Document(item));
                    //this.documentsView.addDocumentView(new app.Document(item));
                    //console.log(JSON.stringify(item));
                }, this);

                // 検索後イベントを発生させる
                //this.trigger("searched");
            }
        });*/
    },

    onAdd: function () {
//            this.collection.create({title: this.$title.val(), content: this.$content.val()}, {wait: true});
//            this.render();
    }
});
