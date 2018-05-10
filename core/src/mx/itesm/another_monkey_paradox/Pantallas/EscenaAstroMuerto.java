package mx.itesm.another_monkey_paradox.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.StretchViewport;

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

    private boolean isThirdLevel;

    //Score
    private int finalScore;

    public EscenaAstroMuerto(Main main, int score) {

        super(main);
        this.finalScore = score;
        this.isThirdLevel = false;
    }

    public EscenaAstroMuerto(Main main, int score, boolean isThirdLevel) {

        super(main);
        this.isThirdLevel = isThirdLevel;
        this.finalScore = score;
    }

    @Override
    public void show() {
        crearBackground();
        crearBoton();
        batch = new SpriteBatch();
    }


    private void crearBackground(){
        if(!isThirdLevel) {
            assetManager.load("pantallaMuerto.png", Texture.class);
            assetManager.finishLoading();
            imgBacgraun = assetManager.get("pantallaMuerto.png");
        }else{
            assetManager.load("Cinematicas/C N3F.png", Texture.class);
            assetManager.finishLoading();
            imgBacgraun = assetManager.get("Cinematicas/C N3F.png");
        }
        Bacgraun = new Sprite(imgBacgraun);
        Bacgraun.setPosition(0,0);
    }

    private void crearBoton() {
        assetManager.load("regresar.png", Texture.class);
        assetManager.load("HistoriaAstro/right-arrow_w.png", Texture.class);
        assetManager.finishLoading();
        imgBoton = assetManager.get("regresar.png");
        imgBotonHome = assetManager.get("HistoriaAstro/right-arrow_w.png");

        stageNivel = new Stage(vista);

        texto = new Texto(1,1,1);

        TextureRegionDrawable btnHome = new TextureRegionDrawable(new TextureRegion(imgBoton));
        home = new ImageButton(btnHome);
        if(!isThirdLevel){
            home.setPosition(ANCHO/2-imgBoton.getWidth()/2, -100);
            home.addAction(Actions.moveTo(ANCHO/2-imgBoton.getWidth()/2, ALTO/5-imgBoton.getHeight()/2, 0.6f));
        }else{
            home.setPosition(-500, -100);
        }
        home.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                main.setScreen(new PantallaSplash(main));
            }
        });


        TextureRegionDrawable btnRregresar = new TextureRegionDrawable((new TextureRegion(imgBotonHome)));
        regresar = new ImageButton(btnRregresar);
        if(isThirdLevel) {
            regresar.setPosition(ANCHO - 103, ALTO - 25 - regresar.getHeight());
        }else{
            regresar.setPosition(-500, ALTO - 30 - regresar.getHeight());
        }
        regresar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                main.setScreen(new PantallaCargandoStoryMode(main, 1, 0, 3, 5));
            }
        });


        stageNivel.addActor(home);
        stageNivel.addActor(regresar);


        Gdx.input.setInputProcessor(stageNivel);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        Bacgraun.draw(batch);
        texto.mostratMensaje(batch, "Lol u ded", 700, 420,1,1, 1);
        if(!isThirdLevel) {
            texto.mostratMensaje(batch, "Score: " + finalScore, -200, 420, 1, 1, 1);
        }
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
