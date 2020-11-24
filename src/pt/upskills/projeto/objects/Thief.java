package pt.upskills.projeto.objects;

import pt.upskills.projeto.game.Engine;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;
import pt.upskills.projeto.rogue.utils.Vector2D;

import java.util.Observable;
import java.util.Random;

public class Thief extends Enemy{

    public Thief(Position position) {
        super(position);
        setLife(50);
        setStrenght(20);
        setPoints(25);
        getMovimentosPossiveis().add(position);
        getMovimentosPossiveis().add(position.plus(new Vector2D(1,1)));
        getMovimentosPossiveis().add(position.plus(new Vector2D(-1,1)));
        getMovimentosPossiveis().add(position.plus(new Vector2D(1,-1)));
        getMovimentosPossiveis().add(position.plus(new Vector2D(-1,-1)));
    }
    @Override
    public boolean canMove(Position position){//para nao andar nas paredes
        boolean possivel=true;
        for(ImageTile tile: Engine.currentRoom.getTiles()) {
            if (tile.getPosition().equals(position)) {
                if (tile instanceof Wall|| tile instanceof Hero || tile instanceof Enemy || tile instanceof Door) {
                    for(Position position1:getMovimentosPossiveis()){
                        if(!position1.equals(position)){
                            possivel=false;
                            break;
                        }
                    }
                }
            }
        }
        return possivel;
    }
    @Override
    public void move(){//mover o Thief nos movimentos possiveis dados ao respetivo inimigo
        Position newPosition;//inicializar a novaposicao
        Random random=new Random();
        do {
            int n=random.nextInt(getMovimentosPossiveis().size());//decidir a nova posicao de forma random
            newPosition= getMovimentosPossiveis().get(n);//ir buscar a posicao random de indice n à lista de movimentos possiveis
        }while(!canMove(newPosition));//enquanto o proximo movimento nao for possivel pois bate numa parede, procurar outro possivel
        setPosition(newPosition);
        if(nextTo(Engine.hero.getPosition())){
            fightHero(Engine.hero);
        }

    }

    @Override
    public void moveClose(Hero hero){
        Position p=getPosition();
        if((hero.getPosition().getX()<getPosition().getY()) && (hero.getPosition().getY()==getPosition().getY())){
            p=getPosition().plus(new Vector2D(-1,-1));
        }if((hero.getPosition().getX()>getPosition().getX()) && (hero.getPosition().getY()==getPosition().getY())){//done
            p=getPosition().plus(new Vector2D(1,-1));
        }if((hero.getPosition().getY()>getPosition().getY()) && (hero.getPosition().getX()==getPosition().getX())){//done
            p=getPosition().plus(new Vector2D(1,1));
        }if((hero.getPosition().getY()<getPosition().getY()) && (hero.getPosition().getX()==getPosition().getX())){//done
            p=getPosition().plus(new Vector2D(-1,-1));
        }if((hero.getPosition().getX()>getPosition().getY()) && (hero.getPosition().getY()>getPosition().getY())){
            p=getPosition().plus(new Vector2D(1,1));
        }if((hero.getPosition().getX()>getPosition().getY()) && (hero.getPosition().getY()<getPosition().getY())){
            p=getPosition().plus(new Vector2D(1,-1));
        }if((hero.getPosition().getX()<getPosition().getY()) && (hero.getPosition().getY()>getPosition().getY())){
            p=getPosition().plus(new Vector2D(-1,1));
        }if((hero.getPosition().getX()<getPosition().getY()) && (hero.getPosition().getY()<getPosition().getY())){
            p=getPosition().plus(new Vector2D(-1,-1));
        }

        if(canMove(p)){
            setPosition(p);
        }
        if(nextTo(hero.getPosition())){
            fightHero(hero);
            hero.updateLifeBar();
        }
    }
    @Override
    public String getName() {
        return "Thief";
    }


    @Override
    public void update(Observable o, Object arg) {
        getMovimentosPossiveis().clear();
        getMovimentosPossiveis().add(getPosition());
        getMovimentosPossiveis().add(getPosition().plus(new Vector2D(1,1)));
        getMovimentosPossiveis().add(getPosition().plus(new Vector2D(-1,1)));
        getMovimentosPossiveis().add(getPosition().plus(new Vector2D(1,-1)));
        getMovimentosPossiveis().add(getPosition().plus(new Vector2D(-1,-1)));



    }
}
