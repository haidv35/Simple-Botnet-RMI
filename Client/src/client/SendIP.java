/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ligirk
 */
public class SendIP extends Thread{
    private String ip;
    
    public SendIP(String ip){
        this.ip = ip;
    }
    
    @Override
    public void run(){
        while(true){
            try {
                Socket socket = new Socket("127.0.0.1", 2345);
                BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(socket.getOutputStream()));
                if (ip != null){
                    writer.write(ip);
                    writer.write("\n");
                    writer.flush();
                }
                writer.close();
                socket.close();
                System.out.println(this.ip);
                break;
            } catch (IOException ex) {
                
            }
        }
    }
    
}