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


        //Boton Return
        TextureRegionDrawable trdReturn = new TextureRegionDrawable(new TextureRegion(new Texture("but-ret.png")));
        TextureRegionDrawable trdReturnPush = new TextureRegionDrawable(new TextureRegion(new Texture("but-ret-push.png")));

        ImageButton btnReturn = new ImageButton(trdReturn, trdReturnPush);
        btnReturn.setPosition(ANCHO / 4 - btnReturn.getWidth() / 2, ALTO / 4 - btnReturn.getHeight() / 2);


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

        //Labels para tablas
        Label creditsTitle = new Label("CREDITS", skin);
        creditsTitle.setFontScale(5f, 5f);
        creditsTitle.setAlignment(Align.center);

        Label adriLabel = new Label("Adrian", skin);
        adriLabel.setFontScale(3.5f, 3.5f);
        adriLabel.setAlignment(Align.left);

        Label ferLabel = new Label("Luis Fernando", skin);
        ferLabel.setFontScale(3.5f, 3.5f);
        ferLabel.setAlignment(Align.left);

        Label santiLabel = new Label("Santiago", skin);
        santiLabel.setFontScale(3.5f, 3.5f);
        santiLabel.setAlignment(Align.left);

        Label diegoLabel = new Label("Diego", skin);
        diegoLabel.setFontScale(3.5f, 3.5f);
        diegoLabel.setAlignment(Align.left);

        Label brianLabel = new Label("Brian", skin);
        brianLabel.setFontScale(3.5f, 3.5f);
        brianLabel.setAlignment(Align.left);

        //Tabla ISC
        Table iscTable = new Table();
        iscTable.add(creditsTitle);
        iscTable.row();
        iscTable.add(santiLabel).width(100);
        iscTable.row();
        iscTable.add(ferLabel).width(100);
        iscTable.row();
        iscTable.add(adriLabel).width(100);

        iscTable.setPosition(ANCHO/2, 3*ALTO/4);

        //Tabla LAD
        Table ladTable = new Table();
        ladTable.add(diegoLabel).width(100);
        ladTable.row();
        ladTable.add(brianLabel).width(100);


        ladTable.setPosition(ANCHO/2, ALTO/4);

        stageMenu.addActor(iscTable);
        stageMenu.addActor(ladTable);

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
