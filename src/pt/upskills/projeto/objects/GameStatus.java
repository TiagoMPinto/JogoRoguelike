package pt.upskills.projeto.objects;

import pt.upskills.projeto.game.Engine;
import pt.upskills.projeto.gui.ImageMatrixGUI;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Scanner;

public class GameStatus {
    public static void saveGame(){
        try {
            File saveRogue = new File("Save.txt");
            PrintWriter myWritter= new PrintWriter(saveRogue);
            StringBuilder linha= new StringBuilder();

            //primeira linha Hero info
            linha.append(Engine.hero.getLife()).append(";").append(Engine.hero.getPosition().getX()).append(";").append(Engine.hero.getPosition().getY()).append(";").append(Engine.hero.getNumFireBalls()).append(";").append(Engine.hero.getNumItem());

            for(Item item: Engine.hero.getInventario()){
                if(item!=null){
                    linha.append(";").append(item.getName()).append(";").append(item.getPosition().getX()).append(";").append(item.getPosition().getY());
                }else{
                    linha.append(";"+"X").append(";"+"X").append(";"+"X");
                }
            }
            linha.append(";").append(Engine.currentRoom.getLevel());

            myWritter.println(linha);
            linha.delete(0, linha.capacity());
            //linhas seguintes info sobre os quartos
            for(Room room:Engine.rooms){
                for(ImageTile tile: room.getTiles()){//primeira linha TILES
                    linha.append(tile.getName()).append(";").append(tile.getPosition().getX()).append(";").append(tile.getPosition().getY()).append(";");
                }

                myWritter.println(linha);
                linha.delete(0, linha.capacity());
                for(Enemy enemy: room.getListEnemys()){//segunda linha ENEMYS
                    linha.append(enemy.getName()).append(";").append(enemy.getPosition().getX()).append(";").append(enemy.getPosition().getY()).append(";").append(enemy.getLife()).append(";");
                }

                myWritter.println(linha);
                linha.delete(0, linha.capacity());
                for(Item item: room.getListItems()){//terceira linha ITEMS
                    linha.append(item.getName()).append(";").append(item.getPosition().getX()).append(";").append(item.getPosition().getY()).append(";");
                }

                myWritter.println(linha);
                linha.delete(0, linha.capacity());
                for(Door door: room.getListDoors()){//quarta linha DOORS
                    linha.append(door.getPortaEntrada()).append(";").append(door.getPortaSaida()).append(";").append(door.getTipo()).append(";").append(door.getNivel()).append(";").append(door.isClosed()).append(";").append(door.isSpecial()).append(";").append(door.getPosition().getX()).append(";").append(door.getPosition().getY()).append(";");
                }
                myWritter.println(linha);
                linha.delete(0, linha.capacity());
            }
            myWritter.close();

        }catch (IOException e){
            System.out.println("It was not possible to save the game.");
            e.printStackTrace();
        }
    }




