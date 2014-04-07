
package co.edu.uniandes.csw.catalogo.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.edu.uniandes.csw.catalogo.logic.dto.CatalogoDTO;
import co.edu.uniandes.csw.catalogo.persistence.api._ICatalogoPersistence;
import co.edu.uniandes.csw.catalogo.persistence.converter.CatalogoConverter;
import co.edu.uniandes.csw.catalogo.persistence.entity.CatalogoEntity;

public abstract class _CatalogoPersistence implements _ICatalogoPersistence {

	@PersistenceContext(unitName="CatalogoPU")
	protected EntityManager entityManager;
	
	public CatalogoDTO createCatalogo(CatalogoDTO catalogo) {
		CatalogoEntity entity=CatalogoConverter.persistenceDTO2Entity(catalogo);
		entityManager.persist(entity);
		return CatalogoConverter.entity2PersistenceDTO(entity);
	}

	@SuppressWarnings("unchecked")
	public List<CatalogoDTO> getCatalogos() {
		Query q = entityManager.createQuery("select u from CatalogoEntity u");
		return CatalogoConverter.entity2PersistenceDTOList(q.getResultList());
	}

	public CatalogoDTO getCatalogo(Long id) {
		return CatalogoConverter.entity2PersistenceDTO(entityManager.find(CatalogoEntity.class, id));
	}

	public void deleteCatalogo(Long id) {
		CatalogoEntity entity=entityManager.find(CatalogoEntity.class, id);
		entityManager.remove(entity);
	}

	public void updateCatalogo(CatalogoDTO detail) {
		CatalogoEntity entity=entityManager.merge(CatalogoConverter.persistenceDTO2Entity(detail));
		CatalogoConverter.entity2PersistenceDTO(entity);
	}

}