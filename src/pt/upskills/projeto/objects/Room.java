package pt.upskills.projeto.objects;

import pt.upskills.projeto.gui.ImageTile;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Scanner;

public class Room {
    private List<ImageTile> tiles;
    private List<Enemy> listEnemys;
    private List<Item> listItems;
    private List<Door> listDoors;
    private List<Observer> listaObservaveis;
    private int level;


    public Room(List<ImageTile> tiles, List<Enemy> listEnemys, List<Item> listItems, List<Door> listDoors, List<Observer> listaObservaveis, int level) {
        this.tiles = new ArrayList(tiles);
        this.listEnemys = new ArrayList(listEnemys);
        this.listItems = new ArrayList(listItems);
        this.listDoors =new ArrayList(listDoors);
        this.listaObservaveis = new ArrayList(listaObservaveis);
        this.level = level;
    }

    public List<ImageTile> getTiles() {
        return tiles;
    }

    public void setTiles(List<ImageTile> tiles) {
        this.tiles = tiles;
    }

    public List<Enemy> getListEnemys() {
        return listEnemys;
    }

    public void setListEnemys(List<Enemy> listEnemys) {
        this.listEnemys = listEnemys;
    }

    public List<Item> getListItems() {
        return listItems;
    }

    public void setListItems(List<Item> listItems) {
        this.listItems = listItems;
    }

    public List<Door> getListDoors() {
        return listDoors;
    }

    public void setListDoors(List<Door> listDoors) {
        this.listDoors = listDoors;
    }

    public List<Observer> getListaObservaveis() {
        return listaObservaveis;
    }

    public void setListaObservaveis(List<Observer> listaObservaveis) {
        this.listaObservaveis = listaObservaveis;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Room{" +
                "tiles=" + tiles +
                ", listEnemys=" + listEnemys +
                ", listItems=" + listItems +
                ", listDoors=" + listDoors +
                ", listaObservaveis=" + listaObservaveis +
                ", level=" + level +
                '}';
    }
}
