/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendaclient;

import agenda.IAgenda;
import agenda.util.Client;
import agendaclient.acceuil.Acceuil;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 *
 * @author Mbeng
 */
public class AgendaClient extends Application {
    private Remote agenda;
    private Client client;
    
    @Override
    public void start(Stage primaryStage) {
        if(connectClient()){    
            Acceuil acceuil = new Acceuil(client, agenda);
            AnchorPane root = acceuil.getPane();

            Scene scene = new Scene(root);

            primaryStage.setTitle("Agenda Client");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    private boolean connectClient(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Connection");
        dialog.setHeaderText("Entrez votre username et l'adresse du serveur");
        
        ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        TextField adresse = new TextField();
        adresse.setPromptText("Adresse de l'objet distant");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Adresse:"), 0, 1);
        grid.add(adresse, 1, 1);

        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), adresse.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        if(result == null){
            return false;
        }
        else{
            try {
                agenda = Naming.lookup("rmi://"+adresse.getText()+"/Agenda");
                int idClient = ((IAgenda)agenda).clientExist(username.getText());
                client =  new Client();
                client.setIdClient(idClient);
                return idClient>0;
                
            } catch (Exception ex) {
                ex.printStackTrace();
            } 
        }
        
        return false;
    }
}
