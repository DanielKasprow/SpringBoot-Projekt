package pl.opole.uni.project.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "client")
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String surName;    
    
	@OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private List<Order> orders;
	
  
	public String getOrdersList() {
		String lista="";
		
		for (Order o : orders)
		{
			
			lista = lista  + o.getIdOrder().toString()+ ", ";
			
		}
		
		if(lista.length()>2)
			return lista.substring(0, lista.length() - 2);
		else
			return "";
	}
    
	public Client() {

	}
	public Client(Long id, String firstName, String surName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.surName = surName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return firstName+" "+ surName;
	}

    
}
