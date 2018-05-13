package mx.itesm.another_monkey_paradox.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.itesm.another_monkey_paradox.Main;
import mx.itesm.another_monkey_paradox.PantallasDeCarga.PantallaCargandoStoryMode;
import mx.itesm.another_monkey_paradox.PantallasDeCarga.PantallaSplash;

/**
 * Created by santi on 5/9/2018.
 */

public class PantallaHistoriaAstro extends PantallaImagenSencilla {

    float xnext, ynext;

    int count = 0;

    private ImageButton next, nextW, skip;

    public PantallaHistoriaAstro(Main main) {
        super(main);
        musicPantalla = Gdx.audio.newMusic(Gdx.files.internal("brian_music.mp3"));
    }

    public void crearElementos(){

        stage = new Stage(vista);

        imgBackground = new Texture("HistoriaAstro/C 1.png");
        background = new Sprite(imgBackground);
        background.setPosition(0,0);

        //Boton Return
        TextureRegionDrawable trdNext = new TextureRegionDrawable(new TextureRegion(new Texture("HistoriaAstro/right-arrow.png")));
        next = new ImageButton(trdNext);

        TextureRegionDrawable trdNextW = new TextureRegionDrawable(new TextureRegion(new Texture("HistoriaAstro/right-arrow_w.png")));
        nextW = new ImageButton(trdNextW);

        TextureRegionDrawable trdSkip = new TextureRegionDrawable(new TextureRegion(new Texture("HistoriaAstro/skip.png")));
        skip = new ImageButton(trdSkip);

        xnext = ANCHO-100;
        ynext = ALTO-30-next.getHeight();

        nextW.setPosition(xnext, ynext);
        next.setPosition(xnext, ynext+500);

        skip.setPosition(xnext-12, 100);
        skip.setSize(80,80);

        nextW.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                count++;
                if(count==1){
                    imgBackground = new Texture("HistoriaAstro/C 2.png");
                    background = new Sprite(imgBackground);
                    background.setPosition(0,0);
                    skip.setPosition(-100,-100);
                } else if(count == 2){
                    imgBackground = new Texture("HistoriaAstro/C 3.png");
                    background = new Sprite(imgBackground);
                    background.setPosition(0,0);
                    next.setPosition(xnext, ynext);
                    nextW.setPosition(xnext, ynext+500);
                }
            }
        });

        skip.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                main.setScreen(new PantallaCargandoStoryMode(main, 1, 0,3,5));
            }
        });

        next.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                main.setScreen(new PantallaCargandoStoryMode(main, 1, 0,3,5));
            }
        });

        stage.addActor(next);
        stage.addActor(nextW);
        stage.addActor(skip);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        crearElementos();
        musicPantalla.setLooping(true);
        Preferences prefs = Gdx.app.getPreferences("AnotherMonkeyPreferenceStory");
        if(prefs.getBoolean("music", true)) {
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
