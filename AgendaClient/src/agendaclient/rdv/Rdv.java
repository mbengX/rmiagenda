/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendaclient.rdv;

import agenda.util.RendezVous;
import agendaclient.acceuil.Acceuil;
import agendaclient.acceuil.AcceuilController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Mbeng
 */
public class Rdv {
    private AnchorPane pane;
    private RdvController controller;
    private RendezVous rendezVous;

    public Rdv(RendezVous rendezVous) {
        load();
        this.rendezVous = rendezVous;
        controller.setValues(rendezVous);
    }
    
    private void load(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Rdv.class.getResource("Rdv.fxml"));
            pane = (AnchorPane) loader.load();
            
            controller = loader.getController();
        } catch (IOException ex) {
            Logger.getLogger(Rdv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public AnchorPane getPane() {
        return pane;
    }

    public RdvController getController() {
        return controller;
    }

    public RendezVous getRendezVous() {
        return rendezVous;
    }

    public void setRendezVous(RendezVous rendezVous) {
        this.rendezVous = rendezVous;
    }
    
    
}
