
/***********************************************************************************
  * Autor: Vitor de Almeida Reis
  * Matricula: 201710793
  * Inicio: 09/08/2019
  * Ultima alteracao: 18/08/2019 
  * Nome: Corrida - Max vs Pateta
  * Funcao: É um jogo de corrida entre Max e Pateta em uma  pista circular
  **********************************************************************************/
import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Principal extends Application {

  public void start(Stage primaryStage){
    
    try {
      Parent principalfxml = FXMLLoader.load(getClass().getResource("/views/tela_principal.fxml"));
      Scene scenePrincipal = new Scene(principalfxml);
      primaryStage.setScene(scenePrincipal);
      primaryStage.setTitle("Max vs Pateta");
      primaryStage.setResizable(false);
      primaryStage.show();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }

}
