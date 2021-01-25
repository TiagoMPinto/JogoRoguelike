package pt.upskills.projeto.objects;

import pt.upskills.projeto.game.Engine;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;
import pt.upskills.projeto.rogue.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.util.Observer;

public abstract class Enemy implements ImageTile, Observer {
     private Position position;
     private int life;
     private int strength;
     private List<Position> movimentosPossiveis;
     private int points;


    public Enemy(Position position) {
        this.position = position;
        movimentosPossiveis=new ArrayList<>();
    }

    public int getLife(){
        return life;
    }

    public void setLife(int life){
        this.life = life;
    }
    public void takeDamage(int damage){
        setLife(getLife()-damage);
    }

    public int getStrength(){
        return strength;
    }

    public void setStrenght(int strength){
        this.strength=strength;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Position> getMovimentosPossiveis() {
        return movimentosPossiveis;
    }

    public boolean isAlive(){
        boolean alive=(life>=0);
        if(!alive) {
            Engine.hero.updatePoints(this.getPoints());
            Engine.currentRoom.getTiles().remove(this);
            Engine.currentRoom.getListEnemys().remove(this);
            Engine.gui.removeImage(this);
        }
        return alive;
    }

    public void dropItem(){
        Random rand= new Random();
        int r= rand.nextInt(4);
        if(r==2) {
            Food food = new Food(getPosition());
            Engine.currentRoom.getTiles().add(food);
            Engine.currentRoom.getListItems().add(food);
            Engine.gui.addImage(food);
        }
    }

    public boolean canMove(Position position){//para nao andar nas paredes
         boolean possivel=true;
         for(ImageTile tile: Engine.currentRoom.getTiles()) {
             if (tile.getPosition().equals(position) && !(tile instanceof Floor)) {
                 possivel = false;
                 break;
             }
         }
         return possivel;
    }



     public void move(){//mover o inimigo nos movimentos possiveis dados ao respetivo inimigo
        Position newPosition;//inicializar a novaposicao
        Random random=new Random();
        do {
            int n=random.nextInt(getMovimentosPossiveis().size());//decidir a nova posicao de forma random
            newPosition= getMovimentosPossiveis().get(n);//ir buscar a posicao random de indice n à lista de movimentos possiveis
        }while(!canMove(newPosition));//enquanto o proximo movimento nao for possivel pois bate numa parede, procurar outro possivel
        position=newPosition;

     }

     public boolean isClose(Hero hero){
        double distancia;
        distancia=Math.sqrt((Math.pow(hero.getPosition().getX()-getPosition().getX(),2))+(Math.pow(hero.getPosition().getY()-getPosition().getY(),2)));
        return distancia < 4;
     }

     public void moveClose(Hero hero){
        Position p=position;
        if(hero.getPosition().getX()>getPosition().getX()){
            p=position.plus(new Vector2D(1,0));
        }if((hero.getPosition().getX()>getPosition().getX()) && (hero.getPosition().getY()> getPosition().getY())){
             p=position.plus(new Vector2D(1,0));
        }if((hero.getPosition().getX()==getPosition().getX()) && (hero.getPosition().getY()> getPosition().getY())){
             p=position.plus(new Vector2D(0,1));
        }
        if(hero.getPosition().getX()<getPosition().getX()){
            p=position.plus(new Vector2D(-1,0));
        }if(hero.getPosition().getY()>getPosition().getY()){
            p=position.plus(new Vector2D(0,1));
        }if(hero.getPosition().getY()<getPosition().getY()){
            p=position.plus(new Vector2D(0,-1));
        }
        if(canMove(p)){
            position=p;
        }
        if(nextTo(hero.getPosition())){
            fightHero(hero);
            hero.updateLifeBar();
        }
     }

     public boolean nextTo(Position position){
        double d;
        d=(Math.pow((position.getX()-getPosition().getX()),2))+(Math.pow((position.getY()-getPosition().getY()),2));
        return d==1.0;
     }



    public void fightHero(Hero hero){
        hero.setLife(hero.getLife()-getStrength());
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Enemy{" +
                "position=" + position +
                ", life=" + life +
                '}';
    }
}
