package tcc.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class Tela1Controller implements Initializable {

    @FXML
    private TextField anoTextField;
    @FXML
    private Button obterCursos;

    int ano;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        obterCursos.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("tela2.fxml"));
                    Parent root = loader.load();

                    Tela2Controller scene2Controller = loader.getController();
                    
                    scene2Controller.enviaText(anoTextField.getText());
                    scene2Controller.pegaDisciplina(anoTextField.getText());

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Dados do Aluno");
                    stage.show();

                    Stage stage1 = (Stage) obterCursos.getScene().getWindow();
                    stage1.close();

                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        }
        );

    }
}
