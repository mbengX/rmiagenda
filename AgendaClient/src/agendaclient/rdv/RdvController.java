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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Mbeng
 */
public class RdvController implements Initializable {

    @FXML
    private CheckBox selectCheckBox;
    @FXML
    private Label titreLabel;
    @FXML
    private Label heureLabel;
    @FXML
    private Label detailLabel;
    @FXML
    private Label periodiciteLabel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setValues(RendezVous rendezVous){
        titreLabel.setText(rendezVous.getTitre());
        heureLabel.setText(rendezVous.getHeure());
        detailLabel.setText(rendezVous.getDetail());
        periodiciteLabel.setText("("+rendezVous.getPeriode()+")");
    }

    public CheckBox getSelectCheckBox() {
        return selectCheckBox;
    }

    public Label getTitreLabel() {
        return titreLabel;
    }

    public Label getHeureLabel() {
        return heureLabel;
    }

    public Label getDetailLabel() {
        return detailLabel;
    }
    
    
}
