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
    Texture imgBackground, adrian, diego, fernando, brian, santi;
    private Sprite spriteBackground, adr, die, fer, bri, san;

    //background music
    private Music musicMenu = Gdx.audio.newMusic(Gdx.files.internal("loboloco.mp3"));

    private Texto title, Adrian, Santi, Fer, Diego, Brian;

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
        musicMenu.setLooping(true);
        Gdx.input.setCatchBackKey(true);
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

        imgBackground = new Texture("space.png");
        adrian = new Texture("pp_s.jpeg");
        santi = new Texture("pp_a.jpeg");
        fernando = new Texture("pp_d.jpeg");
        diego = new Texture("pp.jpeg");
        brian = new Texture("pp_b.jpeg");

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

        title.mostratMensaje(batch,"CREDITS",ANCHO/6,ALTO-60,215/255f,215/255f,215/255f);
        Santi.mostratMensaje(batch,"Luis Santiago Mille  -  ISC",200,ALTO-180,215/255f,215/255f,215/255f);
        Brian.mostratMensaje(batch,"Brian Saggiante  -  LAD",140,ALTO-300,215/255f,215/255f,215/255f);
        Fer.mostratMensaje(batch,"Luis Fernando Cedenio  -  ISC",265,ALTO-420,215/255f,215/255f,215/255f);
        Adrian.mostratMensaje(batch,"Adrian Mendez  -  ISC",90,ALTO-540,215/255f,215/255f,215/255f);
        Diego.mostratMensaje(batch,"Diego Cervantes  -  LAD",140,ALTO-660,215/255f,215/255f,215/255f);
        batch.end();
        stageMenu.draw();
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            main.setScreen(new PantallaDeveloper(main));
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
