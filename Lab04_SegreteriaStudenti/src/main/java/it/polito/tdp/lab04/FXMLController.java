package it.polito.tdp.lab04;

import java.net.URL;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Corso> comboCorsi;

    @FXML
    private Button btnCercaIscritti;

    @FXML
    private TextField txtMatricola;

    @FXML
    private Button btnAutoCompletamento;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnIscriviUser;

    @FXML
    private Button btnCercaCorsi;

    @FXML
    private TextArea txtArea;

    @FXML
    private Button btnReset;

	private Model model;


    @FXML
    void autoCompletamento(ActionEvent event) {
    	try {
    		Integer matricola = Integer.parseInt(txtMatricola.getText());
    		
    		if (txtMatricola.getText().length()!=6) {
    			throw new InvalidParameterException();
    		}
    		
    		String nome = model.getStudente(matricola).getNome();
    		String cognome = model.getStudente(matricola).getCognome();
   
    		txtCognome.setText(cognome);
    		txtNome.setText(nome);
   	
    	} catch(NumberFormatException e) {
			txtArea.appendText("Non hai inserito un codice matricola, esso deve contenere solo numeri!\n");
    	} catch (NullPointerException ne) {
			txtArea.appendText(ne.getMessage() + "\n");
    	} catch (InvalidParameterException e) {
    		
    	}
    
    }

    @FXML
    void cercaCorsi(ActionEvent event) {
    	Corso nomeCorso = comboCorsi.getSelectionModel().getSelectedItem();
    	
    	try {
    		Studente stud = model.getStudente(Integer.parseInt(txtMatricola.getText()));
    		
    		if (String.valueOf(stud)==null || String.valueOf(stud)=="") 
    			txtArea.appendText("Non è stato inserito alcun numero matricola.\n");
    		
    		for (Corso c: model.getCorsoForStudente(stud.getMatricola(), nomeCorso))
    			txtArea.appendText(c.toLongString());
    	
    	} catch(NumberFormatException e) {
			txtArea.appendText("Il campo matricola accetta solamente numeri.\n");
		} catch (SQLException e) {
			txtArea.appendText(e.getMessage()+"\n");
		} catch (RuntimeException e) {
			txtArea.appendText(e.getMessage()+"\n");
		}
    			
    }

    @FXML
    void cercaIscritti(ActionEvent event) {
		Corso corso = comboCorsi.getSelectionModel().getSelectedItem();

		if (corso==null) {
			txtArea.appendText("Non hai selezionato alcun corso.\n");
			return;
		}
		
		try {
			for (Studente studente : model.getStudentiByCorso(corso)) 
				txtArea.appendText(studente.toString() + "\n");
		} catch (NullPointerException e) {
			txtArea.appendText("Il corso selezionato non ha alcun iscritto.\n");
		}
    }
    @FXML
    void iscriviUser(ActionEvent event) {
    	try {
    		Integer matricola = Integer.parseInt(txtMatricola.getText());
    		
    		if (String.valueOf(matricola)==null || String.valueOf(matricola)=="") 
    			txtArea.appendText("Non è stato inserito alcun numero matricola.\n");
    		
    		if (model.IscriviStudente(matricola, comboCorsi.getSelectionModel().getSelectedItem()))
    				txtArea.appendText("Studente iscritto al corso con successo!\n");
    		else
    			txtArea.appendText("Non è stato possibile aggiungere lo studente al corso.\n");
    	} catch (Exception e) {
    		txtArea.appendText(e.getMessage()+"\n");
    	}
    }

    @FXML
    void reset(ActionEvent event) {
    	txtMatricola.clear();
    	txtCognome.clear();
    	txtNome.clear();
    	txtArea.clear();
    }

    @FXML
    void initialize() {
        assert comboCorsi != null : "fx:id=\"comboCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaIscritti != null : "fx:id=\"btnCercaIscritti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAutoCompletamento != null : "fx:id=\"btnAutoCompletamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscriviUser != null : "fx:id=\"btnIscriviUser\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtArea != null : "fx:id=\"txtArea\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	// Dichiaro ComboBox
    	comboCorsi.getItems().add(null);
    	comboCorsi.getItems().addAll(model.getAllCorsi());

    }
}
