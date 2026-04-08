package chatrmi.remote;

import chatrmi.dto.UserSummaryDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface UserQueryService extends Remote {

    List<UserSummaryDto> listUsers(String excludeEmail)
            throws RemoteException;

}