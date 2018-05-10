package mx.itesm.another_monkey_paradox.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.itesm.another_monkey_paradox.Main;
import mx.itesm.another_monkey_paradox.PantallasDeCarga.PantallaSplash;

/**
 * Created by santi on 5/9/2018.
 */

public abstract class PantallaImagenSencilla extends Pantalla implements Screen {

    public Texture imgBackground;
    public Sprite background;
    public Stage stage;
    public Music musicPantalla;

    public PantallaImagenSencilla(Main main){
        super(main);
    }

    abstract public void crearElementos();

    @Override
    abstract public void show();

    @Override
    abstract public void render(float delta);

    @Override
    abstract public void dispose();
}
