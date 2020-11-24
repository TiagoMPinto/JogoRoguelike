package pt.upskills.projeto.objects;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

public class Statue implements ImageTile {
    private Position position;

    public Statue(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Statue";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
