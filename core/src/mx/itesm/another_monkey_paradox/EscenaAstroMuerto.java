package mx.itesm.another_monkey_paradox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
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
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by santi on 1/30/2018.
 */

class EscenaAstroMuerto implements Screen {

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


    public EscenaAstroMuerto(Main main) {

        this.main = main;
        this.assetManager = main.getAssetManager();
    }

    @Override
    public void show() {
        crearCamara();
        crearBoton();
        batch = new SpriteBatch();
    }

    private void crearBoton() {
        imgBoton = assetManager.get("PlayButton.png");

        stageNivel = new Stage(vista);

        texto = new Texto(1,1,1);

        TextureRegionDrawable btnHome = new TextureRegionDrawable(new TextureRegion(imgBoton));
        home = new ImageButton(btnHome);
        home.setPosition(ANCHO/2-imgBoton.getWidth()/2, ALTO/4-imgBoton.getHeight()/2);
        home.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                main.setScreen(new PantallaMenu(main));
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

        Gdx.gl.glClearColor(66/255f,74/255f,96/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        stageNivel.draw();
        texto.mostratMensaje(batch, "You will get it next time", ANCHO/2, ALTO*0.75f,1,1, 1);
        batch.end();
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
