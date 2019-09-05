package models;
import java.lang.Thread;
import controller.Controller;

public class Corredor extends Thread{
  private double velocidade;
  private int id;
  private Controller controller;

  public Corredor(int id, Controller controller){
    this.velocidade = 26;
    this.id = id;
    this.controller = controller;
  }

  @Override
  public void run() {
    while(true){
      try {

        this.controller.percurso(this.id);
  
        sleep((int) this.velocidade);
  
      }catch (Exception e){
        e.printStackTrace();
      }
    }
  }

  public void setVelocidade(double velocidade){
    this.velocidade = velocidade;
  }

}