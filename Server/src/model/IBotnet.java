/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author ann52
 */
public interface IBotnet extends Remote{
    public void testing() throws RemoteException;
    public ArrayList<String> runCommand(String cmd) throws RemoteException;
    public ArrayList<String> installApp(String cmd) throws RemoteException;
}
