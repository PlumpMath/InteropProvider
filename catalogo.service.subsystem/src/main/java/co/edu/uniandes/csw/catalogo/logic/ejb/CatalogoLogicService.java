
package co.edu.uniandes.csw.catalogo.logic.ejb;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless; 
import javax.inject.Inject;

import javax.enterprise.inject.Default;

import co.edu.uniandes.csw.catalogo.logic.api.ICatalogoLogicService;

@Default
@Stateless
@LocalBean
public class CatalogoLogicService extends _CatalogoLogicService implements ICatalogoLogicService {

}