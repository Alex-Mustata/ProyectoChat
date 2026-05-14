package com.mycompany.proyectochat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class PrimaryController implements Initializable{

    @FXML
    private Button btn_apagar;
    @FXML
    private Button btn_mensaje;
    @FXML
    private TextField caja_mensaje;
    @FXML
    private VBox chat;
    
    private Server server;
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        
        try{
             server = new Server(new ServerSocket(5000));
            
        } catch (IOException ex) {
            System.getLogger(PrimaryController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        server.recibirMensajeCliente(chat);
        
        btn_mensaje.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event){
                String mensaje_enviado = caja_mensaje.getText();
                
                if (!mensaje_enviado.isEmpty()) {
                    Text text = new Text(mensaje_enviado);
                    text.setFill(Color.BLUE);
                    TextFlow text_flow = new TextFlow(text);
                    
                    chat.getChildren().add(text_flow);
                    
                    server.enviarMensajeCliente(mensaje_enviado);
                    caja_mensaje.clear();
                }
            }
            
        });
        
        btn_apagar.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event){
               System.exit(0);
            }
            
        });
    } 
    
    public static void addTexto(String mensaje_cliente, VBox vbox){
        Text text = new Text(mensaje_cliente);
        text.setFill(Color.RED);
        TextFlow text_flow = new TextFlow(text);
        
        
        Platform.runLater(new Runnable(){
            
            @Override
            public void run(){
                vbox.getChildren().add(text_flow);
            }
        });
    }
}
