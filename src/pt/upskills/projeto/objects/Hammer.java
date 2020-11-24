package pt.upskills.projeto.objects;

import pt.upskills.projeto.rogue.utils.Position;

public class Hammer extends Item {
    public Hammer(Position position) {
        super(position);
        setPoints(30);
    }

    @Override
    public String getName() {
        return "Hammer";
    }
}
