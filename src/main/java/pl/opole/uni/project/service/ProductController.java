package pl.opole.uni.project.service;


import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pl.opole.uni.project.model.Product;
import pl.opole.uni.project.model.ProductRepository;

@Scope (value = "session")
@Component (value = "productController")
@ELBeanName(value = "productController")
@Join(path = "/products", to = "/productTable.xhtml")
public class ProductController  implements Converter{
    
   
	@Autowired
    private ProductRepository productRepository;

    private List<Product> products;
    private Product product;
    private String mode;
    
	public void add() {
		product = new Product();  
        mode = "add";
    }
    
    public void edit() {
    	mode = "edit";
    }
    
    public void save() {
        productRepository.save(product);
        if (mode.equals("add"))
        	products.add(product);
    }
    
    public void beforeDelete() {
        mode = "delete";  
    }
    
    public void delete() {
    	productRepository.delete(product); 
        products.remove(product);
        product = null;
    }
    
    @Deferred
    @RequestAction
    @IgnorePostback
    public void loadData() {
    		
    	products = productRepository.findAll(); 	
    	product = null;
    }
    	
    public List<Product> getProducts() {
        return products;
    }
    
    public Product getProduct() {
        return product;
    }
    
	public void setProduct(Product product) {
		this.product = product;
	}

    public String getMode() {
        return mode;
    }

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
    	
		if(value == null)
    	return null;
    	else
    	{
    		for(Product product:products)
    		{
    			if(product.getIdProduct().equals(Long.valueOf(value)))
    			{
    				return product;
    			}
    		}
    	}
		return value;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		if (value == null)
			return null;
		else return String.valueOf(((Product) value).getIdProduct());
	}
}
