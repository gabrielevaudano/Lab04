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
		return studenteD.getStudentiByCorso(corso);
	}
	
	public List<Corso> getCorsoForStudente(Integer matricola, Corso corso) throws SQLException {
		
		if (corso==null)
			return corsoD.getCorsiForStudente(matricola);
		else
			return corsoD.getCorsoForStudente(this.getStudente(matricola), corso);

	}
	

	public boolean IscriviStudente(Integer matricola, Corso corso) throws Exception {
		Studente studente;
		Corso c;

		studente = studenteD.getStudente(matricola);
		c = corsoD.getCorso(corso);
		
		List<Studente> studenti = studenteD.getStudentiByCorso(corso);
		
		if (studenti.contains(studente))
			throw new IllegalStateException("L'utente è già iscritto al corso selezionato.\n");
		
		return corsoD.inscriviStudenteACorso(studente, c);
	}
}
