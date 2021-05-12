
package it.polito.tdp.borders;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    private ComboBox<Country> cmbNazione;

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	txtResult.clear();
    	int anno=0;
    	
    	try {
    		anno = Integer.parseInt(txtAnno.getText());
    	} catch(NumberFormatException nfe) {
    		txtResult.setText("L'anno inserito può contenere solo valori numerici");
    		return;
    	}
    	
    	model.creaGrafo(anno);
    	
    	// popolo la comboBox
    	
    	cmbNazione.getItems().setAll(model.getGrafo().vertexSet());
    	
    	
    	ConnectivityInspector<Country,DefaultEdge> ci = new ConnectivityInspector<>(model.getGrafo());
    	
    	txtResult.setText("Numero componenti connesse: "+ci.connectedSets().size()+"\n");
    	
    	for(Country c : model.getGrafo().vertexSet()) {
    		txtResult.appendText(c.getNome()+": "+model.getGrafo().degreeOf(c)+"\n");
    		
    	}
 
    }
    
    @FXML
    void doStatiRaggiungibili(ActionEvent event) {
    	
    	txtResult.clear();
//    	
//    	for(Country c : model.statiRaggiungibili(cmbNazione.getValue())) {
//    		txtResult.appendText(c.getNome()+"\n");
//    	}
//    	
//    	for(Country c : model.statiRaggiungibili2(cmbNazione.getValue())) {
//    		txtResult.appendText(c.getNome()+"\n");
//    	}
    	
    	for(Country c : model.statiRaggiungibili3(cmbNazione.getValue())) {
    		txtResult.appendText(c.getNome()+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
