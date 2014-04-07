
package co.edu.uniandes.csw.catalogo.logic.ejb;
import java.util.List;
import javax.inject.Inject;

import co.edu.uniandes.csw.catalogo.logic.dto.CatalogoDTO;
import co.edu.uniandes.csw.catalogo.logic.api._ICatalogoLogicService;
import co.edu.uniandes.csw.catalogo.persistence.api.ICatalogoPersistence;

public abstract class _CatalogoLogicService implements _ICatalogoLogicService {

	@Inject
	protected ICatalogoPersistence persistance;

	public CatalogoDTO createCatalogo(CatalogoDTO catalogo){
		return persistance.createCatalogo( catalogo); 
    }

	public List<CatalogoDTO> getCatalogos(){
		return persistance.getCatalogos(); 
	}

	public CatalogoDTO getCatalogo(Long id){
		return persistance.getCatalogo(id); 
	}

	public void deleteCatalogo(Long id){
	    persistance.deleteCatalogo(id); 
	}

	public void updateCatalogo(CatalogoDTO catalogo){
	    persistance.updateCatalogo(catalogo); 
	}	
}