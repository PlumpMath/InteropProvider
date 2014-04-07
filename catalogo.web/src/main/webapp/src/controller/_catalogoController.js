define(['model/catalogoModel'], function(catalogoModel) {
    App.Controller._CatalogoController = Backbone.View.extend({
        initialize: function(options) {
            this.modelClass = options.modelClass;
            this.listModelClass = options.listModelClass;
            this.showEdit = true;
            this.showDelete = true;
            this.editTemplate = _.template($('#catalogo').html());
            this.listTemplate = _.template($('#catalogoList').html());
            if (!options || !options.componentId) {
                this.componentId = _.random(0, 100) + "";
            }else{
				this.componentId = options.componentId;
		    }
            var self = this;
            Backbone.on(this.componentId + '-' + 'catalogo-create', function(params) {
                self.create(params);
            });
            Backbone.on(this.componentId + '-' + 'catalogo-list', function(params) {
                self.list(params);
            });
            Backbone.on(this.componentId + '-' + 'catalogo-edit', function(params) {
                self.edit(params);
            });
            Backbone.on(this.componentId + '-' + 'catalogo-delete', function(params) {
                self.destroy(params);
            });
            Backbone.on(this.componentId + '-' + 'post-catalogo-delete', function(params) {
                self.list(params);
            });
            Backbone.on(this.componentId + '-' + 'catalogo-save', function(params) {
                self.save(params);
            });
            if(self.postInit){
            	self.postInit();
            }
        },
        create: function() {
            if (App.Utils.eventExists(this.componentId + '-' +'instead-catalogo-create')) {
                Backbone.trigger(this.componentId + '-' + 'instead-catalogo-create', {view: this});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-catalogo-create', {view: this});
                this.currentCatalogoModel = new this.modelClass();
                this._renderEdit();
                Backbone.trigger(this.componentId + '-' + 'post-catalogo-create', {view: this});
            }
        },
        list: function(params) {
            if (params) {
                var data = params.data;
            }
            if (App.Utils.eventExists(this.componentId + '-' +'instead-catalogo-list')) {
                Backbone.trigger(this.componentId + '-' + 'instead-catalogo-list', {view: this, data: data});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-catalogo-list', {view: this, data: data});
                var self = this;
				if(!this.catalogoModelList){
                 this.catalogoModelList = new this.listModelClass();
				}
                this.catalogoModelList.fetch({
                    data: data,
                    success: function() {
                        self._renderList();
                        Backbone.trigger(self.componentId + '-' + 'post-catalogo-list', {view: self});
                    },
                    error: function(mode, error) {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'catalogo-list', view: self, error: error});
                    }
                });
            }
        },
        edit: function(params) {
            var id = params.id;
            var data = params.data;
            if (App.Utils.eventExists(this.componentId + '-' +'instead-catalogo-edit')) {
                Backbone.trigger(this.componentId + '-' + 'instead-catalogo-edit', {view: this, id: id, data: data});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-catalogo-edit', {view: this, id: id, data: data});
                if (this.catalogoModelList) {
                    this.currentCatalogoModel = this.catalogoModelList.get(id);
                    this._renderEdit();
                    Backbone.trigger(this.componentId + '-' + 'post-catalogo-edit', {view: this, id: id, data: data});
                } else {
                    var self = this;
                    this.currentCatalogoModel = new this.modelClass({id: id});
                    this.currentCatalogoModel.fetch({
                        data: data,
                        success: function() {
                            self._renderEdit();
                            Backbone.trigger(self.componentId + '-' + 'post-catalogo-edit', {view: this, id: id, data: data});
                        },
                        error: function() {
                            Backbone.trigger(self.componentId + '-' + 'error', {event: 'catalogo-edit', view: self, id: id, data: data, error: error});
                        }
                    });
                }
            }
        },
        destroy: function(params) {
            var id = params.id;
            var self = this;
            if (App.Utils.eventExists(this.componentId + '-' +'instead-catalogo-delete')) {
                Backbone.trigger(this.componentId + '-' + 'instead-catalogo-delete', {view: this, id: id});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-catalogo-delete', {view: this, id: id});
                var deleteModel;
                if (this.catalogoModelList) {
                    deleteModel = this.catalogoModelList.get(id);
                } else {
                    deleteModel = new this.modelClass({id: id});
                }
                deleteModel.destroy({
                    success: function() {
                        Backbone.trigger(self.componentId + '-' + 'post-catalogo-delete', {view: self, model: deleteModel});
                    },
                    error: function() {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'catalogo-delete', view: self, error: error});
                    }
                });
            }
        },
		_loadRequiredComponentsData: function(callBack) {
            var self = this;
            var listReady = _.after(1, function(){
                callBack();
            }); 
            var listDataReady = function(componentName, model){
                self[componentName] = model;
                listReady();
            };
				App.Utils.getComponentList('productoComponent',listDataReady);
        },
        save: function() {
            var self = this;
            var model = $('#' + this.componentId + '-catalogoForm').serializeObject();
            if (App.Utils.eventExists(this.componentId + '-' +'instead-catalogo-save')) {
                Backbone.trigger(this.componentId + '-' + 'instead-catalogo-save', {view: this, model : model});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-catalogo-save', {view: this, model : model});
                this.currentCatalogoModel.set(model);
                this.currentCatalogoModel.save({},
                        {
                            success: function(model) {
                                Backbone.trigger(self.componentId + '-' + 'post-catalogo-save', {model: self.currentCatalogoModel});
                            },
                            error: function(error) {
                                Backbone.trigger(self.componentId + '-' + 'error', {event: 'catalogo-save', view: self, error: error});
                            }
                        });
            }
        },
        _renderList: function() {
            var self = this;
            this.$el.slideUp("fast", function() {
                self.$el.html(self.listTemplate({catalogos: self.catalogoModelList.models, componentId: self.componentId, showEdit : self.showEdit , showDelete : self.showDelete}));
                self.$el.slideDown("fast");
            });
        },
        _renderEdit: function() {
            var self = this;
            this.$el.slideUp("fast", function() {
                self.$el.html(self.editTemplate({catalogo: self.currentCatalogoModel, componentId: self.componentId , showEdit : self.showEdit , showDelete : self.showDelete
 
				    ,producto: self.productoComponent
 
				}));
                self.$el.slideDown("fast");
            });
        }
    });
    return App.Controller._CatalogoController;
});