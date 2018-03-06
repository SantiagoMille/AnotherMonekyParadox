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
    private float timerAnimacion;

    public Personaje(Texture texture1, Texture texture2, Texture texture3, Texture texture4){
        TextureRegion img1 = new TextureRegion(texture1);
        TextureRegion img2 = new TextureRegion(texture2);
        TextureRegion img3 = new TextureRegion(texture3);
        TextureRegion img4 = new TextureRegion(texture4);
        animacion = new Animation(0.15f, img1, img2, img3, img4);
        x = PantallaJuego.ANCHO/2-texture1.getWidth()/2;
        y = PantallaJuego.ALTO/2-texture1.getHeight()/2;
        animacion.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void render(SpriteBatch batch){
        timerAnimacion += Gdx.graphics.getDeltaTime();
        TextureRegion frame = (TextureRegion) animacion.getKeyFrame(timerAnimacion);
        batch.draw(frame, x, y);
    }
}