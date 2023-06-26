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

import pl.opole.uni.project.model.Client;
import pl.opole.uni.project.model.Order;
import pl.opole.uni.project.model.OrderRepository;
import pl.opole.uni.project.model.Product;
import pl.opole.uni.project.model.ProductRepository;

@Scope (value = "session")
@Component (value = "orderController")
@ELBeanName(value = "orderController")
@Join(path = "/orders", to = "/orderTable.xhtml")
public class OrderController implements Converter{
    
   		@Autowired
	    private OrderRepository orderRepository;
   		
  		@Autowired
	    private ProductRepository productRepository;
   		
	    
	    private List<Order> orders;
	    private Order order;
	    private Client client;
	    private Product product;
	    private String mode;
	    
  		private List<Product> productListToDialog;
  		private List<Product> productListWithout;	    
  		
  		
		public void add() {
			order = new Order();  
	        mode = "add";
	    }
	    
	    public void edit() {
	    	mode = "edit";
	    }
	    
	    public void save() {
	        orderRepository.save(order);
	        if (mode.equals("add"))
	        	orders.add(order);
	    }
 
	    public void beforeAddProduct() {
   	
	    	productListToDialog = productListWithout;
	    	
	    }
	    
	    
	     public void beforeDeleteProduct() {
	    	productListToDialog = order.getProductList();
	    }
	     
	     public void addProduct()
		    {	    	
	    	 	System.out.print("cos");
		    	order.getProductList().add(product);
		      	productListWithout.remove(product);
		    	product.getOrderList().add(order);
		    	productRepository.save(product);;
		    	//order=orderRepository.save(order);
		    }
	     
		    
		    public void deleteProduct() {      
		    	productListWithout.add(product);
		    	order.getProductList().remove(product);
		    	product.getOrderList().remove(order);
		    	productRepository.save(product);
		    	//order=orderRepository.save(order);
		    }
		    
	     
	    public void beforeDelete() {
	        mode = "delete";  
	    }
	    
	    public void delete() {
	    	orderRepository.delete(order); 
	        orders.remove(order);
	        order = null;
	    }
	    
	    @Deferred
	    @RequestAction
	    @IgnorePostback
	    public void loadData() {
	    		
	    	orders = orderRepository.findAll(); 
	    	productListWithout = productRepository.findAll();
	    	//order=null;
	    }
	    
	    	
	    public List<Order> getOrders() {
	        return orders;
	    }
	    
	    public Order getOrder() {
	        return order;
	    }
	    
		public void setOrder(Order order) {
			this.order = order;
		}

        public String getMode() {
	        return mode;
	    }

		public void setOrders(List<Order> orders) {
			this.orders = orders;
		}

		public void setMode(String mode) {
			this.mode = mode;
		}

	
	public List<Product> getProductListToDialog() {
			return productListToDialog;
		}

		public void setProductListToDialog(List<Product> productListToDialog) {
			this.productListToDialog = productListToDialog;
		}



	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}



	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Product> getProductListWithout() {
		return productListWithout;
	}

	public void setProductListWithout(List<Product> productListWithout) {
		this.productListWithout = productListWithout;
	}

	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		
		if (value == null)		
			return null;
		else
		{
			for (Order orders: orders)
			{
				if (orders.getIdOrder().equals(Long.valueOf(value)))
				{
					return order;
				}
			}
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		
		if (value == null)
			return null;
		else 
			return String.valueOf(((Order) value).getIdOrder());
	}

}
