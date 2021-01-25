package pt.upskills.projeto.objects;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;


public abstract class Item implements ImageTile {
    private Position position;
    private int points;

    public void setPosition(Position position) {
        this.position = position;
    }

    public Item(Position position){
        this.position=position;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Item{" +
                "position=" + position +
                '}';
    }
}
