package pt.upskills.projeto.objects;

import pt.upskills.projeto.game.Engine;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;
import pt.upskills.projeto.rogue.utils.Vector2D;

import java.util.*;

public class Skeleton extends Enemy {


    public Skeleton(Position position) {
        super(position);
        setLife(25);
        setStrenght(6);
        setPoints(15);
        getMovimentosPossiveis().add(position);
        getMovimentosPossiveis().add(position.plus(new Vector2D(0,1)));
        getMovimentosPossiveis().add(position.plus(new Vector2D(0,-1)));
        getMovimentosPossiveis().add(position.plus(new Vector2D(1,0)));
        getMovimentosPossiveis().add(position.plus(new Vector2D(-1,0)));
        getMovimentosPossiveis().add(position.plus(new Vector2D(-1,1)));
        getMovimentosPossiveis().add(position.plus(new Vector2D(1,1)));
        getMovimentosPossiveis().add(position.plus(new Vector2D(1,-1)));

    }



    @Override
    public String getName() {
        return "Skeleton";
    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
