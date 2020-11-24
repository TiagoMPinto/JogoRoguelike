package pt.upskills.projeto.objects;

import pt.upskills.projeto.rogue.utils.Position;

import java.util.Observable;

public class Sword extends Item{

    public Sword(Position position) {
        super(position);
        setPoints(20);
    }


    @Override
    public String getName() {
        return "Sword";
    }
}
