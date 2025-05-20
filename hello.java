package example.hello;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Hello extends Remote {
    String sayHello() throws RemoteException;

    // Novos métodos:
    List<String> getAvailableBooks() throws RemoteException;
    String borrowBook(String title) throws RemoteException;
    void returnBook(String title) throws RemoteException;
}
