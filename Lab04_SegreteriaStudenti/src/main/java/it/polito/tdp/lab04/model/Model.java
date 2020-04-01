package it.polito.tdp.lab04.model;
import java.sql.SQLException;
import java.util.List;


import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	CorsoDAO corsoD;
	StudenteDAO studenteD;
	
	public Model() {
		super();
		this.corsoD = new CorsoDAO();
		this.studenteD = new StudenteDAO();
	}

	public List<Corso> getAllCorsi() {
		return corsoD.getTuttiICorsi();
	}
	
	 
	public Studente getStudente(Integer matricola) {
		return studenteD.getStudente(matricola);
	}
	
	public List<Studente> getStudentiByCorso(Corso corso) {	
		return studenteD.getStudentiByCorso(corsoD.getCorso(corso)); // faccio così per gestire l'eccezione, potrei passare direttamente il corso se lo verificassi direttamente in FXML Controller
																	 // ma in questo modo è più semplice da gestire, avendo già creato una classe apposita per la verifica
	}
	
	public List<Corso> getCorsoForStudente(Studente studente) throws SQLException {
		return corsoD.getCorsiForStudente(studente);
	}
	

	public boolean IscriviStudente(Studente studente, Corso corso) throws Exception {		
		List<Studente> studenti = studenteD.getStudentiByCorso(corsoD.getCorso(corso));
		
		if (studenti.contains(studente))
			throw new IllegalStateException("L'utente è iscritto al corso selezionato.");
		
		return corsoD.inscriviStudenteACorso(studente, corso);
	}
}
