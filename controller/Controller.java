package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Platform;
import models.Corredor;

/******************************************************
 * Classe:  Controller
 * Funcao: Classe responsável por controlar todas as
 *         acoes da view tela_principal.fxml
 * 
 *****************************************************/
public class Controller implements Initializable {

  @FXML
  private Label labelVencedor;
  @FXML
  private AnchorPane anchorPanePrincipal;
  @FXML
  private Button buttonReiniciarJogo, buttonPauseJogo, buttonIniciar;
  @FXML
  private Slider sliderVelocidadePateta, sliderVelocidadeMax;
  @FXML
  private Pane paneVencedor, paneApresentacao;

  @FXML
  private ImageView pateta, max;

  private boolean isPaused = false;

  private Corredor cMax, cPateta;

  private String estadoMax = new String();
  private String estadoPateta = new String();

  public Controller(){
    cMax = new Corredor(1, this);
    cPateta = new Corredor(2, this);

  }

  public void initialize(URL url, ResourceBundle rb) {
    paneApresentacao.setVisible(true); // exibe tela de apresentacao
    
  }

  /************************************************************
   * Metodo: pause
   * Funcao: Este metodo controla o acao de PAUSE e CONTINUAR.
   * Parametros: nenhum
   * Retorno: void
   ***********************************************************/
  @FXML
  public void pause(){
    if(isPaused){
      // liberar as Threads  e atualiza o texto do button "Continuar" para "Pause"

      cMax.resume(); // dá continuidade a Thread
      estadoMax = ""; // o estado do max é resetado e o seu novo estado irá depender da sua posicao

      cPateta.resume(); // dá continuidade a Thread
      estadoPateta = ""; // o estado do max é resetado e o seu novo estado irá depender da sua posicao


      isPaused = false;
      buttonPauseJogo.setText("Pause");

    }else{
      // travar as Threads e atualizar texto do button "Pause" para "Continuar"

      cMax.suspend(); // coloca a Thread em suspensao
      cPateta.suspend(); // coloca a Thread em suspensao

      max.setImage(maxImageEstado()); // seta a imagem do Max parado no trecho em que ele está
      pateta.setImage(patetaImageEstado()); // seta a imagem do Pateta parado no trecho em que ele está

      isPaused = true;
      buttonPauseJogo.setText("Continuar");
    }
  }

  /********************************************************************
   * Metodo: fimCorrida
   * Funcao: é acionado quando alguém chega a linha de chegada.
   *         Também é responsável da tela de apresentacao do vencedor.
   * Parametros: String com o nome do Vencedor(Pateta ou Max).
   * Retorno: void
   ********************************************************************/
  public void fimCorrida(String vencedor) {

    if(vencedor == "Max"){
      cPateta.suspend();
    }else{
      cMax.suspend();
    }

    max.setImage(maxImageEstado());
    pateta.setImage(patetaImageEstado());

    sliderVelocidadeMax.setVisible(false);
    sliderVelocidadePateta.setVisible(false);
    buttonPauseJogo.setVisible(false);
    paneVencedor.setVisible(true);

    labelVencedor.setText(vencedor + " é o vencedor");

    
    cPateta.suspend();
    cMax.suspend();

  }


  /*******************************************************
   * Metodo: maxImageEstado
   * Funcao: Muda a imagem do Max assim que ele estiver 
   *         parado (PAUSE) ou o jogo estiver terminado.
   * Parametros: nenhum
   * Retorno: void
   ******************************************************/
  public Image maxImageEstado(){
    switch(estadoMax){

      case "direitaDescendo":
      case "direita":
      case "direitaSubindo":
        return new Image("/images/Max/paradoParaDireita.png");

      case "cima":
        return new Image("/images/Max/paradoParaCima.png");

      case "esquerdaSubindo":
      case "esquerda":
      case "esquerdaDescendo":
        return new Image("/images/Max/paradoParaEsquerda.png");

      case "baixo":
        return new Image("/images/Max/paradoParaFrente.png");

      default:
        return null;
    }
  }

