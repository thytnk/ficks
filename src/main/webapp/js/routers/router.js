var Workspace = Backbone.Router.extend({
	routes: {
		'*filter': 'setFilter'
	},

	setFilter: function(param) {
	        console.log(param);

		app.DocumentFilter = param || '';
		window.app.Documents.trigger('filter');
	}
});

app.TodoRouter = new Workspace();
Backbone.history.start();