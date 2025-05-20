package example.hello;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class Client {

    public static void main(String[] args) {

        System.out.println("Initiating client");

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            System.out.println("Registry has been located");

            Hello stub = (Hello) registry.lookup("Hello");
            System.out.println("Found server");

            // Método original
            String response = stub.sayHello();
            System.out.println("sayHello response: " + response);

            // Métodos novos
            System.out.println("\n📚 Livros disponíveis:");
            List<String> books = stub.getAvailableBooks();
            books.forEach(System.out::println);

            System.out.println("\n📖 Tentando pegar 'The Hobbit'...");
            String borrowResponse = stub.borrowBook("The Hobbit");
            System.out.println(borrowResponse);

            System.out.println("\n📚 Livros após empréstimo:");
            stub.getAvailableBooks().forEach(System.out::println);

            System.out.println("\n↩️ Devolvendo 'The Hobbit'...");
            stub.returnBook("The Hobbit");

            System.out.println("\n📚 Livros após devolução:");
            stub.getAvailableBooks().forEach(System.out::println);

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
