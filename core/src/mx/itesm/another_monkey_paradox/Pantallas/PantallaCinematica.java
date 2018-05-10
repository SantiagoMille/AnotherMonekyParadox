package mx.itesm.another_monkey_paradox.Pantallas;

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

import mx.itesm.another_monkey_paradox.Main;
import mx.itesm.another_monkey_paradox.Niveles.PantallaJuego2;
import mx.itesm.another_monkey_paradox.Pantallas.Pantalla;
import mx.itesm.another_monkey_paradox.Pantallas.PantallaMenu;
import mx.itesm.another_monkey_paradox.PantallasDeCarga.PantallaCargandoStoryMode;
import mx.itesm.another_monkey_paradox.PantallasDeCarga.PantallaSplash;

/**
 * Created by adrian on 03/04/2018.
 */

public class PantallaCinematica extends Pantalla implements Screen {

    //Escena
    private Stage stageMenu;

    private SpriteBatch batch;

    //For Background
    Texture imgBackground;
    private Sprite spriteBackground;

    private String background;
    private int nivel;
    private int score, vidas, granadas;

    //background music
    private Music musicMenu = Gdx.audio.newMusic(Gdx.files.internal("loboloco.mp3"));

    public PantallaCinematica(Main main, String background, int nivel, int score, int vidas, int granadas) {
        super(main);
        this.background = background;
        this.nivel = nivel;
        this.granadas = granadas;
        this.vidas = vidas;
        this.score = score;
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

        imgBackground = new Texture(this.background);
        spriteBackground = new Sprite(imgBackground);

        //Boton Next
        TextureRegionDrawable trdNext = new TextureRegionDrawable(new TextureRegion(new Texture("HistoriaAstro/right-arrow_w.png")));
        ImageButton btnNext = new ImageButton(trdNext);


        btnNext.setPosition( ANCHO-100, ALTO-30-btnNext.getHeight());
        btnNext.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                if(nivel == 2) {
                    main.setScreen(new PantallaCargandoStoryMode(main,2, score, vidas, granadas));
                }else if(nivel == 3){
                    main.setScreen(new PantallaCargandoStoryMode(main,3, score, vidas, granadas));
                }else if(nivel == 4){
                    main.setScreen(new PantallaCargandoStoryMode(main,4, score, vidas, granadas));
                }else if(nivel == 44){
                    main.setScreen(new PantallaSplash(main));
                }
            }
        });


        stageMenu.addActor(btnNext);

        Gdx.input.setInputProcessor(stageMenu);
    }

    private void crearCamara(){
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
