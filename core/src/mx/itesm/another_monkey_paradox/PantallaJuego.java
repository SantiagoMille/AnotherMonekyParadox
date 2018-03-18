package mx.itesm.another_monkey_paradox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.*;

/**
 * Created by santi on 1/30/2018.
 */

class PantallaJuego extends Pantalla implements Screen  {

    private final Main main;

    public static final float ANCHO = 1280;
    public static final float ALTO = 720;

    //For Background
    Texture imgBackground;
    private Sprite spriteBackground;

    private Fondo fondo;

    //Controles del jugador
    private ImageButton left;
    private ImageButton right;
    private ImageButton granada;
    private ImageButton arma;
    private ImageButton pausa;

    private float stateTime=0;

    private boolean isMovingRight=false;
    private boolean isMovingLeft = false;

    private Personaje personaje;

    //Enemigos
    private Array<Enemigo> listaEnemigos;

    //Vidas
    private boolean[] vidas = new boolean[] {true, true, true};
    private Texture imgVida;
    Sprite vida1, vida2, vida3;

    //Escena
    private Stage stageNivel;

    //Camara
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;

    // Puntaje y texto
    private int puntosJugador = 0;
    private Texto texto;

    //Textura de Astro
    private Texture astroCaminata0;
    private Texture astroCaminata1;
    private Texture astroCaminata2;
    private Texture astroCaminata3;

    //Textura de Cavernicola 01
    private Texture canervicola01Frame0;
    private Texture canervicola01Frame1;
    private Texture canervicola01Frame2;
    private Texture canervicola01Frame3;

    //Textura Fondos de los niveles
    private Texture fondoNivel01;

    //Textura botones
    private Texture botonGranada;
    private Texture botonDisparo;
    private Texture botonPausa;
    private Texture padBack;
    private Texture padKnob;

    //Asset Manager
    private final AssetManager assetManager;

    public PantallaJuego(Main main) {
        this.main = main;
        assetManager = main.getAssetManager();
    }

