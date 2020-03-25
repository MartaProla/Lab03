package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dizionario;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	Dizionario model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TextArea txtFraseInput;

    @FXML
    private Button btnSpellCheck;

    @FXML
    private TextArea txtResult;

    @FXML
    private Label txtErrori;

    @FXML
    private Button btnClearAll;

    @FXML
    private Label txtEsecuzione;

    @FXML
    void doReset(ActionEvent event) {
    	txtFraseInput.clear();
    	txtResult.clear();

    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	if(choiceBox.getValue()==null) {
    		txtResult.appendText("Devi selezionare una lingua, mi raccomando");
    	}
    	if(!model.loadDictionary(choiceBox.getValue())) {
    		txtResult.appendText("Attenzione hai problemi nel caricare il dizionario");
    	}
    	String frase= txtFraseInput.getText();
    	frase = frase.replaceAll("\n", " ");
		frase = frase.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]]", "");
		
		if(frase.isEmpty()) {
			txtResult.setText("Devi inserire una frase da correggere");
		}
    	List<String>elencoParole=new LinkedList<String>();
    	String parts[]=frase.split(" ");
    	int numErrori=0;
    	double startTime=System.nanoTime();
    	
    	for(int i=0;i<parts.length;i++) {
    		elencoParole.add(parts[i].toLowerCase());
    	}
    	for(RichWord s:model.spellCheckText(elencoParole)) {
    		if(!s.isCorretta()) {
    			numErrori++;
    			txtResult.appendText(s.getParola()+"\n");
    		}
    	}
    	double finishTime=System.nanoTime();
    	txtErrori.setText("Ci sono "+numErrori+" errori");
    	txtEsecuzione.setText("SpellCheck ha richiesto "+(finishTime-startTime)+" tempo per essere eseguito");

    }

    @FXML
    void initialize() {
        assert choiceBox != null : "fx:id=\"choiceBox\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtFraseInput != null : "fx:id=\"txtFraseInput\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSpellCheck != null : "fx:id=\"btnSpellCheck\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtErrori != null : "fx:id=\"txtErrori\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClearAll != null : "fx:id=\"btnClearAll\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtEsecuzione != null : "fx:id=\"txtEsecuzione\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    public void setModel(Dizionario m) {
    	this.model=m;
    	this.choiceBox.getItems().addAll("English","Italiano");
    	this.choiceBox.setValue("English");
    }
}
