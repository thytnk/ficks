var app = app || {};
app.DocumentView = Backbone.View.extend({
    tmpl: _.template($("#tmpl-docview").html()),
    events: {
//            "click .delete": "onDelete"
    },

    initialize: function () {
//            _.bindAll(this);
        this.listenTo(this.model, "change", this.render);
        this.listenTo(this.model, "destroy", this.onDestroy);
    },

    onDelete: function () {
        this.model.destroy();
    },

    onDestroy: function () {
        this.remove();
    },

    render: function () {

        this.$el.html(this.tmpl(this.model.toJSON()));
        return this;
    }
});
