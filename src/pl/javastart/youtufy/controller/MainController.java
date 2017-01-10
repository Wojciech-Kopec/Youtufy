package pl.javastart.youtufy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

public class MainController {

    @FXML
    private Button nextButton;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Button previousButton;

    @FXML
    private MenuBar mainMenu;

    @FXML
    private Slider mediaSlider;

    @FXML
    private TableView<?> resultTableView;

    @FXML
    private WebView videoWebView;

    @FXML
    private TextField searchTextField;

    @FXML
    private ListView<?> historyListView;

    @FXML
    private Button playButton;

}
