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
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by santi on 1/30/2018.
 */

class PantallaSplash implements Screen {

    private final Main main;

    public static final float ANCHO = 1280;
    public static final float ALTO = 780;

    //Camara
    private OrthographicCamera camara;
    private Viewport vista;
    //Escena
    private SpriteBatch batch;

    //To draw sprite of monkey
    Texture imgMonkey;
    private Sprite sprite;

    //Asset Manager
    private AssetManager assetManager;


    public static Music musicMenu = Gdx.audio.newMusic(Gdx.files.internal("loboloco.mp3"));

    public PantallaSplash(Main main) {

        this.main = main;
        this.assetManager = main.getAssetManager();
    }

    @Override
    public void show() {
        crearCamara();
        batch = new SpriteBatch();
        crearSpriteMonkey();
        cargarRecursos();
    }

    private void crearSpriteMonkey() {
        assetManager.load("Monkey.png", Texture.class);
        assetManager.finishLoading();
        imgMonkey = assetManager.get("Monkey.png");
        sprite = new Sprite(imgMonkey);
        sprite.setPosition(ANCHO/2-sprite.getWidth()/2, ALTO/2-sprite.getHeight()/2);
    }

    private void crearCamara() {
        camara = new OrthographicCamera(ANCHO,ALTO);
        camara.position.set(ANCHO/2, ALTO/2,0);
        camara.update();
        vista = new StretchViewport(ANCHO,ALTO,camara);
    }

    @Override
    public void render(float delta) {

        actualizarCarga();

        //Usar v=d/t o en este caso d=v*t
        Gdx.gl.glClearColor(66/255f,74/255f,96/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        sprite.draw(batch);
        batch.end();

        sprite.rotate(5);

        /*
        float delay = 1; // seconds
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                musicMenu.play();
                main.setScreen(new PantallaMenu(main));
            }
        }, delay);
        */

    }

    private void cargarRecursos(){
        assetManager.load("LOGO-2.png", Texture.class);
        assetManager.load("PlayButton.png", Texture.class);
        assetManager.load("PlayButton_Pressed.png", Texture.class);
        assetManager.load("trophy.png", Texture.class);
        assetManager.load("trophy_Pressed.png", Texture.class);
        assetManager.load("about-button.png", Texture.class);
        assetManager.load("About-button_Pressed.png", Texture.class);
        assetManager.load("About2.png", Texture.class);
        assetManager.load("StoryModeBack.png", Texture.class);
        assetManager.load("SurvivalModeBack.png", Texture.class);
    }

    //Revisa cómo va la carga de assets
    private void actualizarCarga(){
        if (assetManager.update()){ //regresa true si ya terminó la carga
            main.setScreen(new PantallaMenu(main));
        } else {
            //Aún no termina, preguntar cómo va
            float avance = assetManager.getProgress();
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
        dispose();
    }

    @Override
    public void dispose() {
        imgMonkey.dispose();
    }
}
