package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.ArrayList;
import java.util.*;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class FXMLController {
	private Dictionary model;

	List <String> input = new ArrayList <>();  

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> btnChoiceBox;

    @FXML
    private TextArea txtTesto;

    @FXML
    private Button btnSpell;

    @FXML
    private TextArea txtErrors;

    @FXML
    private Text txtNErrori;

    @FXML
    private Button btnClear;

    @FXML
    private Text txtTime;

    @FXML
    void doClearText(ActionEvent event) {
    	txtTesto.clear();
    	txtErrors.clear();

    	
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	this.model.lodDictionary(btnChoiceBox.getValue());
    	String testo;
    	testo = txtTesto.getText();
    	testo.toLowerCase();
    	testo.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_'~()\\[\\]\"]", "");
    	
    	String [] s = testo.split(" ");
    	input = Arrays.asList(s);
    	
    	List <RichWord> errateStampa = new ArrayList <>();
    	try {
    		errateStampa = this.model.spellCheckText(input);
    	} catch (IllegalStateException se) {
    		txtErrors.appendText(se.getMessage());
    	}
    	
    	txtErrors.setText(this.model.stampaLista(errateStampa));
    	
    	txtNErrori.setText("Il testo contiene "+Integer.toString(this.model.getnErrate())+" errori.");
    	txtTime.setText("Il tempo impiegato e' " +Long.toString(this.model.doTime()) + " nanosecondi.");
    }

    @FXML
    void initialize() {
        assert btnChoiceBox != null : "fx:id=\"btnChoiceBox\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTesto != null : "fx:id=\"txtTesto\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSpell != null : "fx:id=\"btnSpell\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtErrors != null : "fx:id=\"txtErrors\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNErrori != null : "fx:id=\"txtNErrori\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTime != null : "fx:id=\"txtTime\" was not injected: check your FXML file 'Scene.fxml'.";
        
        btnChoiceBox.setValue("English");
        btnChoiceBox.getItems().addAll("English", "Italian");   
    }
    
    public void setModel(Dictionary model) {
    	this.model=model;
    }
}









