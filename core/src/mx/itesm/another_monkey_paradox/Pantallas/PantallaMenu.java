package mx.itesm.another_monkey_paradox.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.math.MathUtils;

import mx.itesm.another_monkey_paradox.Main;
import mx.itesm.another_monkey_paradox.PantallasDeCarga.PantallaCargandoStoryMode;
import mx.itesm.another_monkey_paradox.PantallasDeCarga.PantallaSplash;


/**
 * Created by santi on 1/30/2018.
 */

public class PantallaMenu extends Pantalla implements Screen {

    //Escena
    private Stage stageMenu;

    private SpriteBatch batch;

    //For Background
    Texture imgBackground, imgLogo, backgroundStory, backgroundSurvival;
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

    //Textura de botones
    private Texture botonPlay;
    private Texture botonPlayPressed;
    private Texture botonLeaderboard;
    private Texture botonLeaderboardPressed;
    private Texture botonAbout;
    private Texture botonAboutPressed;
    private Texture botonTutorial;
    private Texture botonTutorialPressed;

    public PantallaMenu(Main main) {
        super(main);
    }

    @Override
    public void show() {
        crearCamara();
        crearMenu();
        batch = new SpriteBatch();
        PantallaSplash.musicMenu.setLooping(true);
        PantallaSplash.musicMenu.play();
        inputMultiplexer.addProcessor(new ProcesadorEntrada());
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void crearMenu() {

        cargarTexturas();

        stageMenu = new Stage(vista);

        spriteBackground = new Sprite(backgroundStory);
        spriteBackground.setPosition(0, 0);

        spriteLogo = new Sprite(imgLogo);
        spriteLogo.setPosition(ANCHO/2-spriteLogo.getWidth()/2, ALTO-spriteLogo.getHeight()-40);

        //Boton Play
        TextureRegionDrawable trdPlay = new TextureRegionDrawable(new TextureRegion(botonPlay));
        TextureRegionDrawable trdPlayPressed = new TextureRegionDrawable(new TextureRegion(botonPlayPressed));
        ImageButton btnPlay = new ImageButton(trdPlay, trdPlayPressed);
        btnPlay.setPosition(ANCHO/2-btnPlay.getWidth()/2, -100);
        btnPlay.addAction(Actions.moveTo(ANCHO/2-btnPlay.getWidth()/2, ALTO/4-btnPlay.getHeight()/2, 0.5f));


        //Boton Leaderboard
        TextureRegionDrawable trdLead = new TextureRegionDrawable(new TextureRegion(botonLeaderboard));
        TextureRegionDrawable trdLeadPush = new TextureRegionDrawable(new TextureRegion(botonLeaderboardPressed));
        ImageButton btnLead = new ImageButton(trdLead, trdLeadPush);
        btnLead.setPosition(-200, ALTO/4-btnLead.getHeight()/2);
        btnLead.addAction(Actions.moveTo(ANCHO/4-btnLead.getWidth()/2, ALTO/4-btnLead.getHeight()/2, 0.5f));

        //Boton CONFIG
        TextureRegionDrawable trdConfig = new TextureRegionDrawable(new TextureRegion(botonAbout));
        TextureRegionDrawable trdConfigPush = new TextureRegionDrawable(new TextureRegion(botonAboutPressed));
        ImageButton btnConfig = new ImageButton(trdConfig, trdConfigPush);
        btnConfig.setPosition(ANCHO+200, ALTO/4-btnConfig.getHeight()/2);
        btnConfig.addAction(Actions.moveTo(ANCHO*3/4-btnConfig.getWidth()/2, ALTO/4-btnConfig.getHeight()/2, 0.5f));

        //Boton Tutorial
        TextureRegionDrawable trdTut = new TextureRegionDrawable(new TextureRegion(botonTutorial));
        TextureRegionDrawable trdTutPush = new TextureRegionDrawable(new TextureRegion(botonTutorialPressed));
        ImageButton btnTut = new ImageButton(trdTut, trdTutPush);
        btnTut.setPosition(40, ALTO*8/10+30);
        btnTut.setSize(95,95);

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
                if(isSurvivalMode){
                    main.setScreen(new PantallaCargandoStoryMode(main, 5,0));

                }else {
                    main.setScreen(new PantallaCargandoStoryMode(main, 1, 0));
                }
            }
        });

        //Click en boton Lead
        btnLead.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                dispose();
                if(isSurvivalMode) {
                    main.setScreen(new PantallaScoresSurvival(main));
                }else{
                    main.setScreen(new PantallaScoresStory(main));
                }
            }
        });

        //Click en boton Developer
        btnTut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                dispose();
                main.setScreen(new PantallaDeveloper(main));
            }
        });

        //Click en boton Horda
        btnHorda.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                isSurvivalMode=true;
                spriteBackground = new Sprite(backgroundSurvival);
                spriteBackground.setPosition(0, 0);
                btnHorda.setVisible(false);
                btnRegresarHorda.setVisible(true);
            }


        });

        //Click para regresar al modo historia
        btnRegresarHorda.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                isSurvivalMode=false;
                spriteBackground = new Sprite(backgroundStory);
                spriteBackground.setPosition(0, 0);
                btnRegresarHorda.setVisible(false);
                btnHorda.setVisible(true);
            }


        });

        //Click en boton Tutorial
        btnConfig.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                dispose();
                if(isSurvivalMode) {
                    String toWrite = "Survive as long as you can, fight Dr. Timetravelov's \nminions.\n\n" +
                            "- Use the joystick to move forwards and \n  backwards. \n" +
                            "- Use the blue button to shoot bananas.\n" +
                            "- Use the red button to throw banana grenades.";
                    main.setScreen(new PantallaTutorial(main, toWrite));
                }else{
                    String toWrite = "Astro is seeking revenge against Dr. Timetravelov...\n\n" +
                            "- Use the joystick to move forwards and \n  backwards. \n" +
                            "- Use the blue button to shoot bananas.\n" +
                            "- Use the red button to throw banana grenades.";
                    main.setScreen(new PantallaTutorial(main, toWrite));
                }
            }


        });

        stageMenu.addActor(btnPlay);
        stageMenu.addActor(btnLead);
        stageMenu.addActor(btnConfig);
        stageMenu.addActor(btnTut);
        stageMenu.addActor(btnHorda);
        stageMenu.addActor(btnRegresarHorda);

        inputMultiplexer.addProcessor(stageMenu);
    }

    private void cargarTexturas(){
        /*
        //Cargar las texturas
        assetManager.load("LOGO-2.png", Texture.class);
        assetManager.load("PlayButton.png", Texture.class);
        assetManager.load("PlayButton_Pressed.png", Texture.class);
        assetManager.load("trophy.png", Texture.class);
        assetManager.load("trophy_Pressed.png", Texture.class);
        assetManager.load("about-button.png", Texture.class);
        assetManager.load("About-button_Pressed.png", Texture.class);
        assetManager.load("About2.png", Texture.class);
        assetManager.load("StoryModeBack.png", Texture.class);
        assetManager.load("SurvivalModeBack.png", Texture.class);

        // Se bloquea hasta cargar los recursos
        assetManager.finishLoading();
        */

        // Cuando termina de cargar las texturas, las leemos
        imgLogo = assetManager.get("LOGO-2.png");
        botonPlay = assetManager.get("PlayButton.png");
        botonPlayPressed = assetManager.get("PlayButton_Pressed.png");
        botonLeaderboard = assetManager.get("trophy.png");
        botonLeaderboardPressed = assetManager.get("trophy_Pressed.png");
        botonAbout = assetManager.get("about-button.png");
        botonAboutPressed = assetManager.get("About-button_Pressed.png");
        botonTutorial = assetManager.get("configButton.png");
        botonTutorialPressed = assetManager.get("configButton.png");
        backgroundStory = assetManager.get("StoryModeBack.png");
        backgroundSurvival = assetManager.get("SurvivalModeBack.png");
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
        stageMenu.act(Gdx.graphics.getDeltaTime());
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
        PantallaSplash.musicMenu.stop();
        /*
        imgLogo.dispose();
        botonPlay.dispose();
        botonPlayPressed.dispose();
        botonLeaderboard.dispose();
        botonLeaderboardPressed.dispose();
        botonAbout.dispose();
        botonAboutPressed.dispose();
        botonTutorial.dispose();
        botonTutorialPressed.dispose();
        backgroundStory.dispose();
        backgroundSurvival.dispose();


        //Ahora el assetManager también libera los recursos
        assetManager.unload("LOGO-2.png");
        assetManager.unload("PlayButton.png");
        assetManager.unload("PlayButton_Pressed.png");
        assetManager.unload("trophy.png");
        assetManager.unload("trophy_Pressed.png");
        assetManager.unload("about-button.png");
        assetManager.unload("About-button_Pressed.png");
        assetManager.unload("About2.png");
        assetManager.unload("StoryModeBack.png");
        assetManager.unload("SurvivalModeBack.png");*/
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
                    spriteBackground = new Sprite(backgroundSurvival);
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
                    spriteBackground = new Sprite(backgroundStory);
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
