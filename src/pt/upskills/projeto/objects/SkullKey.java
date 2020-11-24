package pt.upskills.projeto.objects;

import pt.upskills.projeto.rogue.utils.Position;

public class SkullKey extends Item {
    public SkullKey(Position position) {
        super(position);
        setPoints(100);
    }

    @Override
    public String getName() {
        return "SkullKey";
    }
}
