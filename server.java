package example.hello;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Server implements Hello {

    private List<String> availableBooks;

    public Server() {
        this.availableBooks = new ArrayList<>(Arrays.asList(
            "1984", "Brave New World", "The Hobbit", "Clean Code"
        ));
    }

    @Override
    public String sayHello() {
        return "Hello, world!";
    }

    @Override
    public List<String> getAvailableBooks() throws RemoteException {
        return new ArrayList<>(availableBooks); // segurança contra modificações remotas
    }

    @Override
    public String borrowBook(String title) throws RemoteException {
        if (availableBooks.contains(title)) {
            availableBooks.remove(title);
            return "Você pegou emprestado: " + title;
        } else {
            return "Livro não disponível: " + title;
        }
    }

    @Override
    public void returnBook(String title) throws RemoteException {
        if (!availableBooks.contains(title)) {
            availableBooks.add(title);
        }
    }

    public static void main(String args[]) {
        try {
            Server obj = new Server();
            Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
