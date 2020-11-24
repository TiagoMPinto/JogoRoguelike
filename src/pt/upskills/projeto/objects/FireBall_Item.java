package pt.upskills.projeto.objects;

import pt.upskills.projeto.rogue.utils.Position;

public class FireBall_Item extends Item {
    public FireBall_Item(Position position) {
        super(position);
        setPoints(60);
    }

    @Override
    public String getName() {
        return "Fire";
    }
}
