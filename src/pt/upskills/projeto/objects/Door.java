package pt.upskills.projeto.objects;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

public class Door implements ImageTile {
    private int portaEntrada;
    private int portaSaida;
    private Position position;
    private String tipo;
    private int nivel;
    boolean closed;
    boolean special;

    //este recebe a posicao
    public Door(int n_porta,int portaSaida, String tipo,  int nivel, boolean chave, boolean special, Position position){
        this.position=position;
        this.portaEntrada=n_porta;
        this.portaSaida=portaSaida;
        this.tipo=tipo;
        this.nivel=nivel;
        this.closed=chave;
        this.special=special;
    }
    public Door(int n_porta,int portaSaida, String tipo,  int nivel, boolean chave, boolean special ){
        this.portaEntrada=n_porta;
        this.portaSaida=portaSaida;
        this.tipo=tipo;
        this.nivel=nivel;
        this.closed=chave;
        this.special=special;
    }

    public int getPortaEntrada() {
        return portaEntrada;
    }

    public void setPortaEntrada(int n_porta) {
        this.portaEntrada = n_porta;
    }

    public int getPortaSaida() {
        return portaSaida;
    }

    public void setPortaSaida(int portaSaida) {
        this.portaSaida = portaSaida;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean chave) {
        this.closed = chave;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

    @Override
    public String getName() {
        if(tipo.equals("E")){
            return "DoorWay";
        }else if(tipo.equals("D") && closed){
            return "DoorClosed";
        }else{
            return "DoorOpen";
        }
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Door{" +
                "portaEntrada=" + portaEntrada +
                ", portaSaida=" + portaSaida +
                ", position=" + position +
                ", tipo='" + tipo + '\'' +
                ", nivel=" + nivel +
                ", closed=" + closed +
                ", special=" + special +
                '}';
    }
}
