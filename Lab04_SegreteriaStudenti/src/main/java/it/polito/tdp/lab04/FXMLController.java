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

    		String nome = model.getStudente(matricola).getNome();
    		String cognome = model.getStudente(matricola).getCognome();
   
    		txtCognome.setText(cognome);
    		txtNome.setText(nome);
   	
    	} catch(NumberFormatException e) {
			txtArea.appendText("Non è stato inserito un numero matricola valido: sono ammessi solo caratteri numerici\\n");
    	} catch (NullPointerException ne) {
			txtArea.appendText(ne.getMessage() + "\n");
    	}
    
    }

    @FXML
    void cercaCorsi(ActionEvent event) {    	
    	try {
    		Studente stud = model.getStudente(Integer.parseInt(txtMatricola.getText()));
    		
    		for (Corso c: model.getCorsoForStudente(stud))
    			txtArea.appendText(c.toLongString());
    	
    	} catch(NumberFormatException e) {
			txtArea.appendText("Non è stato inserito un numero matricola valido: sono ammessi solo caratteri numerici.\n");
		} catch (SQLException e) {
			txtArea.appendText(e.getMessage()+"\n");
		} catch (RuntimeException e) {
			txtArea.appendText(e.getMessage()+"\n");
		}
    			
    }

    @FXML
    void cercaIscritti(ActionEvent event) {		
		try {
			Corso corso = comboCorsi.getSelectionModel().getSelectedItem();

			for (Studente studente : model.getStudentiByCorso(corso)) 
				txtArea.appendText(studente.toString() + "\n");
			
		} catch (NullPointerException e) {
			txtArea.appendText(e.getMessage() + "\n");
		}
    }
    
    @FXML
    void iscriviUser(ActionEvent event) {
    	try {
    		Integer matricola = Integer.parseInt(txtMatricola.getText());
    		Corso corso = comboCorsi.getSelectionModel().getSelectedItem();
    		   
    		if (corso==null)
    			throw new NullPointerException("Non è stato selezionato un corso.");
    		
    		if (model.IscriviStudente(model.getStudente(matricola), corso))
    				txtArea.appendText("Studente iscritto al corso con successo!\n");
    		else
    			txtArea.appendText("Non è stato possibile aggiungere lo studente al corso.\n");
    		
    	}	catch (NumberFormatException e) {
    		txtArea.appendText("Non è stato inserito un numero matricola valido: sono ammessi solo caratteri numerici\n");
    	}	catch (Exception e) {
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
