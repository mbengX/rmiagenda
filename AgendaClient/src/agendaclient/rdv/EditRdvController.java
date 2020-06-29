/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendaclient.rdv;

import agenda.util.RendezVous;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jfxtras.scene.control.LocalTimePicker;

/**
 * FXML Controller class
 *
 * @author Mbeng
 */
public class EditRdvController implements Initializable {

    @FXML
    private ComboBox<String> periodiciteBox;
    @FXML
    private TextField titreField;
    @FXML
    private TextArea detailArea;
    @FXML
    private TextField date;
    @FXML
    private LocalTimePicker heurePicker;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        periodiciteBox.getItems().add(RendezVous.ANNUELLE);
        periodiciteBox.getItems().add(RendezVous.MENSUELLE);
        periodiciteBox.getItems().add(RendezVous.HEBDOMADAIRE);
        periodiciteBox.getItems().add(RendezVous.QUOTIDIEN);
        periodiciteBox.getItems().add(RendezVous.AUCUNE);
        periodiciteBox.setValue(RendezVous.AUCUNE);
    }    

    public ComboBox<String> getPeriodiciteBox() {
        return periodiciteBox;
    }

    public TextField getTitreField() {
        return titreField;
    }

    public TextArea getDetailArea() {
        return detailArea;
    }

    public TextField getDate() {
        return date;
    }
    
    

    public LocalTimePicker getHeurePicker() {
        return heurePicker;
    }
    
    
}
