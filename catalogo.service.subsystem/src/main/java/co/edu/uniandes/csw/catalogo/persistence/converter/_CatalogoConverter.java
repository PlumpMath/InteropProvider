
package co.edu.uniandes.csw.catalogo.persistence.converter;

import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.csw.catalogo.logic.dto.CatalogoDTO;
import co.edu.uniandes.csw.catalogo.persistence.entity.CatalogoEntity;

public abstract class _CatalogoConverter {


	public static CatalogoDTO entity2PersistenceDTO(CatalogoEntity entity){
		if (entity != null) {
			CatalogoDTO dto = new CatalogoDTO();
				dto.setId(entity.getId());
				dto.setName(entity.getName());
				dto.setProductosId(entity.getProductosId());
			return dto;
		}else{
			return null;
		}
	}
	
	public static CatalogoEntity persistenceDTO2Entity(CatalogoDTO dto){
		if(dto!=null){
			CatalogoEntity entity=new CatalogoEntity();
			entity.setId(dto.getId());
			entity.setName(dto.getName());
			entity.setProductosId(dto.getProductosId());
			return entity;
		}else {
			return null;
		}
	}
	
	public static List<CatalogoDTO> entity2PersistenceDTOList(List<CatalogoEntity> entities){
		List<CatalogoDTO> dtos=new ArrayList<CatalogoDTO>();
		for(CatalogoEntity entity:entities){
			dtos.add(entity2PersistenceDTO(entity));
		}
		return dtos;
	}
	
	public static List<CatalogoEntity> persistenceDTO2EntityList(List<CatalogoDTO> dtos){
		List<CatalogoEntity> entities=new ArrayList<CatalogoEntity>();
		for(CatalogoDTO dto:dtos){
			entities.add(persistenceDTO2Entity(dto));
		}
		return entities;
	}
}