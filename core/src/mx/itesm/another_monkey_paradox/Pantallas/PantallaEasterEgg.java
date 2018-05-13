package mx.itesm.another_monkey_paradox.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
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

import jdk.nashorn.internal.runtime.SharedPropertyMap;
import mx.itesm.another_monkey_paradox.Main;
import mx.itesm.another_monkey_paradox.PantallasDeCarga.PantallaSplash;

/**
 * Created by Fernando on 08/05/18.
 */

public class PantallaEasterEgg extends PantallaImagenSencilla implements Screen {

    public PantallaEasterEgg(Main main) {
        super(main);

        musicPantalla = Gdx.audio.newMusic(Gdx.files.internal("EasterEgg/EasterEgg.mp3"));
    }

    public void crearElementos(){

        stage = new Stage(vista);

        imgBackground = new Texture("EasterEgg/EGG.png");
        background = new Sprite(imgBackground);
        background.setPosition(0,0);

        //Boton Return
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(new Texture("go-back.png")));
        ImageButton back = new ImageButton(trdBack);
        back.setPosition(30, ALTO-30-back.getHeight());
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                main.setScreen(new PantallaMenu(main));
            }
        });

        stage.addActor(back);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        crearElementos();
        musicPantalla.setLooping(true);
        Preferences prefs = Gdx.app.getPreferences("AnotherMonkeyPreferenceStory");
        if(prefs.getBoolean("music",true)) {
            musicPantalla.play();
        }
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        background.draw(batch);
        batch.end();
        stage.draw();

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            main.setScreen(new PantallaSplash(main));
        }

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        musicPantalla.stop();
    }
}