    @Override
    public void show() {

        crearCamara();
        crearMapa();

        personaje = new Personaje(astroCaminata0, astroCaminata1, astroCaminata2, astroCaminata3);

        fondo = new Fondo(fondoNivel01);
        batch = new SpriteBatch();
        //Gdx.input.setInputProcessor(new ProcesadorEntrada());

        //Lista Enemigos
        listaEnemigos = new Array<Enemigo>();
        Enemigo enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3);
        listaEnemigos.add(enemigo);

    }

    private void crearMapa() {

        cargarTexturas();

        stageNivel = new Stage(vista);

        //Objeto que dibuja texto
        texto = new Texto();

        //Vidas
        vida1 = new Sprite(imgVida);
        vida1.setSize(70,70);
        vida1.setPosition(20, 680 - vida1.getHeight()/2);

        vida2 = new Sprite(imgVida);
        vida2.setSize(70,70);
        vida2.setPosition(100, 680 - vida1.getHeight()/2);

        vida3 = new Sprite(imgVida);
        vida3.setSize(70,70);
        vida3.setPosition(180, 680 - vida1.getHeight()/2);

        //Boton granadas
        TextureRegionDrawable btnGranada = new TextureRegionDrawable(new TextureRegion(botonGranada));

        granada = new ImageButton(btnGranada);
        granada.setSize(135, 135);
        granada.setPosition(ANCHO*3/4-granada.getWidth()/2 + 25, ALTO/4-granada.getHeight()/2 - 80);

        //boton disparo
        TextureRegionDrawable btnArma = new TextureRegionDrawable(new TextureRegion(botonDisparo));

        arma = new ImageButton(btnArma);
        arma.setSize(135, 135);
        arma.setPosition(ANCHO*3/4-arma.getWidth()/2 + arma.getWidth() + 55, ALTO/4-arma.getHeight()/2 - 80);

        //boton pausa
        TextureRegionDrawable btnPausa = new TextureRegionDrawable(new TextureRegion(botonPausa));

        pausa = new ImageButton(btnPausa);
        pausa.setSize(55, 55);
        pausa.setPosition(ANCHO/2-pausa.getWidth()/2, 680 - pausa.getHeight()/2);

        Skin skin = new Skin(); // Texturas para el pad
        skin.add("fondo", padBack);
        skin.add("boton", padKnob);

        Touchpad.TouchpadStyle estilo = new Touchpad.TouchpadStyle();
        estilo.background = skin.getDrawable("fondo");
        estilo.knob = skin.getDrawable("boton");

        // Crea el pad
        Touchpad pad = new Touchpad(64,estilo);     // Radio, estilo
        pad.setBounds(20,20,160,160);               // x,y - ancho,alto

        // Comportamiento del pad
        pad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Touchpad pad = (Touchpad)actor;
                if (pad.getKnobPercentX() > 0.10) { // Más de 20% de desplazamiento DERECHA
                    //personaje.setX(personaje.getX()+3);
                    personaje.setRight(true);
                    isMovingRight=true;
                    isMovingLeft = false;
                } else if ( pad.getKnobPercentX() < -0.10 ) {   // Más de 20% IZQUIERDA
                    //personaje.setX(personaje.getX()-3);
                    personaje.setRight(false);
                    isMovingLeft = true;
                    isMovingRight = false;
                } else {
                    isMovingLeft=false;
                    isMovingRight=false;
                }
            }
        });
        pad.setColor(1,1,1,0.7f);   // Transparente

        granada.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                dispose();
                main.setScreen(new PantallaJuego(main));
            }
        });

        arma.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                dispose();
                main.setScreen(new PantallaJuego(main));
            }
        });

        pausa.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                dispose();
                main.setScreen(new PantallaJuego(main));
            }
        });

        stageNivel.addActor(granada);
        stageNivel.addActor(arma);
        stageNivel.addActor(pausa);
        stageNivel.addActor(pad);

        Gdx.input.setInputProcessor(stageNivel);
    }

    private void cargarTexturas(){

        /*
        ESTOS ASSETS SE CARGARAN EN LA PantallaCargandoStoryMode
        //Textura del nivel 1
        assetManager.load("FondoNivel1/NIVEL 1 PAN.png", Texture.class);

        //Textura de Astro
        assetManager.load("Astro/CAMINATA 4.png", Texture.class);
        assetManager.load("Astro/CAMINATA 2.png", Texture.class);
        assetManager.load("Astro/CAMINATA 3.png", Texture.class);
        assetManager.load("Astro/CAMINATA 1.png", Texture.class);

        //Textura de cavernicola01
        assetManager.load("cavernicola01/CM1 3.png", Texture.class);
        assetManager.load("cavernicola01/CM1 4.png", Texture.class);
        assetManager.load("cavernicola01/CM1 2.png", Texture.class);
        assetManager.load("cavernicola01/CM1 1.png", Texture.class);

        //Textura vida
        assetManager.load("vida.png", Texture.class);

        //Textura botones
        assetManager.load("granada_icon.png", Texture.class);
        assetManager.load("bullet_icon.png", Texture.class);
        assetManager.load("pause-button.png", Texture.class);
        assetManager.load("Pad/padBack.png", Texture.class);
        assetManager.load("Pad/padKnob.png", Texture.class);

        // Se bloquea hasta cargar los recursos
        assetManager.finishLoading();
        */

        //Si llega a este punto es porque ya cargó los assets
        // Cuando termina de cargar las texturas, las leemos
        fondoNivel01 = assetManager.get("FondoNivel1/NIVEL 1 PAN.png");

        astroCaminata0 = assetManager.get("Astro/CAMINATA 4.png");
        astroCaminata1 = assetManager.get("Astro/CAMINATA 2.png");
        astroCaminata2 = assetManager.get("Astro/CAMINATA 3.png");
        astroCaminata3 = assetManager.get("Astro/CAMINATA 1.png");

        canervicola01Frame0 = assetManager.get("cavernicola01/CM1 3.png");
        canervicola01Frame1 = assetManager.get("cavernicola01/CM1 4.png");
        canervicola01Frame2 = assetManager.get("cavernicola01/CM1 2.png");
        canervicola01Frame3 = assetManager.get("cavernicola01/CM1 1.png");

        imgVida = assetManager.get("vida.png");

        botonGranada = assetManager.get("granada_icon.png");
        botonDisparo = assetManager.get("bullet_icon.png");
        botonPausa = assetManager.get("pause-button.png");
        padBack = assetManager.get("Pad/padBack.png");
        padKnob = assetManager.get("Pad/padKnob.png");
    }

    private void crearCamara() {
        camara = new OrthographicCamera(ANCHO,ALTO);
        camara.position.set(ANCHO/2, ALTO/2,0);
        camara.update();
        vista = new StretchViewport(ANCHO,ALTO,camara);
    }

    @Override
    public void render(float delta) {
        stateTime+=delta;
        actualizarObjetos(delta);

        //Usar v=d/t o en este caso d=v*t
        Gdx.gl.glClearColor(.3f,.6f,.3f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        //spriteBackground.draw(batch);
        //astro.draw(batch);

        fondo.render(batch);

        vida1.draw(batch);
        vida2.draw(batch);
        vida3.draw(batch);

        texto.mostratMensaje(batch, Integer.toString(puntosJugador), 1150, 700);
        texto.mostratMensaje(batch, "SCORE: ", 1050, 700);

        //Dibuja enemigos
        for(Enemigo e:listaEnemigos){
            e.render(batch);
            e.setX(e.getX()+(-30*delta));
        }

        TextureRegion currentFrame = (TextureRegion) personaje.getAnimacion().getKeyFrame(stateTime,true);

        if(personaje.isRight()&&currentFrame.isFlipX()){
            currentFrame.flip(true,false);
        } else if(!personaje.isRight()&&!currentFrame.isFlipX()){
            currentFrame.flip(true,false);
        }else {}
        personaje.render(batch, stateTime, isMovingRight, isMovingLeft);

        batch.end();
        stageNivel.draw();

    }

    private void actualizarObjetos(float dt) {

        if(isMovingRight&&!isMovingLeft){
            personaje.setX(personaje.getX()+(dt*20));
            fondo.mover(-dt * 20);
        }else if(isMovingLeft&&!isMovingRight){
            personaje.setX(personaje.getX()+(dt*-20));
            fondo.mover(dt * 20);
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
        //Libera la memoria utlizada por los recursos
        fondoNivel01.dispose();

        astroCaminata0.dispose();
        astroCaminata1.dispose();
        astroCaminata2.dispose();
        astroCaminata3.dispose();

        canervicola01Frame0.dispose();
        canervicola01Frame1.dispose();
        canervicola01Frame2.dispose();
        canervicola01Frame3.dispose();

        imgVida.dispose();

        botonGranada.dispose();
        botonDisparo.dispose();
        botonPausa.dispose();
        padBack.dispose();
        padKnob.dispose();

        //El assetManager también libera los recursos
        assetManager.unload("FondoNivel1/NIVEL 1 PAN.png");
        assetManager.unload("Astro/CAMINATA 4.png");
        assetManager.unload("Astro/CAMINATA 2.png");
        assetManager.unload("Astro/CAMINATA 3.png");
        assetManager.unload("Astro/CAMINATA 1.png");
        assetManager.unload("cavernicola01/CM1 3.png");
        assetManager.unload("cavernicola01/CM1 4.png");
        assetManager.unload("cavernicola01/CM1 2.png");
        assetManager.unload("cavernicola01/CM1 1.png");
        assetManager.unload("vida.png");
        assetManager.unload("granada_icon.png");
        assetManager.unload("bullet_icon.png");
        assetManager.unload("pause-button.png");
        assetManager.unload("Pad/padBack.png");
        assetManager.unload("Pad/padKnob.png");
    }

}
