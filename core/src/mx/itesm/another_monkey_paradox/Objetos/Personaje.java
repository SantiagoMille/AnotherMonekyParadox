package mx.itesm.another_monkey_paradox.Objetos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import mx.itesm.another_monkey_paradox.Niveles.PantallaJuego;

/**
 * Created by Fernando on 20/02/18.
 */

public class Personaje {

    private Animation animacion;
    private float x, y; // Coordenadas
    private boolean isRight;
    Texture standingTexture ;
    Sprite standing;

    public Personaje(Texture texture1, Texture texture2, Texture texture3, Texture texture4){
        TextureRegion img1 = new TextureRegion(texture1);
        TextureRegion img2 = new TextureRegion(texture2);
        TextureRegion img3 = new TextureRegion(texture3);
        TextureRegion img4 = new TextureRegion(texture4);
        animacion = new Animation(0.1f, img1, img2, img3, img4);
        standingTexture = new Texture("Astro/astro.png");
        standing = new Sprite(standingTexture);
        x = PantallaJuego.ANCHO/2-texture1.getWidth()/2;
        y = PantallaJuego.ALTO/4;
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        isRight=true;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public Animation getAnimacion() {
        return animacion;
    }

    public void setAnimacion(Animation animacion) {
        this.animacion = animacion;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth(){return standing.getWidth();}

    public float getHeight(){return standing.getHeight();}

    public void render(SpriteBatch batch, float timerAnimacion, boolean isMovingRight, boolean isMovingLeft){
        if(isMovingLeft||isMovingRight){
            TextureRegion frame = (TextureRegion) animacion.getKeyFrame(timerAnimacion);
            batch.draw(frame, x, y);
        }else {
            standing.setPosition(x, y);
            if(isRight&&standing.isFlipX()) {
                standing.flip(true,false);
            } else if(!isRight&&!standing.isFlipX()) {
                standing.flip(true,false);
            }
            standing.draw(batch);
        }
    }
}
