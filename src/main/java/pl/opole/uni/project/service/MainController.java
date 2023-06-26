package pl.opole.uni.project.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public class MainController <M, R extends JpaRepository<M, Long>>{
	
	 	@Autowired
	 	protected R mainRepository;
	    protected List<M> mainList;
	    protected M selectedObject;
	    private String mode;
	    private final Class<M> modelClass;
	    
	    public MainController(Class<M> clazz) {
	    	modelClass = clazz;
	    }
	    
	    public void add() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
	        selectedObject = modelClass.getDeclaredConstructor().newInstance();  
	        mode = "add";
	    }
	    
	    public void edit() {
	    	mode = "edit";
	    }
	    
	    public void save() {
	        mainRepository.save(selectedObject);
	        if (mode.equals("add"))
	        	mainList.add(selectedObject);
	    }
	    
	    public void beforeDelete() {
	        mode = "delete";  
	    }
	    
	    public void delete() {
	    	mainRepository.delete(selectedObject); 
	        mainList.remove(selectedObject);
	        selectedObject = null;
	    }
	    
	    @Deferred
	    @RequestAction
	    @IgnorePostback
	    public void loadData() {	
	        mainList = mainRepository.findAll();
	        selectedObject = null;
	    }
	   
	    
	    
	    public M getSelectedObject() {
			return selectedObject;
		}

		public void setSelectedObject(M selectedObject) {
			this.selectedObject = selectedObject;
		}

		public List<M> getMainList() {
			return mainList;
		}

		public String getMode() {
	        return mode;
	    }


}
