package mx.itesm.another_monkey_paradox.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import mx.itesm.another_monkey_paradox.Main;
import mx.itesm.another_monkey_paradox.Utils.Texto;

/**
 * Created by adrian on 2/9/2018.
 */

public class PantallaDeveloper extends Pantalla implements Screen {

    //Escena
    private Stage stageMenu;

    private float sensitivity = 0;
    private float difficulty = 0;

    private Texto titulo;

    private Preferences prefs;

    private SpriteBatch batch;

    Texture imgBackground;
    private Sprite spriteBackground, sensi, diffi;

    //background music
    private Music musicMenu = Gdx.audio.newMusic(Gdx.files.internal("loboloco.mp3"));

    TextureRegionDrawable trdMusic;
    TextureRegionDrawable trdMusicPressed;

    public PantallaDeveloper(Main main) {
        super(main);
    }

    @Override
    public void show() {
        crearCamara();
        crearMenu();
        batch = new SpriteBatch();
        musicMenu.setLooping(true);
        Gdx.input.setCatchBackKey(true);
        //musicMenu.play();
    }

    private void crearMenu() {
        stageMenu = new Stage(vista);

        prefs = Gdx.app.getPreferences("AnotherMonkeyPreferenceStory");

        Skin skin = new Skin(Gdx.files.internal("skin/comic-ui.json"));

        imgBackground = new Texture("pantalla_config.png");
        spriteBackground = new Sprite(imgBackground);
        spriteBackground.setAlpha(0.55f);

        //Boton Return
        TextureRegionDrawable trdReturn = new TextureRegionDrawable(new TextureRegion(new Texture("go-back.png")));
        ImageButton btnReturn = new ImageButton(trdReturn);
        btnReturn.setPosition(20, ALTO-30-btnReturn.getHeight());

        //Click en boton Return
        btnReturn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                prefs.putFloat("Difficulty",difficulty);
                prefs.putFloat("Sensitivity",sensitivity);
                prefs.flush();
                main.setScreen(new PantallaMenu(main));
            }
        });

        InputListener stopTouchDown = new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                event.stop();
                difficulty = x;
                return false;
            }
        };

        InputListener stopTouchDown2 = new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                event.stop();
                sensitivity = x;
                return false;
            }
        };


        //Boton Play
        trdMusic = new TextureRegionDrawable(new TextureRegion(new Texture("mute.png")));
        trdMusicPressed = new TextureRegionDrawable(new TextureRegion(new Texture("audio.png")));
        ImageButton btnPlay = new ImageButton(trdMusic);
        btnPlay.setPosition(ANCHO/2-btnPlay.getWidth()/2, -100);
        btnPlay.addAction(Actions.moveTo(ANCHO/2-btnPlay.getWidth()/2, ALTO/4-btnPlay.getHeight()/2, 0.5f));
        

        titulo = new Texto(1,1,1);

        Texture diff = new Texture("button_difficulty.png");
        diffi = new Sprite(diff);
        diffi.setPosition(ANCHO/2-150, ALTO/2+90);

        Slider sliderDif = new Slider(0, 100, 1, false, skin);
        sliderDif.addListener(stopTouchDown); // Stops touchDown events from propagating to the FlickScrollPane.
        sliderDif.setPosition(ANCHO/2+50,ALTO/2+100);

        Texture sens = new Texture("button_sensitivity.png");
        sensi = new Sprite(sens);
        sensi.setPosition(ANCHO/2-165, ALTO/2-10);

        Slider sliderSens = new Slider(0, 100, 1, false, skin);
        sliderSens.addListener(stopTouchDown2); // Stops touchDown events from propagating to the FlickScrollPane.
        sliderSens.setPosition(ANCHO/2+50,ALTO/2);


        TextureRegionDrawable creditsTRD = new TextureRegionDrawable(new TextureRegion(new Texture("button_credits.png")));
        ImageButton btnCreds = new ImageButton(creditsTRD);
        btnCreds.setPosition(ANCHO-20-btnCreds.getWidth(),20);

        btnCreds.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                prefs.putFloat("Difficulty",difficulty);
                prefs.putFloat("Sensitivity",sensitivity);
                prefs.flush();
                main.setScreen(new PantallaCredits(main));
            }
        });

        stageMenu.addActor(btnCreds);
        stageMenu.addActor(sliderDif);
        stageMenu.addActor(sliderSens);
        stageMenu.addActor(btnReturn);

        Gdx.input.setInputProcessor(stageMenu);

    }

    private void crearCamara() {
        camara = new OrthographicCamera(ANCHO,ALTO);
        camara.position.set(ANCHO/2, ALTO/2,0);
        camara.update();
        vista = new StretchViewport(ANCHO,ALTO,camara);
    }

    @Override
    public void render(float delta) {
        //Usar v=d/t o en este caso d=v*t
        Gdx.gl.glClearColor(80/255f,90/255f,95/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stageMenu.act(Gdx.graphics.getDeltaTime());
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        spriteBackground.draw(batch);
        sensi.draw(batch);
        diffi.draw(batch);
        titulo.mostratMensaje(batch,"SETTINGS",ANCHO/4-100,ALTO-50,1,1,1);
        batch.end();
        stageMenu.draw();
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            main.setScreen(new PantallaMenu(main));
        }

    }

    @Override
    public void resize(int width, int height) {
        vista.update(width,height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
