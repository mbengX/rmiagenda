/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import agenda.util.RendezVous;
import agenda.util.tRendezVous;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Mbeng
 */
public interface IAgenda extends Remote{
    public int clientExist(String username) throws RemoteException;
    public boolean ajouterRdv(RendezVous rdv) throws RemoteException;
    public tRendezVous listRendezVousParDate(int idClient, String date) throws RemoteException;
    public boolean supprimerRdv(RendezVous rdv) throws RemoteException;
    public String plageDispo(int idClient, String dateDeb, int nombreDeJour) throws RemoteException;
    //TODO: service d'alarme & plage disponible
}
