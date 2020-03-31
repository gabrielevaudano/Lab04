package it.polito.tdp.lab04;

import javafx.application.Application;
import static javafx.application.Application.launch;

import it.polito.tdp.lab04.FXMLController;
import it.polito.tdp.lab04.model.Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class EntryPoint extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene.fxml"));
    	Parent root = loader.load();
        Scene scene = new Scene(root);
  

        Model model = new Model();
        FXMLController controller = loader.getController();
        controller.setModel(model);
        stage.setTitle("Gestore Corsi");
        stage.setScene(scene);
        stage.show();
    }

  
    public static void main(String[] args) {
        launch(args);
    }

}
