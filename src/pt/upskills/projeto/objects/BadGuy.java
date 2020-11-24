package pt.upskills.projeto.objects;

import pt.upskills.projeto.rogue.utils.Position;
import pt.upskills.projeto.rogue.utils.Vector2D;

import java.util.Observable;

public class BadGuy extends Enemy {
    public BadGuy(Position position) {
        super(position);
        setLife(70);
        setStrenght(11);
        setPoints(30);
        getMovimentosPossiveis().add(position);
        getMovimentosPossiveis().add(position.plus(new Vector2D(0,1)));
        getMovimentosPossiveis().add(position.plus(new Vector2D(-1,0)));


    }



    @Override
    public String getName() {
        return "BadGuy";
    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
