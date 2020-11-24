package pt.upskills.projeto.objects;

import pt.upskills.projeto.game.Engine;
import pt.upskills.projeto.gui.FireTile;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

public class FireBall implements FireTile {
    private Position position;
    private int damage=70;

    public FireBall(Position position){
        this.position=position;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String getName() {
        return "Fire";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean validateImpact() {
        for(ImageTile tile: Engine.currentRoom.getTiles()){
            if(tile instanceof Wall && tile.getPosition().equals(position)){
                return false;
            }
            if(tile instanceof Enemy && tile.getPosition().equals(position)){
                Enemy enemy = (Enemy) tile;
                enemy.takeDamage(damage);
                enemy.isAlive();
                return false;
            }
        }
        return true;
    }

    @Override
    public void setPosition(Position position) {
        this.position=position;
    }
}
