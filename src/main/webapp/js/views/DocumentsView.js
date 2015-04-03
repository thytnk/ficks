var app = app || {};
app.DocumentsView = Backbone.View.extend({

    initialize: function () {
        this.listenTo(this.collection, "add", this.addDocumentView);
        var _this = this;
        this.collection.fetch({reset: true}).done(function () {
            _this.render();
        });
    },

    render: function () {
        this.collection.each(function (item) {
            this.addDocumentView(item);
        }, this);
        return this;
    },

    /* methods */
    empty: function() {
        this.$el.empty();
    },

    addDocumentView: function (item) {
        console.log('DocumentsView#addDocumentView');
        console.log(item);
        this.$el.append(new app.DocumentView({model: item}).render().el);
    }
});
