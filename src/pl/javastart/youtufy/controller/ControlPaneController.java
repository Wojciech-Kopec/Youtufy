package pl.javastart.youtufy.controller;
 
import java.net.URL;
import java.util.ResourceBundle;
 
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
 
public class ControlPaneController implements Initializable {
 
    @FXML
    private Button nextButton;
 
    @FXML
    private Slider volumeSlider;
 
    @FXML
    private Button previousButton;
 
    @FXML
    private Slider songSlider;
 
    @FXML
    private ToggleButton playButton;
 
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // dodanie akcji przycisku dla playButton
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // jeœli przycisk nie by³ wciœniêty, to znaczy, ¿e ma przejœæ w
                // Play
                // jeœli przycisk by³ wciœniêty, to po wciœniêciu przechodzi w
                // Stop
                if (playButton.isSelected()) {
                    System.out.println("Play");
                } else {
                    System.out.println("Stop");
                }
            }
        });
 
        // dodanie akcji dla previous i next
        previousButton.setOnAction(x -> System.out.println("Previous"));
 
        nextButton.setOnAction(x -> System.out.println("Next"));
    }
}