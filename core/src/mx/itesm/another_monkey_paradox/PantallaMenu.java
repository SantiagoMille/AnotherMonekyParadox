package mx.itesm.another_monkey_paradox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
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
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.math.MathUtils;


/**
 * Created by santi on 1/30/2018.
 */

class PantallaMenu implements Screen {

    private final Main main;

    public static final float ANCHO = 1280;
    public static final float ALTO = 720;

    //Camara
    private OrthographicCamera camara;
    private Viewport vista;
    //Escena
    private Stage stageMenu;

    private SpriteBatch batch;

    //For Background
    Texture imgBackground, imgLogo;
    private Sprite spriteBackground, spriteLogo;

    //Movimiento del título
    int diferencial = 3;
    float grados = 0f;

    //Para el swipe y cambiar a modo horda
    private int distanceSwiped=0;
    private boolean isSurvivalMode = false;
    private ImageButton btnRegresarHorda;
    private ImageButton btnHorda;

    //Para ingresar multiples inputProcessors
    InputMultiplexer inputMultiplexer = new InputMultiplexer();


    public PantallaMenu(Main main) {
        this.main = main;
    }

    @Override
    public void show() {
        crearCamara();
        crearMenu();
        batch = new SpriteBatch();
        PantallaSplash.musicMenu.setLooping(false);
        inputMultiplexer.addProcessor(new ProcesadorEntrada());
        Gdx.input.setInputProcessor(inputMultiplexer);
        PantallaSplash.musicMenu.play();
    }