  /********************************************************
   * Metodo: patetaImageEstado
   * Funcao: Muda a imagem do Pateta assim que ele estiver 
   *         parado (PAUSE) ou o jogo estiver terminado.
   * Parametros: descricao dos parametros recebidos
   * Retorno: descricao do valor retornado
   *******************************************************/
  public Image patetaImageEstado(){
    switch(estadoPateta){

      case "direitaDescendo":
      case "direita":
      case "direitaSubindo":
        return new Image("/images/Pateta/paradoParaDireita.png");

      case "cima":
        return new Image("/images/Pateta/paradoParaCima.png");

      case "esquerdaSubindo":
      case "esquerda":
      case "esquerdaDescendo":
        return new Image("/images/Pateta/paradoParaBaixo.png");

      case "baixo":
        return new Image("/images/Pateta/paradoParaBaixo.png");

      default:
        return null;
    }
  }

  /*******************************************************************************
   * Metodo: reiniciarCorrida
   * Funcao: Reinicializa a corrida assim que for clicado 
   *         o button "Reiniciar Corrida". Ele reseta os personagens 
   *         para o local de origem, e suas velocidades são resetadas ao normal.
   * Parametros: nenhum
   * Retorno: void
   *******************************************************************************/
  @FXML
  public void reiniciarCorrida() {
    if(isPaused){
      // libera as Threads e reinicia


      sliderVelocidadeMax.setVisible(true);
      sliderVelocidadePateta.setVisible(true);
      buttonPauseJogo.setVisible(true);
      paneVencedor.setVisible(false);

      max.setLayoutX(415);
      max.setLayoutY(392);
      cMax.setVelocidade(26);

      pateta.setLayoutX(464);
      pateta.setLayoutY(422);
      cPateta.setVelocidade(26);

      estadoMax = "";
      estadoPateta = "";

      cMax.resume();
      cPateta.resume();

      buttonPauseJogo.setText("Pause");

      isPaused = false;
    }else{
      // apenas reinicia

      sliderVelocidadeMax.setVisible(true);
      sliderVelocidadePateta.setVisible(true);
      buttonPauseJogo.setVisible(true);
      paneVencedor.setVisible(false);

      max.setLayoutX(415);
      max.setLayoutY(392);
      cMax.setVelocidade(26);

      pateta.setLayoutX(464);
      pateta.setLayoutY(422);
      cPateta.setVelocidade(26);

      estadoMax = "";
      estadoPateta = "";

      cMax.resume();
      cPateta.resume();
    }

  }

  /*********************************************************************
   * Metodo: inicioCorrida
   * Funcao: Este método é responsável pela tela de apresentacao do Game.
   * Parametros: nenhum
   * Retorno: void
   ********************************************************************/
  @FXML
  public void inicioCorrida() {
    // tela de apresentacao do jogo

    paneApresentacao.setVisible(false);
    anchorPanePrincipal.setVisible(true);

    max.setLayoutX(415);
    max.setLayoutY(392);

    pateta.setLayoutX(464);
    pateta.setLayoutY(422);

    max.setImage(new Image("/images/Max/maxDireita.gif"));
    pateta.setImage(new Image("/images/Pateta/patetaDireita.gif"));

    cMax.start();
    cPateta.start();

  }


  /*********************************************************************
   * Metodo: velocidadeMax
   * Funcao: Este método é responsável por atualizar a velocidade do Max 
   *         de acordo com o valor do Slider.
   * Parametros: nenhum
   * Retorno: void
   ********************************************************************/
  @FXML
  public void velocidadeMax(){
    double valor = 55 - sliderVelocidadeMax.getValue();
    cMax.setVelocidade(valor);
  }


  /*************************************************************************
   * Metodo: velocidadePateta
   * Funcao: Este método é responsável por atualizar a velocidade do Pateta 
   *         de acordo com o valor do Slider.
   * Parametros: nenhum
   * Retorno: void
   ***********************************************************************/
  @FXML
  public void velocidadePateta(){
    double valor = 55 - sliderVelocidadePateta.getValue();
    cPateta.setVelocidade(valor);
  }


