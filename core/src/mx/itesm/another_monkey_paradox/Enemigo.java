package mx.itesm.another_monkey_paradox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.Rectangle;

/**
 * Created by Adrian on 02/03/18.
 */
//Hola

class Enemigo {

    private Animation animacion;
    private float x, y; // Coordenadas
    private float timerAnimacion;
    private Estado estado;
    private float timerMuriendo;
    private int vida = 100;

    private static final float VX = -200;

    public Enemigo(Texture texture1, Texture texture2, Texture texture3, Texture texture4){
        TextureRegion img1 = new TextureRegion(texture1);
        TextureRegion img2 = new TextureRegion(texture2);
        TextureRegion img3 = new TextureRegion(texture3);
        TextureRegion img4 = new TextureRegion(texture4);
        animacion = new Animation(0.2f, img1, img2, img3, img4);
        x = PantallaJuego.ANCHO-texture1.getWidth()/2;
        y = PantallaJuego.ALTO/4;
        estado=Estado.VIVO;
        animacion.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void render(SpriteBatch batch){
        timerAnimacion += Gdx.graphics.getDeltaTime();
        TextureRegion frame = (TextureRegion) animacion.getKeyFrame(timerAnimacion);
        batch.draw(frame, x, y);
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
        if (estado==Estado.MURIENDO){
            timerMuriendo=1;
        }
    }

    public Estado getEstado(){
        return this.estado;
    }

    public int getWidth() {
        return ((TextureRegion) animacion.getKeyFrame(0)).getRegionWidth();
    }

    public int getHeight() {
        return ((TextureRegion) animacion.getKeyFrame(0)).getRegionHeight();
    }

    public float getY() {return y;}

    public float getX(){
        return x;
    }

    public void setX(float x){
        this.x = x;
    }

    public enum Estado{
        VIVO, MURIENDO, MUERTO;
    }

    public void setVida(int x){
        this.vida = x;
    }

    public int getVida(){
        return this.vida;
    }

}
