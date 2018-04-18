package mx.itesm.another_monkey_paradox;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by adrian on 2/9/2018.
 */

class PantallaDeveloper implements Screen {

    private final Main main;

    public static final float ANCHO = 1280;
    public static final float ALTO = 780;

    //Camara
    private OrthographicCamera camara;
    private Viewport vista;
    //Escena
    private Stage stageMenu;

    private float sensitivity = 0;
    private float difficulty = 0;

    private Texto titulo;

    private Preferences prefs;

    private SpriteBatch batch;

    Texture imgBackground;
    private Sprite spriteBackground;

    //background music
    private Music musicMenu = Gdx.audio.newMusic(Gdx.files.internal("loboloco.mp3"));

    public PantallaDeveloper(Main main) {
        this.main = main;
    }

    @Override
    public void show() {
        crearCamara();
        crearMenu();
        batch = new SpriteBatch();
        musicMenu.setLooping(true);
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

        titulo = new Texto(1,1,1);

        TextButton buttonDifficulty = new TextButton("Difficulty", skin);
        buttonDifficulty.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                System.out.println("click " + x + ", " + y);
            }
        });

        buttonDifficulty.setPosition(ANCHO/2-buttonDifficulty.getWidth(),ALTO/2+100);

        Slider sliderDif = new Slider(0, 100, 1, false, skin);
        sliderDif.addListener(stopTouchDown); // Stops touchDown events from propagating to the FlickScrollPane.
        sliderDif.setPosition(ANCHO/2+50,ALTO/2+100);


        TextButton buttonSensitivity = new TextButton("Sensitivity", skin);
        buttonSensitivity.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                System.out.println("click " + x + ", " + y);
            }
        });
        buttonSensitivity.setPosition(ANCHO/2-buttonDifficulty.getWidth(),ALTO/2);

        Slider sliderSens = new Slider(0, 100, 1, false, skin);
        sliderSens.addListener(stopTouchDown2); // Stops touchDown events from propagating to the FlickScrollPane.
        sliderSens.setPosition(ANCHO/2+50,ALTO/2);

        final TextButton creditsButton = new TextButton("Creditos", skin.get("default", TextButton.TextButtonStyle.class));
        creditsButton.setChecked(true);
        creditsButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                prefs.putFloat("Difficulty",difficulty);
                prefs.putFloat("Sensitivity",sensitivity);
                prefs.flush();
                main.setScreen(new PantallaCredits(main));
            }
        });
        creditsButton.setPosition(ANCHO-20-creditsButton.getWidth(),20);


        stageMenu.addActor(buttonDifficulty);
        stageMenu.addActor(creditsButton);
        stageMenu.addActor(buttonSensitivity);
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
        titulo.mostratMensaje(batch,"SETTINGS",ANCHO/4-100,ALTO-50,1,1,1);
        batch.end();
        stageMenu.draw();

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
