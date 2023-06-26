package pl.opole.uni.project.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pl.opole.uni.project.model.MenuItem;

@Scope(value = "session")
@Component(value = "menuController")
public class MenuController {

	private MenuItem selectedItem;
	private List<MenuItem> menuList;
	
	@PostConstruct
	private void loadMenu () {
		menuList = new ArrayList<MenuItem>();
		
		MenuItem startingItem = new MenuItem("Klienci", "Klienci", "clientTable.xhtml");
		//MenuItem startingItem = new MenuItem("Zamówienie", "Lista Zamówień", "orderTable.xhtml");
		menuList.add(startingItem);
		menuList.add(new MenuItem("Towar", "Towar", "productTable.xhtml"));

		menuList.add(new MenuItem("Zamówienie", "Lista Zamówień", "orderTable.xhtml"));
		//menuList.add(new MenuItem("Klienci", "Klienci", "clientTable.xhtml"));

		
		this.selectedItem = startingItem;
	}

	public String changeMenuItem(String itemName) {
        
        for (MenuItem item : menuList)
        {
            if (item.getName().equals(itemName))
            {
                this.selectedItem = item;
                break;
            }
        }
        
        return this.selectedItem.getPage() + "?faces-redirect=true";
    }
	
	public MenuItem getSelectedItem() {
		return selectedItem;
	}

	public List<MenuItem> getMenuList() {
		return menuList;
	}
	
}
