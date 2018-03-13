package mx.itesm.another_monkey_paradox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fernando on 20/02/18.
 */

class Personaje {

    private Animation animacion;
    private float x, y; // Coordenadas
    private boolean isRight;

    public Personaje(Texture texture1, Texture texture2, Texture standing, Texture texture3, Texture texture4){
        TextureRegion img1 = new TextureRegion(texture1);
        TextureRegion img2 = new TextureRegion(texture2);
        TextureRegion imgStand = new TextureRegion(standing);
        TextureRegion img3 = new TextureRegion(texture3);
        TextureRegion img4 = new TextureRegion(texture4);
        animacion = new Animation(0.15f, img1, img2, imgStand, img3, img4);
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

    public void render(SpriteBatch batch, float timerAnimacion){
        TextureRegion frame = (TextureRegion) animacion.getKeyFrame(timerAnimacion);
        batch.draw(frame, x, y);
    }
}
