package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	public Studente getStudente(Integer codice) {
		final String sql = "SELECT nome, cognome, cds FROM studente WHERE matricola = ?";
		
		try {
			Studente studente;

			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, codice);
			
			ResultSet rs = st.executeQuery();
			
			if (rs.next())
				studente = new Studente(codice, rs.getString("nome"), rs.getString("cognome"), rs.getString("cds"));
			else
				throw new NullPointerException("Non è presente alcuno studente con tale matricola.");
			
			conn.close();
			
			return studente;
			
		} catch (SQLException e) {
			throw new RuntimeException("Errore Db: ", e);
		}
	}
	
	public List<Studente> getStudentiByCorso(Corso corso) {
		final String sql = "SELECT s.matricola, s.nome, s.cognome, s.cds FROM iscrizione i, corso c, studente s"
				+ " WHERE i.matricola = s.matricola AND i.codins = c.codins "
				+ " AND c.codins = ?";
		
		List<Studente> studente = new LinkedList<Studente>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodIns());
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				studente.add(new Studente(rs.getInt("matricola"), rs.getString("nome"), rs.getString("cognome"), rs.getString("cds")));
			}
			
			conn.close();

			if (studente.size()==0)
				throw new NullPointerException("Non è presente alcun studente iscritto al corso con tale matricola.");
			
			return studente; 
		}
		catch (SQLException e) {
			throw new RuntimeException("Errore nella gestione del database.", e);
		}
	}
	
}
