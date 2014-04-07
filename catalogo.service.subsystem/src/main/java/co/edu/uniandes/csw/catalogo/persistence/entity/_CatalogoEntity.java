
package co.edu.uniandes.csw.catalogo.persistence.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class _CatalogoEntity {

	@Id
	@GeneratedValue(generator = "Catalogo")
	private Long id;
	private String name;
	private Long productosId;

	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public Long getProductosId(){
		return productosId;
	}
	
	public void setProductosId(Long productosId){
		this.productosId = productosId;
	}
}