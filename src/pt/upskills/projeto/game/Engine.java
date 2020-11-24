package pt.upskills.projeto.game;

import pt.upskills.projeto.gui.ImageMatrixGUI;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.*;
import pt.upskills.projeto.rogue.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Engine {
    public static List<ImageTile> status = new ArrayList<>(10);
    public static ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
    public static List<Room> rooms= new ArrayList<>();
    public static Hero hero= new Hero(new Position(0,0));
    public static int level=0;
    public static Room currentRoom;

    public static void readRooms(){
        File rooms= new File("rooms");
        int j=0;
        for(File f: Objects.requireNonNull(rooms.listFiles())){//guardar os quartos
            readMapFile(j);
            j++;

        }
    }

    public static void init(){


        readRooms();

        for(int i=0; i<10; i++){
            status.add(new Black(new Position(i, 0)));
        }


        changeRoom(hero, level);
        gui.go();


        while (true){
            gui.update();
        }
    }
    public static void restarGame(){
        for(int i=0; i<3; i++){
            hero.getInventario()[i]=null;
            hero.getFireBalls()[i]=null;
        }
        hero.setLife(hero.getTotalLife());
        hero.updateLifeBar();
        Engine.level=0;
        rooms.clear();
        readRooms();
        changeRoom(hero, 0);

    }

    public static void changeRoom(Hero hero, int level){
        gui.clearImages();
        for(Room room: rooms){
            if(room.getLevel()==level){
                currentRoom=room;
                break;
            }
        }
        currentRoom.getTiles().add(hero);
        currentRoom.getListaObservaveis().add(hero);
        for(Observer observer: currentRoom.getListaObservaveis()){
            gui.addObserver(observer);
        }
        gui.newImages(currentRoom.getTiles());
        gui.newStatusImages(status);
        gui.newStatusImages(hero.getLifeBar());
    }

    public static void readMapFile( int level) {
        List<ImageTile> tiles= new ArrayList<>();
        List<Door> listDoors= new ArrayList<>();
        List<Enemy> listEnemys= new ArrayList<>();
        List<Item> listItems= new ArrayList<>();
        List<Observer> listaObservaveis= new ArrayList<>();
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                tiles.add(new Floor(new Position(i, j)));
            }
        }
        try {
            Scanner fileScanner = new Scanner(new File("rooms/room" + level + ".txt"));
            int j = 0;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] caracteres = line.split("");
                if (caracteres[0].equals("#") && caracteres.length > 1) {
                    boolean chave = false;
                    boolean special=false;
                    int portaEntrada = Integer.parseInt(caracteres[2]);
                    int portaSaida= Integer.parseInt(caracteres[16]);
                    String tipo = caracteres[4];
                    int nivel = Integer.parseInt(caracteres[10]);
                    if (caracteres.length==19) {
                        chave = true;
                    }
                    if(caracteres.length > 19){
                        chave=true;
                        special=true;
                    }
                    Door door = new Door(portaEntrada,portaSaida, tipo, nivel, chave, special);
                    listDoors.add(door);


                }else {
                    if(!caracteres[0].equals("#")) {
                        for (int i = 0; i < caracteres.length; i++) {
                            switch (caracteres[i]) {
                                case ("W"):
                                    tiles.add(new Wall(new Position(i, j)));
                                    break;
                                case ("g"):
                                    tiles.add(new Grass(new Position(i, j)));
                                    break;
                                case ("e"):
                                    tiles.add(new Statue(new Position(i, j)));
                                    break;
                                case ("h"):
                                    hero.setPosition(new Position(i, j));
                                    break;
                                case ("S"):
                                    Skeleton skeleton = new Skeleton(new Position(i, j));
                                    listaObservaveis.add(skeleton);
                                    tiles.add(skeleton);
                                    listEnemys.add(skeleton);
                                    break;
                                case ("d"):
                                    DwarfKing dwarfKing = new DwarfKing(new Position(i, j));
                                    listaObservaveis.add(dwarfKing);
                                    tiles.add(dwarfKing);
                                    listEnemys.add(dwarfKing);
                                    break;
                                case ("B"):
                                    Bat bat = new Bat(new Position(i, j));
                                    listaObservaveis.add(bat);
                                    tiles.add(bat);
                                    listEnemys.add(bat);
                                    break;
                                case ("G"):
                                    BadGuy badguy = new BadGuy(new Position(i, j));
                                    listaObservaveis.add(badguy);
                                    tiles.add(badguy);
                                    listEnemys.add(badguy);
                                    break;
                                case ("s"):
                                    Sword sword = new Sword(new Position(i, j));
                                    tiles.add(sword);
                                    listItems.add(sword);
                                    break;
                                case ("H"):
                                    Hammer hammer = new Hammer(new Position(i, j));
                                    tiles.add(hammer);
                                    listItems.add(hammer);
                                    break;
                                case ("k"):
                                    Key key = new Key(new Position(i, j));
                                    tiles.add(key);
                                    listItems.add(key);
                                    break;
                                case("p")://p de projetil
                                    FireBall_Item fireBall= new FireBall_Item(new Position(i,j));
                                    tiles.add(fireBall);
                                    listItems.add(fireBall);
                                    break;
                                case ("f"):
                                    Food food = new Food(new Position(i, j));
                                    tiles.add(food);
                                    listItems.add(food);
                                    break;
                                case("t"):
                                    Thief thief= new Thief(new Position(i,j));
                                    tiles.add(thief);
                                    listEnemys.add(thief);
                                    listaObservaveis.add(thief);
                                    break;
                            }
                            for (Door door : listDoors) {
                                if ((caracteres[i]).equals(String.valueOf(door.getPortaEntrada()))) {
                                    door.setPosition(new Position(i, j));
                                    tiles.add(door);
                                }
                            }
                        }j++;

                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Nao foi possivel ler o ficheiro.");
        }
        Room room= new Room(tiles,listEnemys, listItems, listDoors, listaObservaveis, level);
        rooms.add(room);
    }


        public static void main(String[] args){
        Engine engine = new Engine();
        engine.init();
    }
}

