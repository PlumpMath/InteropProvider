
package co.edu.uniandes.csw.catalogo.logic.mock;
import javax.enterprise.inject.Alternative;
import javax.inject.Singleton;

import co.edu.uniandes.csw.catalogo.logic.api.ICatalogoLogicService;

@Alternative
@Singleton
public class CatalogoMockLogicService extends _CatalogoMockLogicService implements ICatalogoLogicService {
	
}