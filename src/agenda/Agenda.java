/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import agenda.util.RendezVous;
import agenda.util.tRendezVous;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Mbeng
 */
public class Agenda extends UnicastRemoteObject implements IAgenda{
    //connection a la base de données
    private static final int REPEAT = 10;
    
    private Connection connect() {
        // SQLite connection string
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:file:db/agendabd.db";
            conn = DriverManager.getConnection(url);
            System.out.println("connection good!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public Agenda() throws RemoteException {
        super();
        connect();//connection a la base de donnees
    }

    @Override
    public int clientExist(String username) throws RemoteException {
        String sql = "SELECT idClient, username FROM Client WHERE username='"+username+"';";
        int id=-1;
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            int compteur = 0;
            while (rs.next()) {
                username = rs.getString("username");
                id = rs.getInt("idClient");
                System.out.println("connection de "+username+":"+id);
                compteur++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    @Override
    public boolean ajouterRdv(RendezVous rdv) throws RemoteException {
 
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement()) {
            System.out.println(rdv);
            if(!rdv.getPeriode().equals(RendezVous.AUCUNE)){
                Date date = RendezVous.SIMPLE_DATE_FORMAT.parse(rdv.getDate());
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                String dateString = rdv.getDate();
                for(int i=0; i<REPEAT; i++){
                    dateString = RendezVous.SIMPLE_DATE_FORMAT.format(c.getTime());
                    System.out.println("dateString"+dateString);
                    String sql = "INSERT INTO RendezVous(daterdv, heure, titre, detail, periode, idClient) VALUES\n" +
                        "('"+dateString+"', '"+rdv.getHeure()+"', '"+rdv.getTitre()+"', '"+rdv.getDetail()+""
                    + "', '"+rdv.getPeriode()+"', "+rdv.getIdClient()+");";
                    stmt.executeUpdate(sql);
                    System.out.println(rdv.getIdClient()+":ajout d'un rendez-vous");
                    if(rdv.getPeriode().equals(RendezVous.ANNUELLE))
                        c.add(Calendar.YEAR, 1);
                    if(rdv.getPeriode().equals(RendezVous.MENSUELLE))
                        c.add(Calendar.MONTH, 1);
                    if(rdv.getPeriode().equals(RendezVous.HEBDOMADAIRE))
                        c.add(Calendar.WEEK_OF_YEAR, 1);
                    if(rdv.getPeriode().equals(RendezVous.QUOTIDIEN))
                        c.add(Calendar.DAY_OF_YEAR, 1);
                }
            }
            else{
                String sql = "INSERT INTO RendezVous(daterdv, heure, titre, detail, periode, idClient) VALUES\n" +
                            "('"+rdv.getDate()+"', '"+rdv.getHeure()+"', \""+rdv.getTitre()+"\", \""+rdv.getDetail()+""
                        + "\", '"+rdv.getPeriode()+"', "+rdv.getIdClient()+");";
                stmt.executeUpdate(sql);
                System.out.println(rdv.getIdClient()+":ajout d'un rendez-vous");
            }
            
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public tRendezVous listRendezVousParDate(int idClient, String date) throws RemoteException {
        tRendezVous listRendezVous = null;
        
        String sql = "SELECT detail, heure, titre, daterdv, idRdv, idClient, periode FROM RendezVous WHERE idClient="+idClient+" AND daterdv='"+date+"';";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            int compt = 0;
            listRendezVous = new tRendezVous();
            // loop through the result set
            while (rs.next()) {
                compt ++;
                RendezVous rdv = new RendezVous();
                rdv.setDetail(rs.getString("detail"));
                rdv.setHeure(rs.getString("heure"));
                rdv.setTitre(rs.getString("titre"));
                rdv.setDate(rs.getString("daterdv"));
                rdv.setIdRdv(rs.getInt("idRdv"));
                rdv.setIdClient(rs.getInt("idClient"));
                rdv.setPeriode(rs.getString("periode"));
                listRendezVous.getListRendezVous().add(rdv);
            }
            conn.close();
            System.out.println(idClient+":rendez-vous de "+date);
            return listRendezVous;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }

    @Override
    public boolean supprimerRdv(RendezVous rdv) throws RemoteException {
        String sql = "DELETE FROM RendezVous WHERE idClient = "+rdv.getIdClient()+" AND idRdv="+rdv.getIdRdv();
 
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate(sql);
            conn.close();
            System.out.println(rdv.getIdClient()+":suppression de rendez-vous");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public String plageDispo(int idClient, String dateDeb, int nombreDeJour) throws RemoteException {
        try {
            String result = "";
            Date date = RendezVous.SIMPLE_DATE_FORMAT.parse(dateDeb);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            
            
            boolean fin = false;
            String dateString = "";
            while(!fin){
                boolean vide = true;
                int compt = 1;
                while(vide && compt<=nombreDeJour){
                    dateString = RendezVous.SIMPLE_DATE_FORMAT.format(c.getTime());
                    String sql = "SELECT count() AS 'nbre' FROM RendezVous WHERE daterdv='"+dateString+"';";
                    
                    System.out.print(dateString+":");
                    try (Connection conn = this.connect();
                        Statement stmt  = conn.createStatement();
                        ResultSet rs    = stmt.executeQuery(sql)){
                        while (rs.next()) {
                            int count = rs.getInt("nbre");
                            System.out.println("count="+count);
                            if(count>0){
                                vide = false;
                                System.out.println("incorrecte");
                            }
                        }
                        if(vide && compt==nombreDeJour){
                            fin = true;
                            System.out.println("vide="+vide+"; compt="+compt);
                        }
                        
                        conn.close();
                   } catch (SQLException e) {
                       System.out.println(e.getMessage());
                   }
                    c.add(Calendar.DAY_OF_YEAR, 1);
                    compt++;
                }
            }
            c.add(Calendar.DAY_OF_YEAR, -1*nombreDeJour);
            return RendezVous.SIMPLE_DATE_FORMAT.format(c.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
}
