/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendaserveur;

import agenda.Agenda;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mbeng
 */
public class AgendaServeur {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //System.setSecurityManager(new RMISecurityManager());
            
            Agenda agenda = new Agenda();
            Registry r = java.rmi.registry.LocateRegistry.createRegistry(1099);
            r.rebind("Agenda", agenda);
            System.out.println("OK: "+r);
            /*RendezVous rdv = new RendezVous();
            rdv.setDate("20/12/2008");
            rdv.setDetail("penser à acheter des chaussure");
            rdv.setHeure("2h30");
            rdv.setIdClient(1);
            rdv.setPeriode(RendezVous.ANNUELLE);
            rdv.setTitre("Anniversaire");
            agenda.ajouterRdv(rdv);*/
        } catch (RemoteException ex) {
            Logger.getLogger(AgendaServeur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
