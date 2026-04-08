package chatrmi.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoginService extends Remote{
    boolean login(String userEmail, String userPassword) throws RemoteException;
}
