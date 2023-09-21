
package lugui20;

import java.util.Scanner;
import lugui20.service.AnimeServiceCrud;
import lugui20.service.ProducerServiceCrud;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    
    public static void main(String[] args) {
        int op;
        while(true) {
            menu();
            op = Integer.parseInt(SCANNER.nextLine());
            if(op == 0) break;
            switch (op) {
                case 1:
                    producerMenu();
                    op = Integer.parseInt(SCANNER.nextLine());
                    ProducerServiceCrud.buildMenu(op);
                    break;
                case 2:
                    animeMenu();
                    op = Integer.parseInt(SCANNER.nextLine());
                    AnimeServiceCrud.buildMenu(op);
                    break;
            }
        }
    }
    
    private static void menu() {
        System.out.println("Type the number of your operation");
        System.out.println("1. Producer");
        System.out.println("2. Anime");
        System.out.println("0. Exit");
    }
    
    private static void producerMenu() {
        System.out.println("Type the number of your operation");
        System.out.println("1. Insert new producer");
        System.out.println("2. Search for producer/List producers");
        System.out.println("3. Update producer");
        System.out.println("4. Delete producer");
        System.out.println("9. Go Back");
    }
    
    
    private static void animeMenu() {
        System.out.println("Type the number of your operation");
        System.out.println("1. Insert new anime");
        System.out.println("2. Search for producer/List animes");
        System.out.println("3. Update anime");
        System.out.println("4. Delete anime");
        System.out.println("9. Go Back");
    }
}
