package pt.upskills.projeto.objects;

import pt.upskills.projeto.rogue.utils.Position;

public class Key extends Item {

    public Key(Position position) {
        super(position);
        setPoints(50);
    }


    @Override
    public String getName() {
        return "Key";
    }
}
