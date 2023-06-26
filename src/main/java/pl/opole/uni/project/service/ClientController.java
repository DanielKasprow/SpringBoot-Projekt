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
import pl.opole.uni.project.model.ClientRepository;

@Scope (value = "session")
@Component (value = "clientController")
@ELBeanName(value = "clientController")
@Join(path = "/", to = "/clientTable.xhtml")
public class ClientController  implements Converter{
    
   
	
	@Autowired
    private ClientRepository clientRepository;

    
    private List<Client> clients;
    private Client client;
    private String mode;
    
	public void add() {
		client = new Client();  
        mode = "add";
    }
    
    public void edit() {
    	mode = "edit";
    }
    
    public void save() {
        clientRepository.save(client);
        if (mode.equals("add"))
        	clients.add(client);
    }
    
    public void beforeDelete() {
        mode = "delete";  
    }
    
    public void delete() {
    	clientRepository.delete(client); 
        clients.remove(client);
        client = null;
    }
    

    @Deferred
    @RequestAction
    @IgnorePostback
    public void loadData() {
    		
    	clients = clientRepository.findAll(); 	
    	client = null;
    }
    	
    public List<Client> getClients() {
        return clients;
    }
    
    public Client getClient() {
        return client;
    }
    public ClientRepository getClientRepository() {
		return clientRepository;
	}

	public void setClientRepository(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	public void setClient(Client client) {
		this.client = client;
	}


    
    public String getMode() {
        return mode;
    }

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		
		if (value == null)		
			return null;
		else
		{
			for (Client client: clients)
			{
				if (client.getId().equals(Long.valueOf(value)))
				{
					return client;
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
			return String.valueOf(((Client) value).getId());
	}
	
	

}
