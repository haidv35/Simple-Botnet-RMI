/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
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
    
}
