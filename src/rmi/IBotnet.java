/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.io.BufferedReader;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author haiclover
 */
public interface IBotnet extends Remote{
    public void testing() throws RemoteException;
    public ArrayList<String> runCommand(String cmd) throws RemoteException;

    public void installApp();
}
