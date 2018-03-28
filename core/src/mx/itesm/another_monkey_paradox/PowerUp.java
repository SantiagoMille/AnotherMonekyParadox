package mx.itesm.another_monkey_paradox;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by santi on 3/23/2018.
 */

public class PowerUp {

    private Sprite sprite;
    private float x,y;
    private boolean activa;


    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }


    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public PowerUp(Texture textura, float x, float y, boolean activa){
        sprite = new Sprite(textura);
        this.x = x;
        this.y = y;
        this.activa = activa;

        sprite.setSize(70,70);
        sprite.setPosition(x,y);
    }

    public void set(float x, float y){
        sprite.setPosition(x,y);
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

}
