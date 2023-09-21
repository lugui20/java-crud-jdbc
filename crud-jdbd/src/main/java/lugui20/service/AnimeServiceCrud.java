
package lugui20.service;


import lugui20.dominio.Anime;
import lugui20.dominio.Producer;
import java.util.Optional;
import java.util.Scanner;
import lugui20.repository.AnimeRepositoryCrud;

public class AnimeServiceCrud {
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
        System.out.println("Type the name of the new anime:");
        String name = SCANNER.nextLine();
        System.out.println("Type the number of episodes:");
        Integer episodes = Integer.parseInt(SCANNER.nextLine());
        System.out.println("Type the ID of the producer:");
        Integer producerId = Integer.parseInt(SCANNER.nextLine());
        Anime anime = Anime.builder()
                .name(name)
                .episodes(episodes)
                .producer(Producer.builder().id(producerId).build())
                .build();
        AnimeRepositoryCrud.create(anime);
    }
    
    public static void read () {
        System.out.println("Type the name or empty to all:");
        String name = SCANNER.nextLine();
        AnimeRepositoryCrud.read(name)
                .forEach(a -> System.out.printf("ID: %d. Name: %s. Episodes: %d. Producer: %s%n", 
                        a.getId(), a.getName(), a.getEpisodes(), a.getProducer().getName()));
    }
    
    private static void update() {
        System.out.println("Type the ID of the anime you want to update:");
        Optional<Anime> animeOptional = AnimeRepositoryCrud.findById(Integer.parseInt(SCANNER.nextLine()));
        if(animeOptional.isEmpty()) {          
            System.out.println("Anime not found");
            return;
        }
        Anime animeFromDb = animeOptional.get();
        
        System.out.println("Type the new name for the specified id:");
        String newName = SCANNER.nextLine();
        newName = newName.isEmpty() ? animeFromDb.getName() : newName;
        
        System.out.println("Type the new number of episodes:");
        Integer episodes = Integer.parseInt(SCANNER.nextLine());
        
        Anime anime = Anime.builder()
                .id(animeFromDb.getId())
                .name(newName)
                .episodes(episodes)
                .build();
        AnimeRepositoryCrud.update(anime);
    }
    
    private static void delete() {
        System.out.println("Type the IDs of the anime you want to delete:");
        int id = Integer.parseInt(SCANNER.nextLine());
        System.out.println("Are you sure? Y/N");
        String choise = SCANNER.nextLine();
        if("Y".equalsIgnoreCase(choise)) {
            AnimeRepositoryCrud.delete(id);
        }
    }
    
    
    
}
