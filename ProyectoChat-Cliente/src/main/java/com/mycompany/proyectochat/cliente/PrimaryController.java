package com.mycompany.proyectochat.cliente;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class PrimaryController implements Initializable {
    
    @FXML
    private Button btn_usuario;
    @FXML
    private Button btn_mensaje;
    @FXML
    private TextField caja_usuario;
    @FXML
    private TextField caja_mensaje;
    @FXML
    private VBox chat;
    
    Cliente cliente;
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        
       
        
        try{
            
            cliente = new Cliente(new Socket("127.0.0.1", 5000));
            
        }catch(Exception e){
            
        }
        
        cliente.recibirMensajeServidor(chat);
        
        btn_mensaje.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event){
                
                String mensaje = caja_mensaje.getText();
                
                if (!mensaje.isEmpty()) {
                    
                    Text text = new Text(mensaje);
                    text.setFill(Color.BLUE);
                    TextFlow text_flow = new TextFlow(text);
                    chat.getChildren().add(text_flow);
                    
                    cliente.mandarMensajeServidor(mensaje);
                    caja_mensaje.clear();
                    
                }
                
            }
        });
    }
    
    public static void addTexto(String mensaje_recibido, VBox vbox){
        
        Text text = new Text(mensaje_recibido);
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
