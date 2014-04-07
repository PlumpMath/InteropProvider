
package co.edu.uniandes.csw.producto.persistence.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class _ProductoEntity {

	@Id
	@GeneratedValue(generator = "Producto")
	private Long id;
	private String name;
	private String image;
	private String description;
	private Long price;

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
	public String getImage(){
		return image;
	}
	
	public void setImage(String image){
		this.image = image;
	}
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	public Long getPrice(){
		return price;
	}
	
	public void setPrice(Long price){
		this.price = price;
	}
}