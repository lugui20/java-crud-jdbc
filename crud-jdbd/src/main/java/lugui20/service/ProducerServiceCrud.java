

package lugui20.service;


import lugui20.dominio.Producer;
import java.util.Optional;
import java.util.Scanner;
import lugui20.repository.ProducerRepositoryCrud;

/**
 *
 * @author lusta
 */
public class ProducerServiceCrud {
    
    private static final Scanner SCANNER = new Scanner(System.in);
    
    public static void buildMenu(int op) {
        switch (op) {
            case 1:
                create();
                break;
            case 2:
                read();
                break;
            case 3:
                update();
                break;
            case 4:
                delete();
                break;
            //default:
            //    throw new IllegalArgumentException("Not a valid option");
        }
    }
    
    private static void create() {
        System.out.println("Type the name of the new producer:");
        String name = SCANNER.nextLine();
        Producer producer = Producer.builder().name(name).build();
        ProducerRepositoryCrud.create(producer);
    }
    
    public static void read () {
        System.out.println("Type the name or empty to all:");
        String name = SCANNER.nextLine();
        ProducerRepositoryCrud.read(name)
                .forEach(p -> System.out.printf("ID: %d | %s%n", p.getId(), p.getName()));
    }
    
    private static void update() {
        System.out.println("Type the IDs of the producer you want to update:");
        Optional<Producer> producerOptional = ProducerRepositoryCrud.findById(Integer.parseInt(SCANNER.nextLine()));
        if(producerOptional.isEmpty()) {          
            System.out.println("Producer not found");
            return;
        }
        Producer producerFromDb = producerOptional.get();
        System.out.println("Type the new name for the specified id:");
        String newName = SCANNER.nextLine();
        newName = newName.isEmpty() ? producerFromDb.getName() : newName;
        Producer producer = Producer.builder().id(producerFromDb.getId()).name(newName).build();
        ProducerRepositoryCrud.update(producer);
    }
    
    private static void delete() {
        System.out.println("Type the IDs of the producer you want to delete:");
        int id = Integer.parseInt(SCANNER.nextLine());
        System.out.println("Are you sure? Y/N");
        String choise = SCANNER.nextLine();
        if("Y".equalsIgnoreCase(choise)) {
            ProducerRepositoryCrud.delete(id);
        }
    }
    
    
    
}
