package com.mycompany.proyectochat.cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.scene.layout.VBox;

public class Cliente {
    
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    
    public Cliente(Socket socket){
        
        try{
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.getLogger(Cliente.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            cerrarTodo(this.socket, this.in, this.out);
        }
        
    }
    
    
    public void cerrarTodo(Socket socket, DataInputStream in, DataOutputStream out){
        try{
            
            if (in != null) {
                in.close();
            }
            if (out != null){
                out.close();
            }
            if (socket != null){
                socket.close();
            }
            
        }catch(IOException e){
            
        }
    }
    
    public void recibirMensajeServidor(VBox vBox){
        
        new Thread(new Runnable(){
            
            @Override
            public void run(){
                while(socket.isConnected()) {
                    String mensaje_recibido;
                    try {
                    
                        mensaje_recibido = in.readUTF();
                        PrimaryController.addTexto(mensaje_recibido, vBox);
                    
                    } catch (IOException ex) {
                        cerrarTodo(socket, in, out);
                        break;
                    }
                    
                }
            }
                
        }).start();
        
    }
            
    public void mandarMensajeServidor(String mensaje){
        
        try{
            out.writeUTF(mensaje);
            out.flush();
        }catch(IOException e){
            cerrarTodo(socket, in, out);
        }
    }
    
}
