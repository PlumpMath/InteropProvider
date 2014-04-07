
package co.edu.uniandes.csw.catalogo.logic.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public abstract class _CatalogoDTO {

	private Long id;
	private String name;
	private Long productosId;

	public Long getId() {
		return id;
	}
 
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
	public Long getProductosId() {
		return productosId;
	}
 
	public void setProductosId(Long productosid) {
		this.productosId = productosid;
	}
	
}