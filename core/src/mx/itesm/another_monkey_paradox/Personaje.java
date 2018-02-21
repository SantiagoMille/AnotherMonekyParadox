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
        animacion = new Animation(0.2f, texture1, texture2, texture3, texture4);
        x = Pantalla.ANCHO/2;
        y = Pantalla.ALTO*(1/16.0f);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void render(SpriteBatch batch){
        timerAnimacion += Gdx.graphics.getDeltaTime();
        TextureRegion frame = (TextureRegion) animacion.getKeyFrame(timerAnimacion);
        batch.draw(frame, x, y);
    }
}
