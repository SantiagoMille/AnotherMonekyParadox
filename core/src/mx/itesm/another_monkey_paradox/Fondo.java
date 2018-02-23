package mx.itesm.another_monkey_paradox;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Fernando on 20/02/18.
 */

class Fondo {

    private Sprite imagenA;
    private Sprite imagenB;

    public Fondo(Texture texture){
        imagenA = new Sprite(texture);
        imagenA.setPosition(0,0);
        imagenB = new Sprite(texture);
        imagenB.setPosition(imagenA.getWidth(),0);
    }

    public void mover(float dx){
        imagenA.setX(imagenA.getX() + dx);
        imagenB.setX(imagenB.getX() + dx);

        //Actualizar posiciones
        if (imagenA.getX() <= -imagenA.getWidth()){
            imagenA.setX(imagenB.getX() + imagenB.getWidth());
        }

        if (imagenB.getX() <= -imagenB.getWidth()){
            imagenB.setX(imagenA.getX() + imagenA.getWidth());
        }

    }

    public void render(SpriteBatch batch){
        imagenA.draw(batch);
        imagenB.draw(batch);

    }

}

