/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendaclient.rdv;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Mbeng
 */
public class EditRdv {
    private AnchorPane pane;
    private EditRdvController controller;
    
    public EditRdv() {
        load();
    }
    
    private void load(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EditRdv.class.getResource("EditRdv.fxml"));
            pane = (AnchorPane) loader.load();
            
            controller = loader.getController();
        } catch (IOException ex) {
            Logger.getLogger(EditRdv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public AnchorPane getPane() {
        return pane;
    }

    public EditRdvController getController() {
        return controller;
    }
}