    private void crearMenu() {
        stageMenu = new Stage(vista);

        imgBackground = new Texture("StoryModeBack.png");
        spriteBackground = new Sprite(imgBackground);
        spriteBackground.setPosition(0, 0);

        imgLogo = new Texture("LOGO-2.png");
        spriteLogo = new Sprite(imgLogo);
        spriteLogo.setPosition(ANCHO/2-spriteLogo.getWidth()/2, ALTO-spriteLogo.getHeight()-40);


        //Boton Play
        TextureRegionDrawable trdPlay = new TextureRegionDrawable(new TextureRegion(new Texture("PlayButton.png")));
        TextureRegionDrawable trdPlayPressed = new TextureRegionDrawable(new TextureRegion(new Texture("PlayButton_Pressed.png")));

        ImageButton btnPlay = new ImageButton(trdPlay, trdPlayPressed);
        btnPlay.setPosition(ANCHO/2-btnPlay.getWidth()/2, ALTO/4-btnPlay.getHeight()/2);

        //Boton Leaderboard
        TextureRegionDrawable trdLead = new TextureRegionDrawable(new TextureRegion(new Texture("trophy.png")));
        TextureRegionDrawable trdLeadPush = new TextureRegionDrawable(new TextureRegion(new Texture("trophy_Pressed.png")));

        ImageButton btnLead = new ImageButton(trdLead, trdLeadPush);
        btnLead.setPosition(ANCHO/4-btnLead.getWidth()/2, ALTO/4-btnLead.getHeight()/2);

        //Boton about
        TextureRegionDrawable trdConfig = new TextureRegionDrawable(new TextureRegion(new Texture("about-button.png")));
        TextureRegionDrawable trdConfigPush = new TextureRegionDrawable(new TextureRegion(new Texture("About-button_Pressed.png")));

        ImageButton btnConfig = new ImageButton(trdConfig, trdConfigPush);
        btnConfig.setPosition(ANCHO*3/4-btnConfig.getWidth()/2, ALTO/4-btnConfig.getHeight()/2);

        //Boton Tutorial
        TextureRegionDrawable trdTut = new TextureRegionDrawable(new TextureRegion(new Texture("but-tut.png")));
        TextureRegionDrawable trdTutPush = new TextureRegionDrawable(new TextureRegion(new Texture("but-tut-push.png")));

        ImageButton btnTut = new ImageButton(trdTut, trdTutPush);
        btnTut.setPosition(ANCHO*9/10-btnTut.getWidth()/2, ALTO*9/10-btnTut.getHeight()/2);

        //Boton modo horda
        TextureRegionDrawable trdHorda = new TextureRegionDrawable(new TextureRegion(new Texture("arrow-point-to-right.png")));
        btnHorda = new ImageButton(trdHorda);
        btnHorda.setSize(60,60);
        btnHorda.setPosition(ANCHO-btnHorda.getWidth()-50, ALTO/2-btnHorda.getHeight()/2-10);

        //Boton regresar de modo horda
        TextureRegionDrawable trdRegresarHorda = new TextureRegionDrawable(new TextureRegion(new Texture("arrow-point-to-left.png")));
        btnRegresarHorda = new ImageButton(trdRegresarHorda);
        btnRegresarHorda.setSize(60,60);
        btnRegresarHorda.setVisible(false);
        btnRegresarHorda.setPosition(50, ALTO/2-btnRegresarHorda.getHeight()/2-10);

        //Click en boton Play
        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                dispose();
                main.setScreen(new PantallaJuego(main));
            }
        });

        //Click en boton Lead
        btnLead.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                dispose();
                main.setScreen(new PantallaScores(main));
            }
        });

        //Click en boton Tutorial
        btnTut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                dispose();
                main.setScreen(new PantallaTutorial(main));
            }
        });

        //Click en boton Horda
        btnHorda.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                isSurvivalMode=true;
                imgBackground = new Texture("SurvivalModeBack.png");
                spriteBackground = new Sprite(imgBackground);
                spriteBackground.setPosition(0, 0);
                btnHorda.setVisible(false);
                btnRegresarHorda.setVisible(true);
            }


        });

        btnRegresarHorda.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                isSurvivalMode=false;
                imgBackground = new Texture("StoryModeBack.png");
                spriteBackground = new Sprite(imgBackground);
                spriteBackground.setPosition(0, 0);
                btnRegresarHorda.setVisible(false);
                btnHorda.setVisible(true);
            }


        });

        //Click en boton Config
        btnConfig.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                dispose();
                main.setScreen(new PantallaDeveloper(main));
            }


        });

        stageMenu.addActor(btnPlay);
        stageMenu.addActor(btnLead);
        stageMenu.addActor(btnConfig);
        stageMenu.addActor(btnTut);
        stageMenu.addActor(btnHorda);
        stageMenu.addActor(btnRegresarHorda);

        inputMultiplexer.addProcessor(stageMenu);
        //Gdx.input.setInputProcessor(stageMenu);
    }

    private void crearCamara() {
        camara = new OrthographicCamera(ANCHO,ALTO);
        camara.position.set(ANCHO/2, ALTO/2,0);
        camara.update();
        vista = new StretchViewport(ANCHO,ALTO,camara);
    }

    @Override
    public void render(float delta) {

        //Actualizar
        imgLogoRebotando();

        //Usar v=d/t o en este caso d=v*t
        Gdx.gl.glClearColor(.1f,.4f,.9f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        spriteBackground.draw(batch);
        spriteLogo.draw(batch);
        batch.end();
        stageMenu.draw();

    }

    public void imgLogoRebotando(){
        float y = MathUtils.sinDeg(grados);

        grados += diferencial;

        spriteLogo.setPosition(ANCHO / 2 - spriteLogo.getWidth() / 2, spriteLogo.getY() + y);

        if (diferencial == 360 || diferencial == 0){
            diferencial = -diferencial;
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
        PantallaSplash.musicMenu.dispose();
    }

    private class ProcesadorEntrada implements InputProcessor {
        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            if(!isSurvivalMode){
                if(screenX>distanceSwiped){
                    distanceSwiped = screenX;
                }
                if(distanceSwiped-screenX>200){
                    isSurvivalMode=true;
                    imgBackground = new Texture("SurvivalModeBack.png");
                    spriteBackground = new Sprite(imgBackground);
                    spriteBackground.setPosition(0, 0);
                    btnHorda.setVisible(false);
                    btnRegresarHorda.setVisible(true);
                    distanceSwiped=screenX;
                }
            }else{
                if(screenX<distanceSwiped){
                    distanceSwiped = screenX;
                }
                if(distanceSwiped+screenX>200&&distanceSwiped<screenX){
                    isSurvivalMode=false;
                    imgBackground = new Texture("StoryModeBack.png");
                    spriteBackground = new Sprite(imgBackground);
                    spriteBackground.setPosition(0, 0);
                    btnRegresarHorda.setVisible(false);
                    btnHorda.setVisible(true);
                    distanceSwiped=screenX;
                }
            }

            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
}
