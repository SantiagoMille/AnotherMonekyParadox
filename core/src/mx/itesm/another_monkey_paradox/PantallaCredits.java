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
    Texture imgBackground;
    private Sprite spriteBackground;

    //background music
    private Music musicMenu = Gdx.audio.newMusic(Gdx.files.internal("prueba.mp3"));

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

        imgBackground = new Texture("nebula.png");
        spriteBackground = new Sprite(imgBackground);
        spriteBackground.setPosition(0, 0);
        spriteBackground.setAlpha(0.4f);


        //Boton Return
        TextureRegionDrawable trdReturn = new TextureRegionDrawable(new TextureRegion(new Texture("go-back.png")));
        ImageButton btnReturn = new ImageButton(trdReturn);
        btnReturn.setPosition(30, ALTO-30-btnReturn.getHeight());


        //Click en boton Return
        btnReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                main.setScreen(new PantallaDeveloper(main));
            }
        });

        Skin skin = new Skin(Gdx.files.internal("skin/comic-ui.json"));

        //Fotos
        foto.setScaling(Scaling.fit);

        Table table = new Table(skin);
        table.defaults().pad(10f);
        table.setFillParent(true);
        table.setPosition(table.getX(),table.getY()+table.getHeight()+100);

        Label scoresTitle = new Label("                CREDITS", skin);
        scoresTitle.setFontScale(4f,4f);
        scoresTitle.setAlignment(Align.center);

        Label adriLabel;
        Label ferLabel;
        Label santiLabel;
        Label diegoLabel;
        Label brianLabel;

        table.add(scoresTitle).colspan(2).fillX().height(150);
        table.row();

        adriLabel=new Label("Adrian\n  ISC", skin);
        adriLabel.setFontScale(3f,3f);
        adriLabel.setHeight(1000);
        adriLabel.setWidth(1000);
        table.add(adriLabel);

        ferLabel= new Label("Luis Fernando\n       ISC", skin);
        ferLabel.setFontScale(3f,3f);
        ferLabel.setHeight(1000);
        ferLabel.setWidth(1000);
        table.add(ferLabel);

        santiLabel = new Label("Luis Santiago\n      ISC", skin);
        santiLabel.setFontScale(3f, 3f);
        santiLabel.setHeight(1000);
        santiLabel.setWidth(1000);
        table.add(santiLabel);

        table.row();
        table.row();

        diegoLabel = new Label("                      Diego\n                     LAD\n\n", skin);
        diegoLabel.setFontScale(3f, 3f);
        table.add(diegoLabel);

        brianLabel = new Label("                      Brian\n                     LAD\n\n", skin);
        brianLabel.setFontScale(3f, 3f);
        table.add(brianLabel);

        stageMenu.addActor(table);
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
