package mx.itesm.another_monkey_paradox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by santi on 2/9/2018.
 */

public class PantallaScores implements Screen{

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

    public PantallaScores(Main main) {
        this.main = main;
    }

    @Override
    public void show() {
        crearCamara();
        crearMenu();
        batch = new SpriteBatch();

    }

    private void crearMenu() {
        stageMenu = new Stage(vista);

        Skin skin = new Skin(Gdx.files.internal("skin/comic-ui.json"));

        Preferences prefs = Gdx.app.getPreferences("AnotherMonkeyPreference");
        String names = prefs.getString("names", null);
        if(names==null){
            prefs.putString("names", "Astro");
            names = "Astro";
        }
        int scores = prefs.getInteger("highscores", 0);
        if(scores==0){
            prefs.putInteger("highscores", 10000);
            scores = 10000;
        }

        Table table = new Table();
        table.setSkin(skin);
        table.setFillParent(true);

        Label nameLabel = new Label("Name:", skin);
        nameLabel.setFontScale(5f,5f);
        TextField nameText = new TextField("", skin);
        Label addressLabel = new Label("Address:", skin);
        addressLabel.setFontScale(5f,5f);
        TextField addressText = new TextField("", skin);

        table.add(nameLabel);
        table.add(nameText).width(500).height(100);
        table.row();
        table.add(addressLabel);
        table.add(addressText).width(500).height(100);

        stageMenu.addActor(table);

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
        Gdx.gl.glClearColor(66/255f,74/255f,96/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);
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
