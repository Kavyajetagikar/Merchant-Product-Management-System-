package org.jsp.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.jsp.DTO.Merchant;
import org.jsp.DTO.Product;

public class ProductDao {
	EntityManagerFactory fac = Persistence.createEntityManagerFactory("dev");
	EntityManager man = fac.createEntityManager();
	
	public Product addProduct(int mid, Product p) {
		EntityTransaction tran = man.getTransaction();
		tran.begin();
		Merchant mdb=man.find(Merchant.class, mid);
		if(mdb!=null) {
			p.setMerchant(mdb);
			mdb.getProducts().add(p);
			man.persist(p);
			tran.commit();
			return p;
		}
		else {
			return null;
		}
	}
	
	public List<Product> findProductByMerchantId(int mid) {
		Query q = man.createQuery("select m.products from Merchant m where m.id=?1");
		q.setParameter(1, mid);
		List<Product> lp=q.getResultList();
		return lp;
	}

	public Product updateProduct(Product p) {
		EntityTransaction tran = man.getTransaction();
		tran.begin();
		Product pdb=man.find(Product.class, p.getId());
		if(pdb!=null) {
			pdb.setName(p.getName());
			pdb.setBrand(p.getBrand());
			pdb.setCategory(p.getCategory());
			pdb.setCost(p.getCost());
			tran.commit();
			return pdb;
		}
		else {
			return null;
		}
		
	}

	public List<Product> findProductByBrand(String br) {
		Query q = man.createQuery("select p from Product p where p.brand=?1");
		q.setParameter(1, br);
		List<Product> lpList=q.getResultList();
		return lpList;
		
	}

	public List<Product> findProductByCategory(String category) {
		Query q = man.createQuery("select p from Product p where p.category=?1");
		q.setParameter(1, category);
		List<Product> lpList=q.getResultList();
		return lpList;
	}

}