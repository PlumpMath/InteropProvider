
package co.edu.uniandes.csw.catalogo.persistence;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

import co.edu.uniandes.csw.catalogo.persistence.api.ICatalogoPersistence;
import javax.ejb.LocalBean;

@Default
@Stateless 
@LocalBean
public class CatalogoPersistence extends _CatalogoPersistence  implements ICatalogoPersistence {

}