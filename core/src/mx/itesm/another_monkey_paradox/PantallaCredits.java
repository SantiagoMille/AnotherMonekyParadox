package mx.itesm.another_monkey_paradox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.*;

import java.nio.IntBuffer;

import javax.xml.soap.Text;

/**
 * Created by adrian on 2/9/2018.
 */

class PantallaCredits implements Screen {

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
    Texture imgBackground, adrian, diego, fernando, brian, santi;
    private Sprite spriteBackground, adr, die, fer, bri, san;

    //background music
    private Music musicMenu = Gdx.audio.newMusic(Gdx.files.internal("loboloco.mp3"));

    private Texto title, Adrian, Santi, Fer, Diego, Brian;

    //Fotos
    private Image foto = new Image();

    public PantallaCredits(Main main) {
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

        title = new Texto(1,1,1);
        Adrian = new Texto(1,1,1);
        Santi = new Texto(1,1,1);
        Fer = new Texto(1,1,1);
        Brian = new Texto(1,1,1);
        Diego = new Texto(1,1,1);

        imgBackground = new Texture("space2.png");
        adrian = new Texture("pp.jpg");
        santi = new Texture("pp.jpg");
        fernando = new Texture("pp.jpg");
        diego = new Texture("pp.jpg");
        brian = new Texture("pp.jpg");

        spriteBackground = new Sprite(imgBackground);
        //spriteBackground.setAlpha(0.7f);
        adr = new Sprite(adrian);
        bri = new Sprite(brian);
        die = new Sprite(diego);
        fer = new Sprite(fernando);
        san = new Sprite(santi);

        adr.setScale(.15f);
        san.setScale(.15f);
        die.setScale(.15f);
        fer.setScale(.15f);
        bri.setScale(.15f);

        adr.setPosition(600, 250);
        bri.setPosition(600, 130);
        die.setPosition(600, 20);
        san.setPosition(600, -100);
        fer.setPosition(600, -220);

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
        adr.draw(batch);
        san.draw(batch);
        fer.draw(batch);
        bri.draw(batch);
        die.draw(batch);

        title.mostratMensaje(batch,"CREDITS",ANCHO/2,ALTO-60);
        Santi.mostratMensaje(batch,"Luis Santiago Mille  -  ISC",400,ALTO-180);
        Brian.mostratMensaje(batch,"Brian Saggiante  -  LAD",370,ALTO-300);
        Fer.mostratMensaje(batch,"Luis Fernando Cedenio  -  ISC",430,ALTO-420);
        Adrian.mostratMensaje(batch,"Adrian Mendez  -  ISC",350,ALTO-540);
        Diego.mostratMensaje(batch,"Diego Cervantes  -  LAD",380,ALTO-660);
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
