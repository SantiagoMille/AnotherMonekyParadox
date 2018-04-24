package mx.itesm.another_monkey_paradox.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import mx.itesm.another_monkey_paradox.Main;
import mx.itesm.another_monkey_paradox.PantallasDeCarga.PantallaSplash;
import mx.itesm.another_monkey_paradox.Utils.Texto;

/**
 * Created by Adrian on 03/04/2018.
 */

public class EscenaAstroGanador implements Screen {

    private final Main main;

    public static final float ANCHO = 1280;
    public static final float ALTO = 780;

    //Camara
    private OrthographicCamera camara;
    private Viewport vista;
    //Escena
    private SpriteBatch batch;

    //buttons
    private Texture imgBoton;
    private Texture imgBN;
    private ImageButton home;
    private ImageButton next;

    //Asset Manager
    private AssetManager assetManager;

    //Texto
    private Texto texto1;
    private Texto texto2;

    //Escena
    private Stage stageNivel;

    //Para el background
    private Texture imgBackground;
    private Sprite Background;

    private int score;
    private int nivel;

    public EscenaAstroGanador(Main main, int score, int nivel) {
        this.nivel = nivel;
        this.main = main;
        this.score = score;
        this.assetManager = main.getAssetManager();
    }

    @Override
    public void show() {
        crearCamara();
        crearBackground();
        crearBoton();
        batch = new SpriteBatch();
    }

    private void crearBackground(){
        assetManager.load("logros.png", Texture.class);
        assetManager.finishLoading();
        imgBackground = assetManager.get("logros.png");
        Background = new Sprite(imgBackground);
        Background.setPosition(0,0);
    }

    private void crearBoton() {
        //Boton Home
        assetManager.load("boton Home.png", Texture.class);
        assetManager.finishLoading();
        imgBoton = assetManager.get("boton Home.png");

        stageNivel = new Stage(vista);

        texto1 = new Texto(1,1,1);
        texto2 = new Texto(1,1,1);

        TextureRegionDrawable btnHome = new TextureRegionDrawable(new TextureRegion(imgBoton));
        home = new ImageButton(btnHome);
        home.setSize(155,155);
        home.setPosition(ANCHO*2/10, ALTO/2);
        home.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                main.setScreen(new PantallaSplash(main));
            }
        });

        //Boton Next
        assetManager.load("PlayButton.png", Texture.class);
        assetManager.finishLoading();
        imgBN = assetManager.get("PlayButton.png");

        TextureRegionDrawable btnNext = new TextureRegionDrawable(new TextureRegion(imgBN));
        next = new ImageButton(btnNext);
        next.setSize(155,155);
        next.setPosition(ANCHO*7/10, ALTO/2);
        next.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(nivel==2){
                    main.setScreen(new PantallaCinematicaL1(main));
                }
            }
        });

        stageNivel.addActor(home);
        stageNivel.addActor(next);

        Gdx.input.setInputProcessor(stageNivel);
    }

    private void crearCamara() {
        camara = new OrthographicCamera(ANCHO,ALTO);
        camara.position.set(ANCHO/2, ALTO/2,0);
        camara.update();
        vista = new StretchViewport(ANCHO,ALTO,camara);
    }

    @Override
    public void render(float delta) {

        Background.setPosition(0,0);

        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        Background.draw(batch);
        texto1.mostratMensaje(batch, "LEVEL 1 COMPLETED", ANCHO/2-250, ALTO*9/10,1,1, 1);
        texto2.mostratMensaje(batch, "SCORE: " + this.score, ANCHO/2-350, ALTO*8/10,1,1, 1);
        batch.end();
        stageNivel.draw();
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
        dispose();
    }

    @Override
    public void dispose() {
    }
}
