package pt.upskills.projeto.objects;

import pt.upskills.projeto.game.Engine;
import pt.upskills.projeto.game.FireBallThread;
import pt.upskills.projeto.gui.ImageMatrixGUI;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Direction;
import pt.upskills.projeto.rogue.utils.Position;
import pt.upskills.projeto.rogue.utils.Vector2D;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Hero implements ImageTile, Observer {

    private Position position;
    private int currentLife;
    private int totalLife=250;
    private int strength=11;
    private int pontos=0;
    private Item[] inventario= new Item[3];
    private Item[] fireBalls= new Item[3];
    private Direction lasDirection=Direction.RIGHT;
    private List<ImageTile> lifeBar= new ArrayList<>(4);
    private List<ImageTile> showInventario= new ArrayList<>(3);
    private List<ImageTile> showFireBalls= new ArrayList<>(3);


    public Hero(Position position) {
        this.position = position;
        currentLife=totalLife;
        updateLifeBar();
    }


    @Override
    public String getName() {
        return "Hero";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getTotalLife() {
        return totalLife;
    }

    public int getLife() {
        return currentLife;
    }


    public void setLife(int life) {
        this.currentLife = life;
    }
    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength){
        this.strength=strength;
    }

    public Item[] getInventario() {
        return inventario;
    }

    public Item[] getFireBalls() {
        return fireBalls;
    }
    public int getNumFireBalls(){
        int n=0;
        for(Item item: fireBalls){
            if(item!=null){
                n++;
            }
        }
        return n;
    }
    public int getNumItem(){
        int n=0;
        for(Item item: inventario){
            if(item!=null){
                n++;
            }
        }
        return n;
    }

    public int getPontos() {
        return pontos;
    }

    public void setFireBalls(Item[] fireBalls) {
        this.fireBalls = fireBalls;
    }

    public void setInventario(Item[] inventario) {
        this.inventario = inventario;
    }

    public boolean isAlive(){
        return currentLife > 0;
    }

    public List<ImageTile> getLifeBar() {
        return lifeBar;
    }

    public void updateLifeBar(){
        lifeBar.clear();
        double healthparts= (double)totalLife/8;
        int n= (int)Math.ceil(currentLife/healthparts);
        if(n%2==0){
            for(int i=3; i<=(n/2)+2; i++){
                Green green= new Green(new Position(i,0));
                lifeBar.add(green);
            }
            for(int i=(n/2)+3; i<7; i++){
                Red red= new Red(new Position(i,0));
                lifeBar.add(red);
            }
        }else{
            int p=((int)Math.ceil(((n*4)/8.0)))+2;
            RedGreen redGreen= new RedGreen(new Position(p,0));
            lifeBar.add(redGreen);
            for(int i=3; i<=p-1; i++){
                Green green= new Green(new Position(i,0));
                lifeBar.add(green);
            }
            for(int i=p+1; i<7; i++){
                Red red= new Red(new Position(i,0));
                lifeBar.add(red);
            }


        }
        Engine.gui.newStatusImages(lifeBar);
    }
    public void updateStrength(){
        for(Item item: inventario){
            if(item!=null && item.getName().equals("Sword")){
                setStrength(11+6);
            }
            if(item!=null && item.getName().equals("Hammer")){
                setStrength(11+11);
                break;
            }
        }
    }

    public void updateShowInventario(){
        for(ImageTile inv : showInventario) {
            Engine.gui.removeStatusImage(inv);
        }
        showInventario.clear();
        int i=0;
        for(Item item: inventario){
            if(item!=null){
                item.setPosition(new Position(7+i,0));
                showInventario.add(item);
            }
            i++;
        }
        Engine.gui.newStatusImages(showInventario);
    }

    public void updateFireBalls(){
        for(ImageTile inv : showFireBalls) {
            Engine.gui.removeStatusImage(inv);
        }
        showFireBalls.clear();
        int i=0;
        for(Item item: fireBalls){
            if(item!=null){
                item.setPosition(new Position(0+i,0));
                showFireBalls.add(item);
            }
            i++;
        }
        Engine.gui.newStatusImages(showFireBalls);
    }



    public void updateStatus(){
        updateLifeBar();
        updateShowInventario();
        updateStrength();
        updateFireBalls();

    }
    public void updatePoints(int pontos){
        this.pontos=this.pontos+pontos;
        if(this.pontos<0){
            this.pontos=0;
        }
    }



    public void eatFood(Item item){
        if(currentLife==totalLife){
            return;
        }
        setLife(Math.min(currentLife + 50, totalLife));
        Engine.currentRoom.getTiles().remove(item);
        Engine.currentRoom.getListItems().remove(item);
        Engine.gui.removeImage(item);
    }

    public boolean hasItem(String item){
        for(Item item1: inventario){
            if(item1!=null && item1.getName().equals(item)){
                return true;
            }
        }
        return false;
    }

    public boolean hasFireBall(){
        for(int i=0; i<3;i++) {
            if (fireBalls[i] != null) {
                return true;
            }
        }
        return false;
    }

    public void removeFireBall(){
        for(int i=fireBalls.length-1; i>=0; i--){
            if(fireBalls[i]!=null){
                fireBalls[i]=null;
                break;
            }
        }
    }

    public void pickFireBall(Item item){
        for(int i=0; i<3;i++) {
            if (fireBalls[i] == null) {
                fireBalls[i] = item;
                Engine.currentRoom.getTiles().remove(item);
                Engine.currentRoom.getListItems().remove(item);
                Engine.gui.removeImage(item);
                break;
            }
        }

    }

    public void pickItem(Item item){
        for(int i=0; i<3;i++) {
            if (inventario[i] == null) {
                inventario[i] = item;
                updatePoints(item.getPoints());
                Engine.currentRoom.getTiles().remove(item);
                Engine.currentRoom.getListItems().remove(item);
                Engine.gui.removeImage(item);
                break;
            }
        }

    }
    public void dropIem(Item itemToDrop, int n){
        inventario[n]=null;
        itemToDrop.setPosition(position);
        Engine.currentRoom.getTiles().add(itemToDrop);
        Engine.currentRoom.getListItems().add(itemToDrop);
        Engine.gui.addImage(itemToDrop);
    }

    public void fightEnemy(Enemy enemy){
        enemy.setLife(enemy.getLife()-getStrength());
        if(enemy.isAlive()){
            setLife(currentLife-enemy.getStrength());
        }
    }

    public boolean isDoor(Position position){
        for(Door door:Engine.currentRoom.getListDoors()) {
            if (door.getPosition().equals(position)) {
                return true;
            }
        }return false;
    }

    public Door whichDoor(Position position){
        for(Door door:Engine.currentRoom.getListDoors()) {
            if (door.getPosition().equals(position)) {
                return door;
            }
        }
        return null;
    }

    public void enterDoor(Door door){
        Engine.changeRoom(this,door.getNivel());
        for(Door nextDoor:Engine.currentRoom.getListDoors()){
            if(nextDoor.getPortaEntrada()== door.getPortaSaida()){
                Engine.currentRoom.getTiles().add(this);
                position=nextDoor.getPosition();
                break;
            }
        }
    }



    /**
     * This method is called whenever the observed object is changed. This function is called when an
     * interaction with the graphic component occurs {{@link ImageMatrixGUI}}
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        Integer keyCode = (Integer) arg;
        Position novaPosicao = position.plus(new Vector2D(0, 0));//inicializar a variavel nova posicao e depois atribuir como posicao do hero, caso seja possivel
        boolean possivel = true;//se o proximo passo é possivel
        boolean wants2Drop = false;//caso queira fazer drop de um item
        int itemN = 0;

        if (keyCode == KeyEvent.VK_DOWN) {
            novaPosicao = position.plus(Direction.DOWN.asVector());
            lasDirection = Direction.DOWN;
        }
        if (keyCode == KeyEvent.VK_UP) {
            novaPosicao = position.plus(Direction.UP.asVector());
            lasDirection = Direction.UP;
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            novaPosicao = position.plus(Direction.LEFT.asVector());
            lasDirection = Direction.LEFT;
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            novaPosicao = position.plus(Direction.RIGHT.asVector());
            lasDirection = Direction.RIGHT;
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            possivel=false;
            if(hasFireBall()){//se tivermos uma bola de fogo
                FireBall fireBall = new FireBall(position);
                FireBallThread fireBallThread = new FireBallThread(lasDirection, fireBall);
                fireBallThread.start();
                ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
                gui.addImage(fireBall);
                removeFireBall();
            }

        }
        if(keyCode==KeyEvent.VK_S){
            possivel=false;
            GameStatus.saveGame();
        }
        if(keyCode==KeyEvent.VK_L){
            possivel=false;
            GameStatus.loadGame();
        }
        if(keyCode==KeyEvent.VK_R){//caso queiramos voltar ao inicio
            possivel=false;
            Engine.restarGame();
        }

        for (ImageTile tile : Engine.currentRoom.getTiles()) { //procurar na tela a posicao onde seria nova posicao do hero
            if ((tile instanceof Wall || tile instanceof Statue) && tile.getPosition().equals(novaPosicao)) { //caso seja uma parede, nao é possivel
                possivel = false;
                break;
            }
            if (tile instanceof Enemy && tile.getPosition().equals(novaPosicao)){ //se for um inimigo tbm n é possivel e estes lutam
                possivel = false;
                Enemy enemy = (Enemy) tile;
                fightEnemy(enemy);
                updateLifeBar();
                if (!enemy.isAlive()) {
                    if(enemy.getName().equals("Skeleton")){
                        enemy.dropItem();
                    }
                    break;
                }
            }
        }
        if(isDoor(novaPosicao) && whichDoor(novaPosicao).isClosed()){
            if(whichDoor(novaPosicao).isSpecial()){//caso seja a porta especial, é necessario ter a chave do BOSS
                if(hasItem("SkullKey")){
                    for(int i=0; i<3; i++){
                        if(inventario[i]!=null && inventario[i].getName().equals("SkullKey")){
                            Engine.status.remove(inventario[i]);
                            Engine.gui.removeStatusImage(inventario[i]);
                            inventario[i]=null;
                            whichDoor(novaPosicao).setClosed(false);
                            break;
                        }
                    }
                }
                possivel=false;
            }
            if(!whichDoor(novaPosicao).isSpecial()){//caso nao seja a porta especial
                if(hasItem("Key")){//se tiver a chave
                    for(int i=0; i<3; i++){
                        if(inventario[i]!=null && inventario[i].getName().equals("Key")){
                            Engine.status.remove(inventario[i]);
                            Engine.gui.removeStatusImage(inventario[i]);
                            inventario[i]=null;
                            whichDoor(novaPosicao).setClosed(false);
                            break;
                        }
                    }
                }
                possivel=false;
            }
        }

        if(possivel){
            position=novaPosicao;
            for(Door door:Engine.currentRoom.getListDoors()){
                if(door.getPosition().equals(novaPosicao)){
                    enterDoor(door);
                    break;
                }
            }


            for(Enemy enemy: Engine.currentRoom.getListEnemys()) {
                if (enemy.isClose(Engine.hero)) {
                    if(enemy.isAlive()){
                        enemy.moveClose(this);
                    }
                } else {
                    enemy.move();
                }

            }

            for(Item item:Engine.currentRoom.getListItems()){
                if(item.getPosition().equals(position)) {
                    if(item instanceof FireBall_Item){
                        pickFireBall(item);
                        break;
                    }
                    if (!(item instanceof Food)) {
                        pickItem(item);
                    } else {
                        eatFood(item);
                    }
                    break;
                }
            }
        }
        if (keyCode == KeyEvent.VK_1){
            wants2Drop=true;
            itemN=0;
        }
        if (keyCode == KeyEvent.VK_2){
            wants2Drop=true;
            itemN=1;
        }
        if (keyCode == KeyEvent.VK_3){
            wants2Drop=true;
            itemN=2;
        }
        if(wants2Drop){
            if(inventario[itemN]!=null){
                Item itemToDrop=inventario[itemN];
                dropIem(itemToDrop, itemN);
            }else{
                System.out.println("No Item in slot selected!");
            }
        }
        updateStatus();
        if(Engine.currentRoom.getLevel()==7){
            try {
                File pontuacao = new File("Pontuacoes.txt");
                PrintWriter printWriter= new PrintWriter(pontuacao);
                String linha="Tiago:"+getPontos();
                printWriter.println(linha);
                printWriter.close();

            }catch (IOException e){
                e.printStackTrace();
            }
        }
        if(!isAlive()){
            System.out.println("You died. Try Again!");
            Engine.restarGame();
        }

    }
}
