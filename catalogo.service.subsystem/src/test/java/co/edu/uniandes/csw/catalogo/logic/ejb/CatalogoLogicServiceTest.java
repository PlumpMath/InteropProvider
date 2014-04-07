
package co.edu.uniandes.csw.catalogo.logic.ejb;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.*;


import co.edu.uniandes.csw.catalogo.logic.dto.CatalogoDTO;
import co.edu.uniandes.csw.catalogo.logic.api.ICatalogoLogicService;
import co.edu.uniandes.csw.catalogo.persistence.CatalogoPersistence;
import co.edu.uniandes.csw.catalogo.persistence.api.ICatalogoPersistence;
import co.edu.uniandes.csw.catalogo.persistence.entity.CatalogoEntity;

@RunWith(Arquillian.class)
public class CatalogoLogicServiceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(CatalogoLogicService.class.getPackage())
				.addPackage(CatalogoPersistence.class.getPackage())
				.addPackage(CatalogoEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private ICatalogoLogicService catalogoLogicService;
	
	@Inject
	private ICatalogoPersistence catalogoPersistence;	

	@Before
	public void configTest() {
		try {
			clearData();
			insertData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void clearData() {
		List<CatalogoDTO> dtos=catalogoPersistence.getCatalogos();
		for(CatalogoDTO dto:dtos){
			catalogoPersistence.deleteCatalogo(dto.getId());
		}
	}

	private List<CatalogoDTO> data=new ArrayList<CatalogoDTO>();

	private void insertData() {
		for(int i=0;i<3;i++){
			CatalogoDTO pdto=new CatalogoDTO();
			pdto.setName(generateRandom(String.class));
			pdto.setProductosId(generateRandom(Long.class));
			pdto=catalogoPersistence.createCatalogo(pdto);
			data.add(pdto);
		}
	}
	
	@Test
	public void createCatalogoTest(){
		CatalogoDTO ldto=new CatalogoDTO();
		ldto.setName(generateRandom(String.class));
		ldto.setProductosId(generateRandom(Long.class));
		
		
		CatalogoDTO result=catalogoLogicService.createCatalogo(ldto);
		
		Assert.assertNotNull(result);
		
		CatalogoDTO pdto=catalogoPersistence.getCatalogo(result.getId());
		
		Assert.assertEquals(ldto.getName(), pdto.getName());	
		Assert.assertEquals(ldto.getProductosId(), pdto.getProductosId());	
	}
	
	@Test
	public void getCatalogosTest(){
		List<CatalogoDTO> list=catalogoLogicService.getCatalogos();
		Assert.assertEquals(list.size(), data.size());
        for(CatalogoDTO ldto:list){
        	boolean found=false;
            for(CatalogoDTO pdto:data){
            	if(ldto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getCatalogoTest(){
		CatalogoDTO pdto=data.get(0);
		CatalogoDTO ldto=catalogoLogicService.getCatalogo(pdto.getId());
        Assert.assertNotNull(ldto);
		Assert.assertEquals(pdto.getName(), ldto.getName());
		Assert.assertEquals(pdto.getProductosId(), ldto.getProductosId());
        
	}
	
	@Test
	public void deleteCatalogoTest(){
		CatalogoDTO pdto=data.get(0);
		catalogoLogicService.deleteCatalogo(pdto.getId());
        CatalogoDTO deleted=catalogoPersistence.getCatalogo(pdto.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateCatalogoTest(){
		CatalogoDTO pdto=data.get(0);
		
		CatalogoDTO ldto=new CatalogoDTO();
		ldto.setId(pdto.getId());
		ldto.setName(generateRandom(String.class));
		ldto.setProductosId(generateRandom(Long.class));
		
		
		catalogoLogicService.updateCatalogo(ldto);
		
		
		CatalogoDTO resp=catalogoPersistence.getCatalogo(pdto.getId());
		
		Assert.assertEquals(ldto.getName(), resp.getName());	
		Assert.assertEquals(ldto.getProductosId(), resp.getProductosId());	
	}
	
	public <T> T generateRandom(Class<T> objectClass){
		Random r=new Random();
		if(objectClass.isInstance(String.class)){
			String s="";
			for(int i=0;i<10;i++){
				char c=(char)(r.nextInt()/('Z'-'A')+'A');
				s=s+c;
			}
			return objectClass.cast(s);
		}else if(objectClass.isInstance(Integer.class)){
			Integer s=r.nextInt();
			return objectClass.cast(s);
		}else if(objectClass.isInstance(Long.class)){
			Long s=r.nextLong();
			return objectClass.cast(s);
		}else if(objectClass.isInstance(java.util.Date.class)){
			java.util.Calendar c=java.util.Calendar.getInstance();
			c.set(java.util.Calendar.MONTH, r.nextInt()/12);
			c.set(java.util.Calendar.DAY_OF_MONTH,r.nextInt()/30);
			c.setLenient(false);
			return objectClass.cast(c.getTime());
		} 
		return null;
	}
	
}