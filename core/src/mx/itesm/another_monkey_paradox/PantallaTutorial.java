package mx.itesm.another_monkey_paradox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by adrian on 2/9/2018.
 */

class PantallaTutorial implements Screen {

    private final Main main;

    public static final float ANCHO = 1280;
    public static final float ALTO = 780;

    //Camara
    private OrthographicCamera camara;
    private Viewport vista;
    //Escena
    private Stage stageMenu;

    private SpriteBatch batch;

    //For Background
    Texture imgBackground;
    private Sprite spriteBackground;

    //background music
    private Music musicMenu = Gdx.audio.newMusic(Gdx.files.internal("prueba.mp3"));

    public PantallaTutorial(Main main) {
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

        imgBackground = new Texture("space.png");
        spriteBackground = new Sprite(imgBackground);
        spriteBackground.setPosition(0, 0);

        //Boton Return
        TextureRegionDrawable trdReturn = new TextureRegionDrawable(new TextureRegion(new Texture("but-ret.png")));
        TextureRegionDrawable trdReturnPush = new TextureRegionDrawable(new TextureRegion(new Texture("but-ret-push.png")));

        ImageButton btnReturn = new ImageButton(trdReturn, trdReturnPush);
        btnReturn.setPosition(ANCHO/4-btnReturn.getWidth()/2, ALTO/4-btnReturn.getHeight()/2);

        //Click en boton Return
        btnReturn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                main.setScreen(new PantallaMenu(main));
            }
        });

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
        Gdx.gl.glClearColor(.1f,.4f,.9f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        spriteBackground.draw(batch);
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