/**
 * Sample Skeleton for 'SerieA.fxml' Controller Class
 */

package it.polito.tdp.seriea;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import it.polito.tdp.seriea.model.Model;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class SerieAController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxSeason"
    private ChoiceBox<Season> boxSeason; // Value injected by FXMLLoader

    @FXML // fx:id="boxTeam"
    private ChoiceBox<?> boxTeam; // Value injected by FXMLLoader
    
    @FXML
    private Button btnCaricaPartite;

    @FXML
    private Button btnDomino;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void handleCarica(ActionEvent event) {
    	
    	Season season = boxSeason.getValue();
    	if(season == null) {
    		txtResult.appendText("E' necessario scegliere una squadra dal menu\n");
    		return;
    	}
    	model.creaGrafo(season);
    	txtResult.appendText(String.format("Grafo creato con %d vertici e %d archi\n", model.getVertici(), model.getArchi()));
    	List<Team> classifica = model.getClassifica();
    	txtResult.appendText("CLASSIFICA FINALE DEL CAMPIONATO\n");
    	for(Team t: classifica) {
    		txtResult.appendText(String.format("Team: %s - Punteggi: %d\n", t.getTeam(), t.getPunti()));
    	}

    }

    @FXML
    void handleDomino(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxSeason != null : "fx:id=\"boxSeason\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert boxTeam != null : "fx:id=\"boxTeam\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnCaricaPartite != null : "fx:id=\"btnCaricaPartite\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnDomino != null : "fx:id=\"btnDomino\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SerieA.fxml'.";
    
        btnCaricaPartite.setDisable(false);
        btnDomino.setDisable(true);
        
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	boxTeam.getItems().clear();
    	boxSeason.getItems().addAll(model.getSeason());
    }
}
