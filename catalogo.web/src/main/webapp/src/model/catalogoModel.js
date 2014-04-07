define(['model/_catalogoModel'], function() {
    App.Model.CatalogoModel = App.Model._CatalogoModel.extend({

    });

    App.Model.CatalogoList = App.Model._CatalogoList.extend({
        model: App.Model.CatalogoModel
    });

    return  App.Model.CatalogoModel;

});