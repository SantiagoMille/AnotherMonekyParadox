package mx.itesm.another_monkey_paradox.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.*;

import mx.itesm.another_monkey_paradox.Main;
import mx.itesm.another_monkey_paradox.Utils.Texto;

/**
 * Created by adrian on 2/9/2018.
 */

public class PantallaCredits extends Pantalla implements Screen {

    //Escena
    private Stage stageMenu;

    private SpriteBatch batch;

    //For Background
    private Texture imgBackground, adrian, diego, fernando, brian, santi, logoTec;
    private Sprite spriteBackground, tec;
    private ImageButton adr, die, fer, bri, san;
    private boolean boolAdr, boolDie, boolFer, boolBri, boolSan;


    private Texto title, Adrian, Santi, Fer, Diego, Brian, Itesm;

    //Fotos
    private Image foto = new Image();

    public PantallaCredits(Main main) {
        super(main);
    }

    @Override
    public void show() {
        crearCamara();
        crearMenu();
        batch = new SpriteBatch();
        Gdx.input.setCatchBackKey(true);
        //musicMenu.play();
    }

    private void crearMenu() {
        stageMenu = new Stage(vista);

        boolAdr = false;
        boolFer = false;
        boolBri = false;
        boolDie = false;
        boolSan = false;

        title = new Texto(1,1,1);
        Adrian = new Texto(1,1,1);
        Santi = new Texto(1,1,1);
        Fer = new Texto(1,1,1);
        Brian = new Texto(1,1,1);
        Diego = new Texto(1,1,1);
        Itesm = new Texto(1, 1, 1);

        imgBackground = new Texture("space.png");

        adrian = new Texture("pp_a.PNG");
        santi = new Texture("pp_s.PNG");
        fernando = new Texture("pp.PNG");
        diego = new Texture("pp_d.PNG");
        brian = new Texture("pp_b.PNG");
        logoTec = new Texture("logoTec.png");

        spriteBackground = new Sprite(imgBackground);
        tec = new Sprite(logoTec);
        tec.setPosition(ANCHO*0.85f-40, 95);

        TextureRegionDrawable trdSan = new TextureRegionDrawable(new TextureRegion(santi));
        san = new ImageButton(trdSan);
        san.setPosition(0, ALTO-280+48);
        san.addAction(Actions.alpha(0));
        san.addAction(Actions.moveTo(800, ALTO-280+48, 0.8f));
        san.addAction(Actions.fadeIn(0.8f));

        san.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                boolSan = true;
            }
        });

        TextureRegionDrawable trdBri = new TextureRegionDrawable(new TextureRegion(brian));
        bri = new ImageButton(trdBri);
        bri.setPosition(0, ALTO-400+48);
        bri.addAction(Actions.alpha(0));
        bri.addAction(Actions.moveTo(800, ALTO-400+48, 0.8f));
        bri.addAction(Actions.fadeIn(0.8f));

        bri.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                boolBri = true;
            }
        });

        TextureRegionDrawable trdFer = new TextureRegionDrawable(new TextureRegion(fernando));
        fer = new ImageButton(trdFer);
        fer.setPosition(0, ALTO-520+48);
        fer.addAction(Actions.alpha(0));
        fer.addAction(Actions.moveTo(800, ALTO-520+48, 0.8f));
        fer.addAction(Actions.fadeIn(0.8f));

        fer.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                boolFer = true;
            }
        });

        TextureRegionDrawable trdAdr = new TextureRegionDrawable(new TextureRegion(adrian));
        adr = new ImageButton(trdAdr);
        adr.setPosition(0, ALTO-640+48);
        adr.addAction(Actions.alpha(0));
        adr.addAction(Actions.moveTo(800, ALTO-640+48, 0.8f));
        adr.addAction(Actions.fadeIn(0.8f));

        adr.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                boolAdr = true;
            }
        });
        TextureRegionDrawable trdDie = new TextureRegionDrawable(new TextureRegion(diego));
        die = new ImageButton(trdDie);
        die.setPosition(0, ALTO-760+48);
        die.addAction(Actions.alpha(0));
        die.addAction(Actions.moveTo(800, ALTO-760+48, 0.8f));
        die.addAction(Actions.fadeIn(0.8f));

        die.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                boolDie = true;
            }
        });

        //Boton Return
        TextureRegionDrawable trdReturn = new TextureRegionDrawable(new TextureRegion(new Texture("go-back.png")));
        ImageButton btnReturn = new ImageButton(trdReturn);
        btnReturn.setPosition(30, ALTO-30-btnReturn.getHeight());
        btnReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                main.setScreen(new PantallaDeveloper(main));
            }
        });

        //Fotos
        foto.setScaling(Scaling.fit);

        stageMenu.addActor(btnReturn);
        stageMenu.addActor(san);
        stageMenu.addActor(bri);
        stageMenu.addActor(fer);
        stageMenu.addActor(adr);
        stageMenu.addActor(die);

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
        borrarPantalla();
        Gdx.gl.glClearColor(.1f,.4f,.9f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        spriteBackground.draw(batch);
        tec.draw(batch);

        title.mostratMensaje(batch,"CREDITS",ANCHO/6,ALTO-60,215/255f,215/255f,215/255f);
        Santi.mostratMensaje(batch,"Luis Santiago Mille  -  ISC",200,ALTO-180,215/255f,215/255f,215/255f);
        Brian.mostratMensaje(batch,"Brian Saggiante  -  LAD",140,ALTO-300,215/255f,215/255f,215/255f);
        Fer.mostratMensaje(batch,"Luis Fernando Cedenio  -  ISC",265,ALTO-420,215/255f,215/255f,215/255f);
        Adrian.mostratMensaje(batch,"Adrian Mendez  -  ISC",90,ALTO-540,215/255f,215/255f,215/255f);
        Diego.mostratMensaje(batch,"Diego Cervantes  -  LAD",140,ALTO-660,215/255f,215/255f,215/255f);
        Itesm.textoPequeno();
        Itesm.mostratMensaje(batch, "Proyecto de desarollo de videojuegos \n Campus Estado de Mexico\n\n anothermonkeyparadox@gmail.com", 750, 90, 215/255f,215/255f,215/255f);
        batch.end();

        stageMenu.act(Gdx.graphics.getDeltaTime());
        stageMenu.draw();

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            main.setScreen(new PantallaDeveloper(main));
        }

        if(boolSan && boolDie && boolBri && boolFer && boolAdr){
            main.setScreen(new PantallaEasterEgg(main));
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

    }

    @Override
    public void dispose() {
    }

}
