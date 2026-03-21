package chatrmi.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface LogonService extends Remote{
    
    void createUser(String username, String email, String password) throws RemoteException;
}
