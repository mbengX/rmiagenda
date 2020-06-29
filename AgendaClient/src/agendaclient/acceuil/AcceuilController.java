/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendaclient.acceuil;

import agenda.IAgenda;
import agenda.util.Client;
import agenda.util.RendezVous;
import agenda.util.tRendezVous;
import agendaclient.plagelibre.FindPlageLibre;
import agendaclient.rdv.EditRdv;
import agendaclient.rdv.EditRdvController;
import agendaclient.rdv.Rdv;
import java.net.URL;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author Mbeng
 */
public class AcceuilController implements Initializable {

    @FXML
    private jfxtras.scene.control.CalendarPicker calendarPicker;
    @FXML
    private Hyperlink plageDispoHyperlink;
    @FXML
    private FlowPane rdvFlowPane;
    @FXML
    private Button ajouterButton;
    @FXML
    private Button supprimerButton;
    @FXML
    private Label dateSelectedLabel;
    
    private ArrayList<Rdv> rdvList;
    private Client client;
    private Remote serveur;
    private String date = RendezVous.SIMPLE_DATE_FORMAT.format(new Date());
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        rdvList = new ArrayList<>();
        calendarPicker.setValueValidationCallback((calendar) -> {
            date = RendezVous.SIMPLE_DATE_FORMAT.format(calendar.getTime());
            dateSelectedLabel.setText(date);
            try {
                tRendezVous listRendezVous = ((IAgenda)serveur).listRendezVousParDate(client.getIdClient(), date);
                
                rdvFlowPane.getChildren().clear();
                rdvList.clear();
                
                Label aucunLabel = new Label("Vous n'avez aucun rendez-vous pour cette date.");
                if(listRendezVous.getListRendezVous().isEmpty())
                    rdvFlowPane.getChildren().add(aucunLabel);
                
                if(listRendezVous!=null){
                    for(RendezVous rdv: listRendezVous.getListRendezVous()){
                        ajouterRdv(rdv);
                    }
                }
            } catch (RemoteException ex) {
                Logger.getLogger(AcceuilController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true; //To change body of generated lambdas, choose Tools | Templates.
        });
    }

    public void setInfoServeur(Client client, Remote serveur) {
        this.client = client;
        this.serveur = serveur;
        
        String d = RendezVous.SIMPLE_DATE_FORMAT.format(new Date());
        dateSelectedLabel.setText(d);
        try {
            
            tRendezVous listRendezVous = ((IAgenda)serveur).listRendezVousParDate(client.getIdClient(), d);
            rdvFlowPane.getChildren().clear();
            rdvList.clear();
            
            Label aucunLabel = new Label("Vous n'avez aucun rendez-vous pour cette date.");
            if(listRendezVous.getListRendezVous().isEmpty())
                rdvFlowPane.getChildren().add(aucunLabel);
                
            if(listRendezVous!=null){
                for(RendezVous rdv: listRendezVous.getListRendezVous()){
                    ajouterRdv(rdv);
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(AcceuilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private RendezVous editRdvDialogue(){
        Dialog<RendezVous> dialog = new Dialog<>();
        dialog.setTitle("Edit un rendez-vous");
        dialog.setHeaderText("Remplissez les champs");

        ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        EditRdv editRdv = new EditRdv();
        AnchorPane pane = editRdv.getPane();
        EditRdvController editRdvController = editRdv.getController();
        editRdvController.getDate().setText(date);
        dialog.getDialogPane().setContent(pane);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                RendezVous rendezVous = new RendezVous();
                rendezVous.setDate(editRdvController.getDate().getText());
                rendezVous.setDetail(editRdvController.getDetailArea().getText());
                rendezVous.setHeure(editRdvController.getHeurePicker().getLocalTime().toString());
                rendezVous.setPeriode(editRdvController.getPeriodiciteBox().getValue());
                rendezVous.setTitre(editRdvController.getTitreField().getText());
                return rendezVous;
            }
            return null;
        });

        Optional<RendezVous> result = dialog.showAndWait();

        if(result == null){
            return null;
        }
        else{
            try {
                return result.get();
            } catch (Exception e) {
                return null;
            }
        }    
    }
    
    private void plageDispoDialogue(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Chercher une plage libre");

        ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType);
        
        FindPlageLibre plageDispo = new FindPlageLibre(serveur, client);
        AnchorPane pane = plageDispo.getPane();

        dialog.getDialogPane().setContent(pane);
          
        String datedeb = RendezVous.localDateToString(plageDispo.getController().getDateDebutPicker().getValue());
        String nbJr = plageDispo.getController().getNombreDeJourField().getText();
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                
                return new Pair<>(datedeb, nbJr);
            }
            return null;
        });
        
        
        Optional<Pair<String, String>> result = dialog.showAndWait();
    }
    
    @FXML
    private void ajouterOnAction(){
        RendezVous rendezVous = editRdvDialogue();
        if(rendezVous!=null){
            rendezVous.setIdClient(client.getIdClient());
            try {
                if(((IAgenda)serveur).ajouterRdv(rendezVous)){
                    ajouterRdv(rendezVous);
                }
            } catch (RemoteException ex) {
                System.out.println("Impossible d'ajouter le rendez-vous!");
                ex.printStackTrace();
            }
        }
    }
    
    private void ajouterRdv(RendezVous rendezVous){
        Rdv rdv = new Rdv(rendezVous);
        rdvFlowPane.getChildren().add(rdv.getPane());
        rdvList.add(rdv);
    }
    
    @FXML
    private void supprimerOnAction(){
        ArrayList<Rdv> listToDelete = new ArrayList<>();
        ArrayList<AnchorPane> listRdvToDelete = new ArrayList<>();
        
        for(int number = 0; number<rdvList.size(); number++){
            if(rdvList.get(number).getController().getSelectCheckBox().isSelected()){
                try {
                    if(((IAgenda)serveur).supprimerRdv(rdvList.get(number).getRendezVous())){
                        listToDelete.add(rdvList.get(number));
                        listRdvToDelete.add(rdvList.get(number).getPane());
                    }
                    else{
                        System.out.println("problème de suppression dans le serveur");
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(AcceuilController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        rdvList.removeAll(listToDelete);
        rdvFlowPane.getChildren().removeAll(listRdvToDelete);
    }
    
    @FXML
    private void plageDispoOnAction(){
        plageDispoDialogue();
    }
}
