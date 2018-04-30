package mx.itesm.another_monkey_paradox.Objetos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by santi on 3/6/2018.
 */

public class Bala {

    private static final float VX = -200;
    private Sprite sprite;
    private Estado estado;
    private float timerMuriendo;

    public boolean fliped;

    public static float getVX() {
        return VX;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
        if (estado==Estado.MURIENDO){
            timerMuriendo=1;
        }
    }

    public Bala(Texture textura, boolean fliped){
        sprite = new Sprite(textura);
        this.fliped = fliped;
        estado=Estado.VIVO;
    }

    public void set(float x, float y){
        sprite.setPosition(x,y);
    }

    public void mover(float delta){
        sprite.setX(sprite.getX()+delta*VX);

        if(estado==Estado.MURIENDO){
            //sprite.rotate(10);
            sprite.setScale(sprite.getScaleX()*0.8f);
            timerMuriendo -= delta;
            if(timerMuriendo<=0){
                estado=Estado.MUERTO;
            }
        }
    }


    public void moverY(float delta){
        sprite.setY(sprite.getY()+delta*VX);
    }

    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }

    public float getX() {
        return sprite.getX();
    }

    public float getWidth(){
        return sprite.getWidth();
    }


    public void setX(float x) {
        sprite.setX(x);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public enum Estado{
        VIVO, MURIENDO, MUERTO;
    }
}
