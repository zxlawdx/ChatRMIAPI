package chatrmi.remote;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SendMessageService extends Remote {
    String sendMessage(String sessionUserEmail, String remoteUserEmail, String message) throws RemoteException;
}
