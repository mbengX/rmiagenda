/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendaclient.acceuil;

import agenda.IAgenda;
import agenda.util.Client;
import java.io.IOException;
import java.rmi.Remote;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Mbeng
 */
public class Acceuil {
    private AnchorPane pane;
    private AcceuilController controller;

    public Acceuil(Client client, Remote serveur) {
        load();
        controller.setInfoServeur(client, serveur);
    }
    
    private void load(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Acceuil.class.getResource("Acceuil.fxml"));
            pane = (AnchorPane) loader.load();
            
            controller = loader.getController();
        } catch (IOException ex) {
            Logger.getLogger(Acceuil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public AnchorPane getPane() {
        return pane;
    }

    public AcceuilController getController() {
        return controller;
    }
    
    
}
