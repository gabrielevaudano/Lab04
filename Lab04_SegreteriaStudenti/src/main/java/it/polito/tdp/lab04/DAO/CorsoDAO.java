package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				// Aggiungo ogni ogggetto rilevato alla lista che devo successivamente restituire
				corsi.add(new Corso(codins, numeroCrediti, nome, periodoDidattico));
			}
			
			if (corsi.size()==0)
				throw new NullPointerException("Non esiste alcun insegnamento registrato.");
			
			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore nella lettura dati dal database.", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(Corso c) {
		final String sql = "SELECT * FROM corso WHERE corso.codins = ?";

		Corso corso = null;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, c.getCodIns());
			
			ResultSet rs = st.executeQuery();
			

			if (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				corso = new Corso(codins, numeroCrediti, nome, periodoDidattico);
			}
			
			conn.close();

			if (corso==null)
				throw new NullPointerException("Non esiste alcun corso con tale codice insegnamento.");
			
			
			return corso;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore nella lettura dati dal database.", e);
		}
	}
	
	

	public List<Corso> getCorsiForStudente(Integer matricola) {
		
		final String sql = "SELECT c.* FROM corso c, iscrizione i, studente s  "
				+ " WHERE c.codins = i.codins AND s.matricola = i.matricola "
				+ " AND s.matricola = ?";
		
		
		List<Corso> corso = new LinkedList<Corso>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, matricola);
	
			ResultSet rs = st.executeQuery();
			
			
			while (rs.next()) {
				corso.add(new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd")));
			}
			
			if (corso.size()==0)
				throw new NullPointerException("Lo studente selezionato esiste in registro, ma non frequenta alcun corso.");
			
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException("Errore nella lettura dati dal database. ", e);
		}
		
		return corso; 

	}

	public List<Corso> getCorsoForStudente(Studente studente, Corso corso)  {
				
		final String sqlAlternate = "SELECT c.* FROM corso c, iscrizione i, studente s  "
				+ " WHERE c.codins = i.codins AND s.matricola = i.matricola "
				+ " AND s.matricola = ? AND c.codins = ? ";		
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sqlAlternate);
			
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodIns());
	
			ResultSet rs = st.executeQuery();
			
			if (!rs.next()) {
				conn.close();
				throw new NullPointerException("Lo studente selezionato esiste in registro, ma non frequenta il corso.");
			}
			else {
				conn.close();
				throw new RuntimeException("Lo studente è iscritto al corso selezionato!");
			}
		} catch(SQLException e) {
			throw new RuntimeException("Errore nella lettura dati dal database.", e);
		}
	}
	
	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		final String sqlAlternate = "INSERT INTO iscrizione (matricola, codins) VALUES (?, ?) ";		
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sqlAlternate);
			
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodIns());
	
			Integer rs = st.executeUpdate();
			
			if (rs==1)
				return true;
			
		} catch(SQLException e) {
			throw new RuntimeException("Errore nella lettura dati dal database.", e);
		}
		return false;
	}





}
