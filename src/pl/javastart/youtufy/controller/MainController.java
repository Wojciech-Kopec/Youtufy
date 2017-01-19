package pl.javastart.youtufy.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import pl.javastart.youtufy.data.YoutubeVideo;
import pl.javastart.youtufy.main.Youtube;

public class MainController implements Initializable {

    @FXML
    private ContentPaneController contentPaneController;

    @FXML
    private ControlPaneController controlPaneController;

    @FXML
    private MenuPaneController menuPaneController;

    @FXML
    private SearchPaneController searchPaneController;

    private Youtube youtubeInstance;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        configureSearch();
        configureButtons();
        configureTableClick();
    }

    private void configureSearch() {
        TextField searchField = searchPaneController.getSearchTextField();
        TableView<YoutubeVideo> resultsTable = contentPaneController.getResultTableView();

        youtubeInstance = new Youtube();
        youtubeInstance.getSearchQuery().bind(searchField.textProperty());
        resultsTable.setItems(youtubeInstance.getYoutubeVideos());

        searchField.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER) {
                    try {
                        youtubeInstance.search();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        ListView<String> searchHistory = searchPaneController.getHistoryListView();
        searchHistory.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2) {
                    String searchText = searchHistory.getSelectionModel().getSelectedItem();
                    searchField.setText(searchText);
                    try {
                        youtubeInstance.search();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void configureButtons() {
        ToggleButton playButton = controlPaneController.getPlayButton();
        WebEngine webEngine = contentPaneController.getVideoWebView().getEngine();

        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(playButton.isSelected()) {
                    webEngine.executeScript("player.playVideo();");
                } else {
                    webEngine.executeScript("player.pauseVideo();");
                }
            }
        });

        Button prevButton = controlPaneController.getPreviousButton();
        Button nextButton = controlPaneController.getNextButton();
        TableView<YoutubeVideo> resultsTable = contentPaneController.getResultTableView();

        prevButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = resultsTable.getSelectionModel().getSelectedIndex();
                if(index > 0) {
                    resultsTable.getSelectionModel().select(index-1);
                    contentPaneController.playSelectedItem();
                }
            }
        });

        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = resultsTable.getSelectionModel().getSelectedIndex();
                int size = resultsTable.getItems().size();
                if(index < size-1) {
                    resultsTable.getSelectionModel().select(index+1);
                    contentPaneController.playSelectedItem();
                }
            }
        });
    }

    private void configureTableClick() {
        TableView<YoutubeVideo> resultsTable = contentPaneController.getResultTableView();
        resultsTable.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2) {
                    contentPaneController.playSelectedItem();
                    controlPaneController.getPlayButton().setSelected(true);
                }
            }
        });
    }
}