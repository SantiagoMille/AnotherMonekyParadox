package mx.itesm.another_monkey_paradox.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import mx.itesm.another_monkey_paradox.Main;
import mx.itesm.another_monkey_paradox.PantallasDeCarga.PantallaCargandoStoryMode;
import mx.itesm.another_monkey_paradox.PantallasDeCarga.PantallaSplash;
import mx.itesm.another_monkey_paradox.Utils.Texto;

/**
 * Created by santi on 1/30/2018.
 */

public class EscenaAstroMuerto extends Pantalla implements Screen {


    //button home
    private Texture imgBoton;
    private ImageButton home;

    //button regresar
    private Texture imgBotonHome;
    private ImageButton regresar;

    //Texto
    private Texto texto;

    //Escena
    private Stage stageNivel;

    //Para el bacgraun
    private Texture imgBacgraun;
    private Sprite Bacgraun;

    //Score
    private int finalScore;

    public EscenaAstroMuerto(Main main, int score) {

        super(main);
        this.finalScore = score;
    }

    @Override
    public void show() {
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
        assetManager.load("PlayButton.png", Texture.class);
        assetManager.finishLoading();
        imgBoton = assetManager.get("regresar.png");
        imgBotonHome = assetManager.get("PlayButton.png");

        stageNivel = new Stage(vista);

        texto = new Texto(1,1,1);

        TextureRegionDrawable btnHome = new TextureRegionDrawable(new TextureRegion(imgBoton));
        home = new ImageButton(btnHome);
        home.setPosition(ANCHO/2-imgBoton.getWidth()/2, -100);
        home.addAction(Actions.moveTo(ANCHO/2-imgBoton.getWidth()/2, ALTO/5-imgBoton.getHeight()/2, 0.6f));
        home.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                main.setScreen(new PantallaSplash(main));
            }
        });


        TextureRegionDrawable btnRregresar = new TextureRegionDrawable((new TextureRegion(imgBotonHome)));
        regresar = new ImageButton(btnRregresar);
        regresar.setSize(128,128);
        regresar.setPosition(-200, ALTO/5-imgBotonHome.getHeight()/2);
        regresar.addAction(Actions.moveTo(ANCHO/2-imgBotonHome.getWidth()/2-100, ALTO/4-imgBotonHome.getHeight()/2,0.6f));
        regresar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                main.setScreen(new PantallaCargandoStoryMode(main, 1, 0));
            }
        });


        stageNivel.addActor(home);
        //stageNivel.addActor(regresar);


        Gdx.input.setInputProcessor(stageNivel);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        Bacgraun.draw(batch);
        texto.mostratMensaje(batch, "Good luck \nnext time", ANCHO*0.85f, ALTO*0.7f,1,1, 1);
        texto.mostratMensaje(batch, "Score: " + finalScore, -200, 420,1,1, 1);
        batch.end();
        stageNivel.draw();
        stageNivel.act();
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
