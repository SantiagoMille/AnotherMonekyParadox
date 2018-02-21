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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

/**
 * Created by adrian on 2/9/2018.
 */

class PantallaDeveloper implements Screen {

    private final Main main;

    public static final float ANCHO = 1280;
    public static final float ALTO = 780;

    //Camara
    private OrthographicCamera camara;
    private Viewport vista;
    //Escena
    private Stage stageMenu;
    private Table container;

    private SpriteBatch batch;

    //background music
    private Music musicMenu = Gdx.audio.newMusic(Gdx.files.internal("prueba.mp3"));

    public PantallaDeveloper(Main main) {
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

        Skin skin = new Skin(Gdx.files.internal("skin/comic-ui.json"));

        container = new Table();
        stageMenu.addActor(container);
        container.setFillParent(true);
        container.setPosition(0,20);

        Table table = new Table();

        final ScrollPane scroll = new ScrollPane(table, skin);


        //Boton Return
        TextureRegionDrawable trdReturn = new TextureRegionDrawable(new TextureRegion(new Texture("go-back.png")));
        ImageButton btnReturn = new ImageButton(trdReturn);
        btnReturn.setPosition(30, ALTO-30-btnReturn.getHeight());

        //Click en boton Return
        btnReturn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                main.setScreen(new PantallaMenu(main));
            }
        });

        InputListener stopTouchDown = new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                event.stop();
                return false;
            }
        };

        table.pad(10).defaults().expandX().space(4);
        Label title = new Label("                       Configuraciones\n\n",skin);
        title.setFontScale(3.5f);
        title.setAlignment(Align.right);
        table.add(title).colspan(2).fillX().height(150);
        table.row();
        table.add(new Label("                                                                                                       ", skin)).expandX().fillX();

        TextButton buttonVolumen = new TextButton("Volumen", skin);
        table.add(buttonVolumen);
        buttonVolumen.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                System.out.println("click " + x + ", " + y);
            }
        });

        Slider sliderVolumen = new Slider(0, 100, 1, false, skin);
        sliderVolumen.addListener(stopTouchDown); // Stops touchDown events from propagating to the FlickScrollPane.
        table.add(sliderVolumen);

        table.add(new Label("                                                                                                               ", skin));

        table.row();
        table.add(new Label(" ",skin));
        table.row();
        table.add(new Label(" ",skin));
        table.row();
        table.add(new Label("                                                                                             ", skin)).expandX().fillX();

        TextButton buttonSensitivity = new TextButton("Sensitivity", skin);
        table.add(buttonSensitivity);
        buttonSensitivity.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                System.out.println("click " + x + ", " + y);
            }
        });

        Slider sliderSens = new Slider(0, 100, 1, false, skin);
        sliderSens.addListener(stopTouchDown); // Stops touchDown events from propagating to the FlickScrollPane.
        table.add(sliderSens);

        table.add(new Label("                                                                                                               ", skin));

        final TextButton creditsButton = new TextButton("Creditos", skin.get("default", TextButton.TextButtonStyle.class));
        creditsButton.setChecked(true);
        creditsButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                scroll.setFlickScroll(creditsButton.isChecked());
                main.setScreen(new PantallaCredits(main));
            }
        });

        container.add(scroll).expand().fill().colspan(4);
        container.row().space(10).padBottom(10);
        container.add(new Label("                   .                   .                   .                   .                   .                   .",skin));
        container.add(new Label("                   .                   .                   .                   .                   .                   .                   .",skin));
        table.row();
        table.add(new Label("\n\n\n\n\n\n\n\n\n\n\n ",skin));
        table.row();
        table.add(new Label("\n\n\n\n\n\n\n\n\n\n\n ",skin));
        table.row();
        container.add(creditsButton).left().expandX();

        stageMenu.addActor(btnReturn);

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
        Gdx.gl.glClearColor(127/255f,135/255f,160/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stageMenu.act(Gdx.graphics.getDeltaTime());
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
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
