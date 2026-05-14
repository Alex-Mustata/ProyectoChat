package com.mycompany.proyectochat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.scene.layout.VBox;

public class Server {
    
    private ServerSocket servidor_socket;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    
    public Server(ServerSocket servidor_socket){
        
        try{
            
            this.servidor_socket = servidor_socket;
            this.socket = servidor_socket.accept();
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            
        } catch (IOException ex) {
            cerrarTodo(socket, in, out);
        }
        
    }
    
    public void enviarMensajeCliente(String mensaje){
        
        try{
            out.writeUTF(mensaje);
            out.flush();
        }catch(IOException e){
            cerrarTodo(socket, in, out);
        }
        
    }
    
    public void recibirMensajeCliente(VBox vBox){
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
            
}