    public static void loadGame(){
        List<Room> roomsLoad= new ArrayList<>();
        int currentNivel = 0;
        int xHero=0;
        int yHero=0;
        List<ImageTile> tiles= new ArrayList<>();
        List<Door> listDoors= new ArrayList<>();
        List<Enemy> listEnemys= new ArrayList<>();
        List<Item> listItems= new ArrayList<>();
        List<Observer> listaObservaveis= new ArrayList<>();
        Engine.rooms.clear();
        try{
            Scanner fileScanner = new Scanner(new File("Save.txt"));
            int j=0;
            int i=0;
            int level=0;
            while (fileScanner.hasNextLine()){
                if(i==0){
                    String heroLine =fileScanner.nextLine();
                    String[] heroInfo= heroLine.split(";");
                    Engine.hero.setLife(Integer.parseInt(heroInfo[0]));
                    currentNivel=Integer.parseInt(heroInfo[14]);
                    xHero=Integer.parseInt(heroInfo[1]);
                    yHero=Integer.parseInt(heroInfo[2]);
                    Engine.hero.setPosition(new Position(xHero, yHero));
                    if(!heroInfo[3].equals("X")){
                        for(int l=0; l<Integer.parseInt(heroInfo[3]); l++){//Adicionar as bolas de fogo
                            Engine.hero.getFireBalls()[l]=new FireBall_Item(new Position(l,0) );
                        }
                        for(int l=Integer.parseInt(heroInfo[3]); l<3; l++){
                            Engine.hero.getFireBalls()[l]=null;
                        }
                    }
                    if(!heroInfo[4].equals("0")){
                        for(int k=0; k<3; k++){
                            switch (heroInfo[5+k*3]){
                                case "X":
                                    Engine.hero.getInventario()[k]=null;
                                    break;
                                case "Sword":
                                    Engine.hero.getInventario()[k]=new Sword(new Position(6+(k*3), 7+(k*3)));
                                    break;
                                case "Hammer":
                                    Engine.hero.getInventario()[k]=new Hammer(new Position(6+(k*3), 7+(k*3)));
                                    break;
                                case "Key":
                                    Engine.hero.getInventario()[k]=new Key(new Position(6+(k*3), 7+(k*3)));
                                    break;
                                case "SkullKey":
                                    Engine.hero.getInventario()[k]=new SkullKey(new Position(6+(k*3), 7+(k*3)));
                                    break;
                            }

                        }
                        i++;
                    }

                }
                if(j==1 && i!=0){
                    String tilesLine =fileScanner.nextLine();
                    String[] tilesInfo= tilesLine.split(";");
                    int n=0;
                    for(int m=0; m< tilesInfo.length; m+=3){
                        switch (tilesInfo[m]){
                            case "Hero":
                                if(n==0){//devido a um erro ainda nao corrigido de sobreposicao de tiles Hero
                                    tiles.add(Engine.hero);
                                    listaObservaveis.add(Engine.hero);
                                    n++;
                                }
                                break;
                            case "Wall":
                                tiles.add(new Wall(new Position(Integer.parseInt(tilesInfo[m+1]),Integer.parseInt(tilesInfo[m+2]) )));
                                break;
                            case "Floor":
                                tiles.add(new Floor(new Position(Integer.parseInt(tilesInfo[m+1]),Integer.parseInt(tilesInfo[m+2]) )));
                                break;
                            case "Grass":
                                tiles.add(new Grass(new Position(Integer.parseInt(tilesInfo[m+1]),Integer.parseInt(tilesInfo[m+2]) )));
                                break;
                            case "Statue":
                                tiles.add(new Statue(new Position(Integer.parseInt(tilesInfo[m+1]),Integer.parseInt(tilesInfo[m+2]) )));
                                break;

                            //adicionar Portas depois
                        }
                    }
                }
                if(j==2 && i!=0){
                    String enemysLine =fileScanner.nextLine();
                    String[] enemysInfo= enemysLine.split(";");
                    for(int p=0; p< enemysInfo.length; p+=4){
                        switch (enemysInfo[p]){
                            case "Skeleton":
                                Skeleton skeleton=new Skeleton(new Position(Integer.parseInt(enemysInfo[p+1]),Integer.parseInt(enemysInfo[p+2])));
                                skeleton.setLife(Integer.parseInt(enemysInfo[p+3]));
                                listEnemys.add(skeleton);
                                listaObservaveis.add(skeleton);
                                tiles.add(skeleton);
                                break;
                            case "Bat":
                                Bat bat=new Bat(new Position(Integer.parseInt(enemysInfo[p+1]),Integer.parseInt(enemysInfo[p+2])));
                                bat.setLife(Integer.parseInt(enemysInfo[p+3]));
                                listEnemys.add(bat);
                                listaObservaveis.add(bat);
                                tiles.add(bat);
                                break;
                            case "Thief":
                                Thief thief=new Thief(new Position(Integer.parseInt(enemysInfo[p+1]),Integer.parseInt(enemysInfo[p+2])));
                                thief.setLife(Integer.parseInt(enemysInfo[p+3]));
                                listEnemys.add(thief);
                                listaObservaveis.add(thief);
                                tiles.add(thief);
                                break;
                            case "BadGuy":
                                BadGuy badGuy=new BadGuy(new Position(Integer.parseInt(enemysInfo[p+1]),Integer.parseInt(enemysInfo[p+2])));
                                badGuy.setLife(Integer.parseInt(enemysInfo[p+3]));
                                listEnemys.add(badGuy);
                                listaObservaveis.add(badGuy);
                                tiles.add(badGuy);
                                break;
                            case "DwarfKing":
                                DwarfKing dwarf=new DwarfKing(new Position(Integer.parseInt(enemysInfo[p+1]),Integer.parseInt(enemysInfo[p+2])));
                                dwarf.setLife(Integer.parseInt(enemysInfo[p+3]));
                                listEnemys.add(dwarf);
                                listaObservaveis.add(dwarf);
                                tiles.add(dwarf);
                                break;
                        }
                    }
                }
                if(j==3 && i!=0){
                    String itemsLine =fileScanner.nextLine();
                    String[] itemsInfo= itemsLine.split(";");
                    for(int p=0; p< itemsInfo.length; p+=3) {
                        switch (itemsInfo[p]) {
                            case "Key":
                                Key key=new Key(new Position(Integer.parseInt(itemsInfo[p + 1]), Integer.parseInt(itemsInfo[p + 2])));
                                listItems.add(key);
                                tiles.add(key);
                                break;
                            case "Food":
                                Food food=new Food(new Position(Integer.parseInt(itemsInfo[p + 1]), Integer.parseInt(itemsInfo[p + 2])));
                                listItems.add(food);
                                tiles.add(food);
                                break;
                            case "Sword":
                                Sword sword=new Sword(new Position(Integer.parseInt(itemsInfo[p + 1]), Integer.parseInt(itemsInfo[p + 2])));
                                listItems.add(sword);
                                tiles.add(sword);
                                break;
                            case "Hammer":
                                Hammer hammer=new Hammer(new Position(Integer.parseInt(itemsInfo[p + 1]), Integer.parseInt(itemsInfo[p + 2])));
                                listItems.add(hammer);
                                tiles.add(hammer);
                                break;
                            case "Fire":
                                FireBall_Item fire=new FireBall_Item(new Position(Integer.parseInt(itemsInfo[p + 1]), Integer.parseInt(itemsInfo[p + 2])));
                                listItems.add(fire);
                                tiles.add(fire);
                                break;
                            case "SkullKey":
                                SkullKey skullKey=new SkullKey(new Position(Integer.parseInt(itemsInfo[p + 1]), Integer.parseInt(itemsInfo[p + 2])));
                                listItems.add(skullKey);
                                tiles.add(skullKey);
                                break;
                        }
                    }
                }
                if(j==4 && i!=0){//linha das portas
                    String doorsLine =fileScanner.nextLine();
                    String[] doorInfo= doorsLine.split(";");
                    for(int p=0; p< doorInfo.length; p+=8) {
                        boolean closed=false;
                        boolean special=false;
                        if(doorInfo[p + 4].equals("true")){
                            closed=true;
                        }
                        if(doorInfo[p + 5].equals("true")){
                            special=true;
                        }
                        Door door= new Door(Integer.parseInt(doorInfo[p]),Integer.parseInt(doorInfo[p+1]), doorInfo[p+2], Integer.parseInt(doorInfo[p+3]), closed, special, new Position(Integer.parseInt(doorInfo[p+6]), Integer.parseInt(doorInfo[p+7])));
                        listDoors.add(door);
                        tiles.add(door);

                    }

                    Room room= new Room(tiles,listEnemys, listItems, listDoors, listaObservaveis, level);
                    roomsLoad.add(room);
                    tiles.clear();
                    listDoors.clear();
                    listEnemys.clear();
                    listItems.clear();
                    listaObservaveis.clear();
                    level++;

                    j=0;
                }


                j++;
                i++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("Nao foi possivel carregar o jogo guardado.");
            e.printStackTrace();
        }
        Engine.gui.clearImages();
        Engine.rooms=new ArrayList(roomsLoad);
        Engine.changeRoom(Engine.hero, currentNivel);
    }
}
