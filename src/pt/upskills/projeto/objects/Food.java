package pt.upskills.projeto.objects;

import pt.upskills.projeto.rogue.utils.Position;

public class Food extends Item {
    public Food(Position position) {
        super(position);
    }


    @Override
    public String getName() {
        return "Food";
    }
}
