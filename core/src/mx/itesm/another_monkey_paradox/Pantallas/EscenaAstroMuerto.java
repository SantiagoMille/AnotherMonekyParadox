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

import mx.itesm.another_monkey_paradox.Main;
import mx.itesm.another_monkey_paradox.PantallasDeCarga.PantallaSplash;
import mx.itesm.another_monkey_paradox.Utils.Texto;

/**
 * Created by santi on 1/30/2018.
 */

public class EscenaAstroMuerto implements Screen {

    private final Main main;

    public static final float ANCHO = 1280;
    public static final float ALTO = 780;

    //Camara
    private OrthographicCamera camara;
    private Viewport vista;
    //Escena
    private SpriteBatch batch;

    //button
    private Texture imgBoton;
    private ImageButton home;

    //Asset Manager
    private AssetManager assetManager;

    //Texto
    private Texto texto;

    //Escena
    private Stage stageNivel;

    //Para el bacgraun
    private Texture imgBacgraun;
    private Sprite Bacgraun;



    public EscenaAstroMuerto(Main main) {

        this.main = main;
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
        assetManager.load("pantallaMuerto.png", Texture.class);
        assetManager.finishLoading();
        imgBacgraun = assetManager.get("pantallaMuerto.png");
        Bacgraun = new Sprite(imgBacgraun);
        Bacgraun.setPosition(0,0);
    }

    private void crearBoton() {
        assetManager.load("regresar.png", Texture.class);
        assetManager.finishLoading();
        imgBoton = assetManager.get("regresar.png");


        stageNivel = new Stage(vista);

        texto = new Texto(1,1,1);

        TextureRegionDrawable btnHome = new TextureRegionDrawable(new TextureRegion(imgBoton));
        home = new ImageButton(btnHome);
        home.setPosition(ANCHO/2-imgBoton.getWidth()/2, ALTO/5-imgBoton.getHeight()/2);
        home.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                main.setScreen(new PantallaSplash(main));
            }
        });

        stageNivel.addActor(home);

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

        Bacgraun.setPosition(0,0);

        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        Bacgraun.draw(batch);
        texto.mostratMensaje(batch, "Lol u ded", ANCHO/2, ALTO/2,1,1, 1);
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