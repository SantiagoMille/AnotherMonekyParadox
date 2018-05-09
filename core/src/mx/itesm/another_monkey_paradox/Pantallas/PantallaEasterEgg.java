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
 * Created by Fernando on 08/05/18.
 */

public class PantallaEasterEgg extends Pantalla implements Screen {

    private Texture imgBackground;
    private Sprite background;
    private Stage stageEaster;
    private Music musicEaster = Gdx.audio.newMusic(Gdx.files.internal("EasterEgg/EasterEgg.mp3"));



    public PantallaEasterEgg(Main main) {
        super(main);
    }

    public void crearElementos(){

        stageEaster = new Stage(vista);

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

        stageEaster.addActor(back);
    }

    @Override
    public void show() {
        crearElementos();
        musicEaster.setLooping(true);
        musicEaster.play();
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        background.draw(batch);
        batch.end();
        stageEaster.draw();

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
        musicEaster.stop();
    }
}
