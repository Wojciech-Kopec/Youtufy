package pl.javastart.youtufy.controller;
 
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
 
public class MainController implements Initializable {
	
	@FXML
	private ContentPaneController contentPaneController;
	@FXML
	private ControlPaneController controlPaneController;
	@FXML
	private MenuPaneController menuPaneController;
	@FXML
	private SearchPaneController searchPaneController;
 
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	System.out.println(contentPaneController);
        System.out.println(controlPaneController);
        System.out.println(menuPaneController);
        System.out.println(searchPaneController);
    }
}
