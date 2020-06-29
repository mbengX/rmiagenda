/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendaclient.plagelibre;

import agenda.IAgenda;
import agenda.util.Client;
import agenda.util.RendezVous;
import agendaclient.acceuil.Acceuil;
import agendaclient.acceuil.AcceuilController;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Mbeng
 */
public class FindPlageLibre {
    private AnchorPane pane;
    private FindPlageLibreController controller;
    private Remote serveur;
    private Client client;
    
    public FindPlageLibre(Remote serveurRemote, Client client) {
        load();
        this.serveur = serveurRemote;
        this.client = client;
    }
    
    private void load(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(FindPlageLibre.class.getResource("FindPlageLibre.fxml"));
            pane = (AnchorPane) loader.load();
            
            controller = loader.getController();
            controller.getRechercheButton().setOnAction((event) -> {
                try {
                    int njr = Integer.parseInt(controller.getNombreDeJourField().getText());
                    String dateDeb = RendezVous.localDateToString(controller.getDateDebutPicker().getValue());
                    controller.getConsoleArea().setText("Une plage de "+njr+" jour(s) disponible après le "+dateDeb+" tombe à partir du:\n"
                            + ((IAgenda)serveur).plageDispo(client.getIdClient(), dateDeb, njr));
                } catch (RemoteException ex) {
                    Logger.getLogger(FindPlageLibre.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(FindPlageLibre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public AnchorPane getPane() {
        return pane;
    }

    public FindPlageLibreController getController() {
        return controller;
    }
}
