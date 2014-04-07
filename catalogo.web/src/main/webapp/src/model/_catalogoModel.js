define([], function() {
    App.Model._CatalogoModel = Backbone.Model.extend({
        defaults: {
 
		 'name' : ''
 ,  
		 'productosId' : ''
        },
        initialize: function() {
        },
        getDisplay: function(name) {
			 if(name=='productosId'){  
                 var value = App.Utils.getModelFromCache('productoComponent',this.get('productosId'));
                 if(value) 
                 return value.get('name');
             }
         return this.get(name);
        }
    });

    App.Model._CatalogoList = Backbone.Collection.extend({
        model: App.Model._CatalogoModel,
        initialize: function() {
        }

    });
    return App.Model._CatalogoModel;
});