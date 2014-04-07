
package co.edu.uniandes.csw.catalogo.persistence;

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
import co.edu.uniandes.csw.catalogo.persistence.api.ICatalogoPersistence;
import co.edu.uniandes.csw.catalogo.persistence.entity.CatalogoEntity;

@RunWith(Arquillian.class)
public class CatalogoPersistenceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(CatalogoPersistence.class.getPackage())
				.addPackage(CatalogoEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private ICatalogoPersistence catalogoPersistence;

	@PersistenceContext
	private EntityManager em;

	@Inject
	UserTransaction utx;

	@Before
	public void configTest() {
		System.out.println("em: " + em);
		try {
			utx.begin();
			clearData();
			insertData();
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				utx.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private void clearData() {
		em.createQuery("delete from CatalogoEntity").executeUpdate();
	}

	private List<CatalogoEntity> data=new ArrayList<CatalogoEntity>();

	private void insertData() {
		for(int i=0;i<3;i++){
			CatalogoEntity entity=new CatalogoEntity();
			entity.setName(generateRandom(String.class));
			entity.setProductosId(generateRandom(Long.class));
			em.persist(entity);
			data.add(entity);
		}
	}
	
	@Test
	public void createCatalogoTest(){
		CatalogoDTO dto=new CatalogoDTO();
		dto.setName(generateRandom(String.class));
		dto.setProductosId(generateRandom(Long.class));
		
		
		CatalogoDTO result=catalogoPersistence.createCatalogo(dto);
		
		Assert.assertNotNull(result);
		
		CatalogoEntity entity=em.find(CatalogoEntity.class, result.getId());
		
		Assert.assertEquals(dto.getName(), entity.getName());	
		Assert.assertEquals(dto.getProductosId(), entity.getProductosId());	
	}
	
	@Test
	public void getCatalogosTest(){
		List<CatalogoDTO> list=catalogoPersistence.getCatalogos();
		Assert.assertEquals(list.size(), data.size());
        for(CatalogoDTO dto:list){
        	boolean found=false;
            for(CatalogoEntity entity:data){
            	if(dto.getId()==entity.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getCatalogoTest(){
		CatalogoEntity entity=data.get(0);
		CatalogoDTO dto=catalogoPersistence.getCatalogo(entity.getId());
        Assert.assertNotNull(dto);
		Assert.assertEquals(entity.getName(), dto.getName());
		Assert.assertEquals(entity.getProductosId(), dto.getProductosId());
        
	}
	
	@Test
	public void deleteCatalogoTest(){
		CatalogoEntity entity=data.get(0);
		catalogoPersistence.deleteCatalogo(entity.getId());
        CatalogoEntity deleted=em.find(CatalogoEntity.class, entity.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateCatalogoTest(){
		CatalogoEntity entity=data.get(0);
		
		CatalogoDTO dto=new CatalogoDTO();
		dto.setId(entity.getId());
		dto.setName(generateRandom(String.class));
		dto.setProductosId(generateRandom(Long.class));
		
		
		catalogoPersistence.updateCatalogo(dto);
		
		
		CatalogoEntity resp=em.find(CatalogoEntity.class, entity.getId());
		
		Assert.assertEquals(dto.getName(), resp.getName());	
		Assert.assertEquals(dto.getProductosId(), resp.getProductosId());	
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