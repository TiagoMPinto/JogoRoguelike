package pt.upskills.projeto.objects;

import pt.upskills.projeto.game.Engine;
import pt.upskills.projeto.rogue.utils.Position;
import pt.upskills.projeto.rogue.utils.Vector2D;

import java.util.Observable;

public class DwarfKing extends Enemy {
    private int i=0;


    public DwarfKing(Position position) {
        super(position);
        setLife(100);
        setStrenght(15);
        setPoints(500);
        getMovimentosPossiveis().add(position);
        getMovimentosPossiveis().add(position.plus(new Vector2D(1,0)));
        getMovimentosPossiveis().add(position.plus(new Vector2D(-1,0)));
        getMovimentosPossiveis().add(position.plus(new Vector2D(0,-1)));
        getMovimentosPossiveis().add(position.plus(new Vector2D(0,1)));
        getMovimentosPossiveis().add(position.plus(new Vector2D(1,1)));
        getMovimentosPossiveis().add(position.plus(new Vector2D(1,-1)));
        getMovimentosPossiveis().add(position.plus(new Vector2D(-1,1)));
        getMovimentosPossiveis().add(position.plus(new Vector2D(-1,-1)));

    }

    @Override
    public void update(Observable o, Object arg) {
        getMovimentosPossiveis().clear();
        getMovimentosPossiveis().add(getPosition());
        getMovimentosPossiveis().add(getPosition().plus(new Vector2D(1,0)));
        getMovimentosPossiveis().add(getPosition().plus(new Vector2D(-1,0)));
        getMovimentosPossiveis().add(getPosition().plus(new Vector2D(0,-1)));
        getMovimentosPossiveis().add(getPosition().plus(new Vector2D(0,1)));
        getMovimentosPossiveis().add(getPosition().plus(new Vector2D(1,1)));
        getMovimentosPossiveis().add(getPosition().plus(new Vector2D(1,-1)));
        getMovimentosPossiveis().add(getPosition().plus(new Vector2D(-1,1)));
        getMovimentosPossiveis().add(getPosition().plus(new Vector2D(-1,-1)));
        if(!isAlive() && i==0){
            SkullKey skullKey= new SkullKey(getPosition());
            Engine.currentRoom.getTiles().add(skullKey);
            Engine.currentRoom.getListItems().add(skullKey);
            Engine.gui.addImage(skullKey);
            i++;
        }
    }

    @Override
    public String getName() {
        return "DwarfKing";
    }
}
