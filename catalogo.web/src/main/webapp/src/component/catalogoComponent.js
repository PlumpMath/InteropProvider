define(['component/_CRUDComponent', 'controller/toolbarController', 'model/toolbarModel', 'model/catalogoModel', 'controller/catalogoController'], function() {
    App.Component.CatalogoComponent = App.Component._CRUDComponent.extend({
        name: 'catalogo',
        model: App.Model.CatalogoModel,
        listModel: App.Model.CatalogoList,
        controller : App.Controller.CatalogoController
    });
    return App.Component.CatalogoComponent;
});