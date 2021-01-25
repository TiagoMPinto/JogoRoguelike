package pt.upskills.projeto.objects;

import pt.upskills.projeto.rogue.utils.Position;
import pt.upskills.projeto.rogue.utils.Vector2D;

import java.util.Observable;

public class Bat extends Enemy {


    public Bat(Position position) {
        super(position);
        setLife(30);
        setStrenght(4);
        getMovimentosPossiveis().add(position);
        getMovimentosPossiveis().add(position.plus(new Vector2D(1,1)));
        getMovimentosPossiveis().add(position.plus(new Vector2D(-1,1)));
        getMovimentosPossiveis().add(position.plus(new Vector2D(-1,-1)));
        getMovimentosPossiveis().add(position.plus(new Vector2D(1,-1)));
        setPoints(10);
    }


    @Override
    public String getName() {
        return "Bat";
    }


    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException();
    }
}
