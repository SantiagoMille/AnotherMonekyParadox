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
 * Created by santi on 1/30/2018.
 */

class PantallaMenu implements Screen {

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


    public PantallaMenu(Main main) {
        this.main = main;
    }

    @Override
    public void show() {
        crearCamara();
        crearMenu();
        batch = new SpriteBatch();
        PantallaSplash.musicMenu.setLooping(false);
        PantallaSplash.musicMenu.play();
    }

    private void crearMenu() {
        stageMenu = new Stage(vista);

        imgBackground = new Texture("Background.png");
        spriteBackground = new Sprite(imgBackground);
        spriteBackground.setPosition(0, 0);

        //Boton Play
        TextureRegionDrawable trdPlay = new TextureRegionDrawable(new TextureRegion(new Texture("button_start.png")));
        TextureRegionDrawable trdPlayPressed = new TextureRegionDrawable(new TextureRegion(new Texture("button_start_pressed.png")));

        ImageButton btnPlay = new ImageButton(trdPlay, trdPlayPressed);
        btnPlay.setPosition(ANCHO/2-btnPlay.getWidth()/2, ALTO/4-btnPlay.getHeight()/2);

        //Boton Leaderboard
        TextureRegionDrawable trdLead = new TextureRegionDrawable(new TextureRegion(new Texture("but-lead.png")));
        TextureRegionDrawable trdLeadPush = new TextureRegionDrawable(new TextureRegion(new Texture("but-lead-push.png")));

        ImageButton btnLead = new ImageButton(trdLead, trdLeadPush);
        btnLead.setPosition(ANCHO/4-btnLead.getWidth()/2, ALTO/4-btnLead.getHeight()/2);

        //Boton Config
        TextureRegionDrawable trdConfig = new TextureRegionDrawable(new TextureRegion(new Texture("but-config.png")));
        TextureRegionDrawable trdConfigPush = new TextureRegionDrawable(new TextureRegion(new Texture("but-config-push.png")));

        ImageButton btnConfig = new ImageButton(trdConfig, trdConfigPush);
        btnConfig.setPosition(ANCHO*3/4-btnConfig.getWidth()/2, ALTO/4-btnConfig.getHeight()/2);

        //Click en boton Play
        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                dispose();
                main.setScreen(new PantallaJuego(main));
            }
        });

        //Click en boton Lead
        btnLead.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                dispose();
                main.setScreen(new PantallaScores(main));
            }
        });

        //Click en boton Config
        btnConfig.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                dispose();
                main.setScreen(new PantallaDeveloper(main));
            }
        });

        stageMenu.addActor(btnPlay);
        stageMenu.addActor(btnLead);
        stageMenu.addActor(btnConfig);

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
        PantallaSplash.musicMenu.dispose();
    }
}
