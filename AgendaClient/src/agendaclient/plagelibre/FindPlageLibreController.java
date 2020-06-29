/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendaclient.plagelibre;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Mbeng
 */
public class FindPlageLibreController implements Initializable {
    @FXML
    private DatePicker dateDebutPicker;
    @FXML
    private Button rechercheButton;
    @FXML
    private TextArea consoleArea;
    @FXML
    private TextField nombreDeJourField;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public DatePicker getDateDebutPicker() {
        return dateDebutPicker;
    }

    public Button getRechercheButton() {
        return rechercheButton;
    }

    public TextArea getConsoleArea() {
        return consoleArea;
    }

    public TextField getNombreDeJourField() {
        return nombreDeJourField;
    }
    
    
}