  /*************************************************************************
   * Metodo: velocidadePateta
   * Funcao: Percuso feito pelos personagens
   * Parametros: nenhum
   * Retorno: void
   ***********************************************************************/
  public void percurso(int id){
    switch (id) {

      //percurso do max
      case 1:
        Platform.runLater(() -> {


          /********************************************************************************
           * A variável estadoMax indica o estado em que o Max está, dessa forma
           * quando o usuário clicar em pause, o personagem irá usar uma imagem semelhante
           * ao GIF que está usando no trecho que ele está.
           *******************************************************************************/
          if(max.getLayoutY() == 392  && max.getLayoutX() <= 670){
            if(estadoMax != "direita"){
              estadoMax = "direita";
              max.setImage(new Image("/images/Max/maxDireita.gif"));
            }
            max.setLayoutX(max.getLayoutX() + 1);
          }
  
          if(max.getLayoutX() > 670 && max.getLayoutX() < 771 && max.getLayoutY() > 292){
            if(estadoMax != "direitaSubindo"){
              estadoMax = "direitaSubindo";
              max.setImage(new Image("/images/Max/maxDireita.gif"));
            }
            max.setLayoutX(max.getLayoutX() + 1);
            max.setLayoutY(max.getLayoutY() - 1);
          }
  
          if(max.getLayoutX() == 771 && max.getLayoutY() > 165){
            if(estadoMax != "cima"){
              estadoMax = "cima";
              max.setImage(new Image("/images/Max/maxSubindo.gif"));
            }
            
            max.setLayoutY(max.getLayoutY() - 1);
          }
  
          if(max.getLayoutY() <= 165 && max.getLayoutX() > 694){
            if(estadoMax != "esquerdaSubindo"){
              estadoMax = "esquerdaSubindo";
              max.setImage(new Image("/images/Max/maxEsquerda.gif"));
            }
            max.setLayoutY(max.getLayoutY() - 1);
            max.setLayoutX(max.getLayoutX() - 1);
          }
  
          if(max.getLayoutX() <= 694 && max.getLayoutX() > 180 && max.getLayoutY() == 88){
            if(estadoMax != "esquerda"){
              estadoMax = "esquerda";
              max.setImage(new Image("/images/Max/maxEsquerda.gif"));
            }
            max.setLayoutX(max.getLayoutX() - 1);
          }
  
          if(max.getLayoutX() <= 180 && max.getLayoutX() > 90 && max.getLayoutY() >= 88 && max.getLayoutY() < 178){
            if(estadoMax != "esquerdaDescendo"){
              estadoMax = "esquerdaDescendo";
              max.setImage(new Image("/images/Max/maxEsquerda.gif"));
            }
            max.setLayoutX(max.getLayoutX() - 1);
            max.setLayoutY(max.getLayoutY() + 1);
          }
  
          if(max.getLayoutX() == 90 && max.getLayoutY() >= 178){
            if(estadoMax != "baixo"){
              estadoMax = "baixo";
              max.setImage(new Image("/images/Max/maxDescendo.gif"));
            }
            max.setLayoutY(max.getLayoutY() + 1);
          }
  
          if(max.getLayoutX() >= 90 && max.getLayoutX() < 402 && max.getLayoutY() >= 310 && max.getLayoutY() < 392){
            if(estadoMax != "direitaDescendo"){
              estadoMax = "direitaDescendo";
              max.setImage(new Image("/images/Max/maxDireita.gif"));
            }
            max.setLayoutX(max.getLayoutX() + 1);
            max.setLayoutY(max.getLayoutY() + 1);
          }

          if(max.getLayoutX() == 365 && max.getLayoutY() == 392){
            fimCorrida("Max");
          }

        });
        break;

        //percurso do pateta
      case 2:
        Platform.runLater(() -> {
          /********************************************************************************
           * A variável estadoMax indica o estado em que o Pateta está, dessa forma
           * quando o usuário clicar em pause, o personagem irá usar uma imagem semelhante
           * ao GIF que está usando no trecho que ele está.
           *******************************************************************************/

          if(pateta.getLayoutY() == 422 && pateta.getLayoutX() < 676){
            if(estadoPateta != "direita"){
              estadoPateta = "direita";
              pateta.setImage(new Image("/images/Pateta/patetaDireita.gif"));
            }
            pateta.setLayoutX(pateta.getLayoutX() + 1);
          }

          if(pateta.getLayoutX() > 675 && pateta.getLayoutX() < 805 && pateta.getLayoutY() > 305){
            if(estadoPateta != "direitaSubindo"){
              estadoPateta = "direitaSubindo";
              pateta.setImage(new Image("/images/Pateta/patetaDireita.gif"));
            }
            pateta.setLayoutX(pateta.getLayoutX() + 1.2);
            pateta.setLayoutY(pateta.getLayoutY() - 1);
          }

          if(pateta.getLayoutX() > 805 && pateta.getLayoutX() < 806 && pateta.getLayoutY() <= 314 && pateta.getLayoutY() > 159){
            if(estadoPateta != "cima"){
              estadoPateta = "cima";
              pateta.setImage(new Image("/images/Pateta/patetaSubindo.gif"));
            }
            pateta.setLayoutY(pateta.getLayoutY() - 1);
          }

          if(pateta.getLayoutX() <= 806 && pateta.getLayoutX() > 675 && pateta.getLayoutY() <= 159 && pateta.getLayoutY() > 50){
            if(estadoPateta != "esquerdaSubindo"){
              estadoPateta = "esquerdaSubindo";
              pateta.setImage(new Image("/images/Pateta/patetaEsquerda.gif"));
            }
            pateta.setLayoutY(pateta.getLayoutY() - 1);
            pateta.setLayoutX(pateta.getLayoutX() - 1.2);
          }

          if(pateta.getLayoutX() <= 675 && pateta.getLayoutX() > 165 && pateta.getLayoutY() == 50){
            if(estadoPateta != "esquerda"){
              estadoPateta = "esquerda";
              pateta.setImage(new Image("/images/Pateta/patetaEsquerda.gif"));
            }
            pateta.setLayoutX(pateta.getLayoutX() - 1);
          }

          if(pateta.getLayoutX() <= 165 && pateta.getLayoutY() >= 50 && pateta.getLayoutY() < 140){
            if(estadoPateta != "esquerdaDescendo"){
              estadoPateta = "esquerdaDescendo";
              pateta.setImage(new Image("/images/Pateta/patetaEsquerda.gif"));
            }
            pateta.setLayoutX(pateta.getLayoutX() - 1.2);
            pateta.setLayoutY(pateta.getLayoutY() + 1);
          }

          if(pateta.getLayoutX() > 56 && pateta.getLayoutX() < 57 && pateta.getLayoutY() >= 125 && pateta.getLayoutY() < 315){
            if(estadoPateta != "baixo"){
              estadoPateta = "baixo";
              pateta.setImage(new Image("/images/Pateta/patetaDescendo.gif"));
            }
            pateta.setLayoutY(pateta.getLayoutY() + 1);
          }

          if(pateta.getLayoutX() > 56 && pateta.getLayoutX() < 200 && pateta.getLayoutY() >= 315 && pateta.getLayoutY() < 422){
            if(estadoPateta != "direitaDescendo"){
              estadoPateta = "direitaDescendo";
              pateta.setImage(new Image("/images/Pateta/patetaDireita.gif"));
            }
            pateta.setLayoutX(pateta.getLayoutX() + 1.2);
            pateta.setLayoutY(pateta.getLayoutY() + 1);
          }

          if(pateta.getLayoutX() > 361 && pateta.getLayoutX() < 362 && pateta.getLayoutY() == 422){
            fimCorrida("Pateta");
          }
          
        });
        break;
    }
  }
}