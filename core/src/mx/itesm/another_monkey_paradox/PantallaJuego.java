package mx.itesm.another_monkey_paradox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * Created by santi on 1/30/2018.
 */

class PantallaJuego extends Pantalla implements Screen  {

    private final Main main;

    public static final float ANCHO = 1280;
    public static final float ALTO = 720;

    private boolean powerUpVidaFlag = true;
    private boolean powerUpGranadaFlag = true;

    private boolean firstFilter=true;
    private boolean secondFilter=true;

    //For Background
    Texture boss;
    private Sprite bossSprite;

    private Fondo fondo;

    //Sonido
    private Sound gunSound;
    private Sound boomSound;
    private Sound hitSound;

    //Armas
    private Array<Bala> listaBalas;
    private Array<Granada> listaGranadas;
    //Fisica Granada
    float velocityY;     // Velocidad de la granada

    //Controles del jugador
    private ImageButton granada;
    private ImageButton arma;
    private ImageButton pausa;
    private ImageButton home;
    private ImageButton continua;

    private float stateTime = 0;

    private boolean isMovingRight=false;
    private boolean isMovingLeft = false;

    private boolean isFliped;

    private Personaje personaje;

    //Enemigos
    private Array<Enemigo> listaEnemigos;
    private int vidaEnemigo = 100;

    //Vidas
    private ArrayList<PowerUp> vidas = new ArrayList<PowerUp>();
    int contador = 0;

    //Escena
    private Stage stageNivel;

    //Camara
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;

    // Puntaje y texto
    private int puntosJugador = 0;
    private BitmapFont font;
    public GlyphLayout textoGly;


    public GlyphLayout pausaText;

    //Granada y texto
    private int maxGrandas = 5;
    public GlyphLayout textoGlyGran;


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

    //Textura de Cavernicola 02
    private Texture canervicola02Frame0;
    private Texture canervicola02Frame1;
    private Texture canervicola02Frame2;
    private Texture canervicola02Frame3;

    //Textura de Cavernicola 03
    private Texture canervicola03Frame0;
    private Texture canervicola03Frame1;
    private Texture canervicola03Frame2;
    private Texture canervicola03Frame3;


    //Textura Fondos de los niveles
    private Texture fondoNivel01;

    //Textura botones
    private Texture botonGranada;
    private Texture botonDisparo;
    private Texture botonPausa;

    private Texture botonContinua;
    private Texture botonHome;

    private Texture padBack;
    private Texture padKnob;

    // PAUSA
    private EscenaPausa escenaPausa;

    // Estados del juego
    private EstadoJuego estado;

    //Textura Armas
    private Texture bananaDisparo;
    private Texture bananaGranada;

    private Enemigo enemigo;

    //bananas Colision
    private Bala banana1;
    private Bala banana2;
    private Bala banana3;
    private Bala banana4;
    private Bala banana5;
    private Bala banana6;
    private Boolean crashRight = false;
    private Boolean crashLeft = false;

    //PowerUps
    private Random random;
    private Texture imgpowerUpGranada;
    private Texture imgpowerUpVida;
    private PowerUp powerUpGranada;
    private PowerUp powerUpVida;
    private ArrayList<PowerUp> listaVidasExtra = new ArrayList<PowerUp>();
    private ArrayList<PowerUp> listaGranadasExtra = new ArrayList<PowerUp>();
    Double randomX;
    Double randomX2;

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

        //Lista Enemigos
        listaEnemigos = new Array<Enemigo>();
        for(int i=0; i<4;i++){
            enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,true,i);
            listaEnemigos.add(enemigo);
        }
        for(int i=0; i<4;i++){
            enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,false,i);
            listaEnemigos.add(enemigo);
        }

        //Lista Balas
        listaBalas = new Array<Bala>();

        //Lista Granadas
        listaGranadas = new Array<Granada>();

        //Lista PowerUps
        listaGranadasExtra.add(powerUpGranada);
        listaVidasExtra.add(powerUpVida);

        estado = EstadoJuego.JUGANDO;

        //Gdx.input.setInputProcessor(new ProcesadorEntrada());

    }

    private void crearMapa() {

        cargarTexturas();

        stageNivel = new Stage(vista);

        random = new Random();

        //Objeto que dibuja texto
        font = new BitmapFont(Gdx.files.internal("tutorial.fnt"));
        textoGly = new GlyphLayout(font,"Score");
        textoGlyGran = new GlyphLayout(font,"Score");

        boss = new Texture("boss_stand.png");
        bossSprite = new Sprite(boss);
        bossSprite.setPosition(ANCHO,ALTO/4);

        pausaText = new GlyphLayout(font,"PAUSED",new Color(0,0,0,1),1000f,1,true);

        for(int i=0;i<3;i++){
            if(i<3) {
                vidas.add(new PowerUp(new Texture("vida.png"), camara.position.x + 10 - ANCHO / 2 + (75 * i), ALTO - 70,true));
            }else{
                vidas.add(new PowerUp(new Texture("vida.png"), camara.position.x + 10 - ANCHO / 2 + (75 * i), ALTO - 70, false));
            }
        }

        //Boton granadas
        TextureRegionDrawable btnGranada = new TextureRegionDrawable(new TextureRegion(botonGranada));


        //Granadas Colisión
        banana1 = new Bala(bananaDisparo,false);
        banana1.set(-100,-100);

        banana2 = new Bala(bananaDisparo, false);
        banana2.set(-100,-100);

        banana3 = new Bala(bananaDisparo, false);
        banana3.set(-100,-100);

        banana4 = new Bala(bananaDisparo, true);
        banana4.set(-100,-100);

        banana5 = new Bala(bananaDisparo, true);
        banana5.set(-100,-100);

        banana6 = new Bala(bananaDisparo, true);
        banana6.set(-100,-100);

        randomX = random.nextDouble()*4000;
        randomX2 = random.nextDouble()*4000;
        System.out.println(randomX);
        System.out.println(randomX2);

        //Power Ups
        powerUpVida = new PowerUp(imgpowerUpVida, -100, ALTO/4, true);
        powerUpGranada = new PowerUp(imgpowerUpGranada, -600, ALTO/4, true);

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

        //boton continua
        TextureRegionDrawable btnContinua = new TextureRegionDrawable(new TextureRegion(botonContinua));

        continua = new ImageButton(btnContinua);
        continua.setSize(55, 55);
        continua.setPosition(ANCHO/2-continua.getWidth()/2, 680 - continua.getHeight()/2);

        //boton Home
        TextureRegionDrawable btnHome = new TextureRegionDrawable(new TextureRegion(botonHome));

        home = new ImageButton(btnContinua);
        home.setSize(55, 55);
        home.setPosition(ANCHO/2-continua.getWidth()/2, 680 - continua.getHeight()/2);

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
                if (pad.getKnobPercentX() > 0.25) { // Más de 20% de desplazamiento DERECHA
                    personaje.setRight(true);
                    isMovingRight=true;
                    isMovingLeft = false;
                } else if ( pad.getKnobPercentX() < -0.25 ) {   // Más de 20% IZQUIERDA
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

        // Comportamiento de Boton Granada
        granada.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                if(maxGrandas>0) {
                    if (!isFliped) {
                        Granada grana = new Granada(bananaGranada, false);
                        grana.set(personaje.getX() + 105, personaje.getY() + 68);
                        listaGranadas.add(grana);
                        maxGrandas--;
                    } else {
                        Granada grana = new Granada(bananaGranada, true);
                        grana.set(personaje.getX(), personaje.getY() + 68);
                        listaGranadas.add(grana);
                        maxGrandas--;
                    }
                }
            }
        });

        // Comportamiento de Boton Disparo
        arma.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                gunSound.play();
                if(!isFliped) {
                    Bala nueva = new Bala(bananaDisparo,false);
                    nueva.set(personaje.getX() + 105, personaje.getY() + 68);
                    listaBalas.add(nueva);
                } else {
                    Bala nueva = new Bala(bananaDisparo,true);
                    nueva.set(personaje.getX(), personaje.getY() + 68);
                    listaBalas.add(nueva);
                }
            }
        });

        // Comportamiento de Boton Pausa
        pausa.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                estado = EstadoJuego.PAUSADO;
                //main.setScreen((Screen) new EscenaPausa(vista,batch));
                if(escenaPausa == null){
                    escenaPausa = new EscenaPausa(vista,batch);
                }
                Gdx.input.setInputProcessor(escenaPausa);
                dispose();
            }
        });

        stageNivel.addActor(granada);
        stageNivel.addActor(arma);
        stageNivel.addActor(pausa);
        stageNivel.addActor(pad);

        Gdx.input.setInputProcessor(stageNivel);
    }

    private void cargarTexturas(){

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

        canervicola02Frame0 = assetManager.get("cavernicola02/CM2 3.png");
        canervicola02Frame1 = assetManager.get("cavernicola02/CM2 4.png");
        canervicola02Frame2 = assetManager.get("cavernicola02/CM2 2.png");
        canervicola02Frame3 = assetManager.get("cavernicola02/CM2 1.png");

        canervicola03Frame0 = assetManager.get("cavernicola03/CM3 3.png");
        canervicola03Frame1 = assetManager.get("cavernicola03/CM3 4.png");
        canervicola03Frame2 = assetManager.get("cavernicola03/CM3 2.png");
        canervicola03Frame3 = assetManager.get("cavernicola03/CM3 1.png");


        botonGranada = assetManager.get("granada_icon.png");
        botonDisparo = assetManager.get("bullet_icon.png");
        botonPausa = assetManager.get("pause-button.png");
        botonContinua = assetManager.get("PlayButton.png");
        botonHome = assetManager.get("boton Home.png");
        padBack = assetManager.get("Pad/padBack.png");
        padKnob = assetManager.get("Pad/padKnob.png");

        bananaDisparo = assetManager.get("banana.png");
        bananaGranada = assetManager.get("granana.png");

        imgpowerUpGranada = assetManager.get("Items/GRANADAS.png");
        imgpowerUpVida = assetManager.get("Items/VIDA.png");

        gunSound = assetManager.get("pew.mp3");
        boomSound = assetManager.get("boom.mp3");
        hitSound = assetManager.get("hit.mp3");
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

        if(estado != EstadoJuego.PAUSADO) {
            actualizarObjetos(delta, stateTime);

        }

        if(!powerUpVidaFlag){
            powerUpVida.setX(powerUpVida.getX()-(delta*80));
        }

        if(!powerUpGranadaFlag){
            powerUpGranada.setX(powerUpGranada.getX()-(delta*80));
        }

        //Usar v=d/t o en este caso d=v*t
        Gdx.gl.glClearColor(.3f,.6f,.3f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        fondo.render(batch);
/*
        if(fondo.getImagenA().getX()<-780&&fondo.getImagenA().getX()>-882&&firstFilter){
            firstFilter=false;
            for(int i=0; i<4;i++){
                enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<4;i++){
                enemigo = new Enemigo(canervicola02Frame1, canervicola02Frame1, canervicola02Frame2, canervicola02Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo.getImagenA().getX()<-1480&&fondo.getImagenA().getX()>-1582&&secondFilter){
            secondFilter=false;
            firstFilter=true;
            for(int i=0; i<4;i++){
                enemigo = new Enemigo(canervicola03Frame0, canervicola03Frame1, canervicola03Frame2, canervicola03Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<4;i++){
                enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo.getImagenA().getX()<-2180&&fondo.getImagenA().getX()>-2282&&firstFilter){
            secondFilter=true;
            firstFilter=false;
            for(int i=0; i<4;i++){
                enemigo = new Enemigo(canervicola02Frame0, canervicola02Frame1, canervicola02Frame2, canervicola02Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<4;i++){
                enemigo = new Enemigo(canervicola03Frame0, canervicola03Frame1, canervicola03Frame2, canervicola03Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo.getImagenA().getX()<-2880&&fondo.getImagenA().getX()>-2982&&secondFilter){
            secondFilter=false;
            firstFilter=true;
            for(int i=0; i<4;i++){
                enemigo = new Enemigo(canervicola02Frame0, canervicola02Frame1, canervicola02Frame2, canervicola02Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<4;i++){
                enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo.getImagenA().getX()<-3580&&fondo.getImagenA().getX()>-3600&&firstFilter){
            secondFilter=true;
            firstFilter=false;
            for(int i=0; i<10;i++){
                enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<4;i++){
                enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }*/

        if (fondo.getImagenA().getX()<-randomX &&powerUpVidaFlag){
            powerUpVida.setX(ANCHO*0.75f);
            powerUpVidaFlag=false;
        }

        if (fondo.getImagenA().getX()<-randomX2&&powerUpGranadaFlag){
            powerUpGranada.setX(ANCHO*0.75f);
            powerUpGranadaFlag=false;
        }


        //PANTALLA DE VICTORIA PROVISIONAL
        if(fondo.getImagenA().getX()<-4000&&fondo.getImagenA().getX()>-4010&&firstFilter){
            main.setScreen(new EscenaAstroGanador(main, puntosJugador));
        }

        for(PowerUp e:vidas){
            if(e.isActiva()){
                e.render(batch);
            }
        }

        TextureRegion currentFrame = (TextureRegion) personaje.getAnimacion().getKeyFrame(stateTime,true);

        if(personaje.isRight()&&currentFrame.isFlipX()){
            currentFrame.flip(true,false);
            isFliped = false;
        } else if(!personaje.isRight()&&!currentFrame.isFlipX()){
            currentFrame.flip(true,false);
            isFliped = true;
        }else {}


        //Granadas
        for(Granada Granada: listaGranadas){
            Granada.render(batch);
        }

        //Balas
        for(Bala bala: listaBalas){
            bala.render(batch);
        }

        personaje.render(batch, stateTime, isMovingRight, isMovingLeft);
        //Dibuja enemigos
        for(Enemigo e:listaEnemigos){
            e.render(batch);
            if(estado == EstadoJuego.JUGANDO&&e.right){
                e.setX(e.getX()+(-60*delta));
            }else if(estado == EstadoJuego.JUGANDO&&!e.right){
                if(personaje.getX()<camara.position.x){
                    e.setX(e.getX()+(60*delta));
                }else{
                    e.setX(e.getX()+(15*delta));
                }

            }
        }

        if(fondo.getImagenA().getX()<-3900){
            if(personaje.getX()>=camara.position.x) {
                bossSprite.setPosition(bossSprite.getX() - (delta * 78), bossSprite.getY());
            }
            bossSprite.draw(batch);
        }


        //Texto Score
        textoGly.setText(font, "Score: "+puntosJugador);
        font.draw(batch,textoGly, ANCHO/2 + 95,ALTO-15);

        textoGlyGran.setText(font, "Grenades: "+maxGrandas);
        font.draw(batch,textoGlyGran, ANCHO/2 + 360,ALTO-15);

        //Texto Pausa
        if(estado == EstadoJuego.PAUSADO){
            font.draw(batch,pausaText, ANCHO/4-175,ALTO*13/20);
        }

        powerUpGranada.render(batch);
        powerUpVida.render(batch);
        banana1.render(batch);
        banana2.render(batch);
        banana3.render(batch);
        banana4.render(batch);
        banana5.render(batch);
        banana6.render(batch);
        batch.end();

        stageNivel.draw();

        // Botón PAUSA
        if (estado==EstadoJuego.PAUSADO) {
            escenaPausa.draw(); // Solo si está pausado muestra la imagen
        }

    }

    private void actualizarObjetos(float dt, float stateTime) {

        //Movimiento del personaje
        if(isMovingRight&&!isMovingLeft){
            if(personaje.getX()<camara.position.x){
                personaje.setX(personaje.getX()+(dt*80));
            }else {
                fondo.mover(-dt * 78);
            }

        }else if(isMovingLeft&&!isMovingRight){
            if(personaje.getX()>(camara.position.x - ANCHO/2)){
                personaje.setX(personaje.getX()+(dt*-80));
            }
            if(fondo.getImagenA().getX()>0) {
                fondo.mover(dt * 20);
            }
        }

        //Balas
        int i=0;
        for(Bala bala:listaBalas){
            if(bala.getX()>camara.position.x+ANCHO/2||bala.getX()<camara.position.x-ANCHO/2){
                listaBalas.removeIndex(i);
            }
            if(bala.fliped){
                bala.mover(dt*2);
            } else {
                bala.mover(-dt * 2);
            }
            bala.getSprite().rotate(10);
            i++;
        }

        //Granadas
        int j=0;
        for(Granada granada:listaGranadas){
            if(granada.getX()>camara.position.x+ANCHO/2||granada.getX()<camara.position.x-ANCHO/2){
                listaGranadas.removeIndex(j);
            }
            if(granada.fliped){
                granada.mover(dt*2);
            } else {
                granada.mover(-dt * 2);
            }
            granada.getSprite().rotate(10);
            j++;
        }

        if(crashRight){
            if(banana2.getX()>camara.position.x+ANCHO/2||banana2.getX()<camara.position.x-ANCHO/2){
                crashRight = false;
            }
            banana1.mover(-dt*2);
            banana2.moverY(dt*2);
            banana3.moverY(-dt*2);
        }

        if(crashLeft){
            if(banana5.getX()>camara.position.x+ANCHO/2||banana5.getX()<camara.position.x-ANCHO/2){
                crashRight = false;
            }
            banana4.mover(dt*2);
            banana5.moverY(dt*2);
            banana6.moverY(-dt*2);
        }

        verificarColisionBalaEnemigo(stateTime);
        verificarColisionGranadaEnemigo(stateTime);
        verificarColisionPersonajeEnemigo(stateTime);
        verificarVidaAstro();

    }

    private void verificarVidaEnemigos() {
        for(int i = listaEnemigos.size-1;i>=0;i--){
            if(listaEnemigos.get(i).getVida() <= 0){
                listaEnemigos.removeIndex(i);
                puntosJugador += 10;
                Gdx.app.log("EEHHEHEHEHHE", puntosJugador+"");            }
        }
    }

    private void verificarColisionBalaEnemigo(float dt) {
        Rectangle rectEnemigo;
        Bala bala;

        for(int j =listaBalas.size-1; j>=0;j--){
            bala = listaBalas.get(j);
            for(int i =listaEnemigos.size-1;i>=0;i--){

                enemigo = listaEnemigos.get(i);
                if(enemigo.right) {
                    rectEnemigo = new Rectangle(enemigo.getX() + 210, enemigo.getY(), enemigo.getWidth(), enemigo.getHeight());
                }else{
                    rectEnemigo = new Rectangle(enemigo.getX() - 210, enemigo.getY(), enemigo.getWidth(), enemigo.getHeight());
                }
                if(bala.getSprite().getBoundingRectangle().overlaps(rectEnemigo)){
                    listaBalas.removeIndex(j);
                    vidaEnemigo = enemigo.getVida() - 17;
                    enemigo.setVida(vidaEnemigo);
                    System.out.println(vidaEnemigo);
                }
                verificarVidaEnemigos();
            }
        }
    }

    private void verificarColisionGranadaEnemigo(float dt) {
        Rectangle rectEnemigo;
        Granada granada;
        for(int j =listaGranadas.size-1; j>=0;j--){
            granada = listaGranadas.get(j);
            for(int i =listaEnemigos.size-1;i>=0;i--){
                enemigo = listaEnemigos.get(i);
                if(enemigo.right) {
                    rectEnemigo = new Rectangle(enemigo.getX() + 210, enemigo.getY(), enemigo.getWidth(), enemigo.getHeight());
                }else{
                    rectEnemigo = new Rectangle(enemigo.getX() - 210, enemigo.getY(), enemigo.getWidth(), enemigo.getHeight());
                }
                if(granada.getSprite().getBoundingRectangle().overlaps(rectEnemigo)){
                    boomSound.play();
                    listaGranadas.removeIndex(j);
                    vidaEnemigo = enemigo.getVida() - 100;
                    if(enemigo.right) {
                        explosionGranadaRight(rectEnemigo.getX(), rectEnemigo.getY() + enemigo.getHeight()/3);
                    } else {
                        explosionGranadaLeft(rectEnemigo.getX()+enemigo.getWidth(), rectEnemigo.getY() + enemigo.getHeight()/3);
                    }
                    enemigo.setVida(vidaEnemigo);
                    verificarVidaEnemigos();
                }
            }
        }
    }

    private void explosionGranadaRight(float x, float y){
        crashRight = true;
        banana1.set(x,y);
        banana2.set(x,y);
        banana3.set(x,y);
    }

    private void explosionGranadaLeft(float x, float y){
        crashLeft = true;
        banana4.set(x,y);
        banana5.set(x,y);
        banana6.set(x,y);
    }

    private void verificarColisionPersonajeEnemigo(float dt) {
        Enemigo x;
        Rectangle rectEnemigo;
        Rectangle rectPersonaje;

        for (int i = listaEnemigos.size - 1; i >= 0; i--) {
            x = listaEnemigos.get(i);
            rectEnemigo = new Rectangle(x.getX(), x.getY(), x.getWidth(), x.getHeight());
            rectPersonaje = new Rectangle(personaje.getX(), personaje.getY(), personaje.getWidth(), personaje.getHeight());
                if (rectEnemigo.overlaps(rectPersonaje)) {
                    for (int j = vidas.size() - 1; j >= 0; j--) {
                        if (contador >= 50){
                            if (vidas.get(j).isActiva()) {
                                hitSound.play();
                            vidas.get(j).setActiva(false);
                            System.out.println(vidas.get(j));
                            contador = 0;
                        }
                    }
                }
            }
        }
        contador++;
    }

    private void verificarVidaAstro(){
        int vidasFalse = 0;
        for (int i = vidas.size() - 1; i >= 0; i--){
            if(vidas.get(i).isActiva() == false){
                vidasFalse++;
            }
        }
        if(vidasFalse == vidas.size()){
            escribirScore();
            main.setScreen(new EscenaAstroMuerto(main));
        }
    }

    private void escribirScore() {
        Preferences prefs = Gdx.app.getPreferences("AnotherMonkeyPreferenceStory");
        String scoresString = prefs.getString("highscores", null);
        String[] scores;
        if(scoresString==null){
            scores = new String[0];
        }else {
            scores = scoresString.split(",");
        }
        int puntosActuales;
        int puntosSiguientes;
        if(scores.length>=6) {
            ArrayList<String> temp = new ArrayList<String>();
            temp.addAll(Arrays.asList(scores));
            for (int i = scores.length - 1; i > 0; i--) {
                puntosActuales = Integer.parseInt(scores[i].split(":")[1]);
                puntosSiguientes =0;
                try {
                    puntosSiguientes = Integer.parseInt(scores[i - 1].split(":")[1]);
                }catch (Exception e){
                    puntosActuales = 100000;
                }
                if (puntosJugador > puntosActuales && puntosJugador <= puntosSiguientes) {
                    Date date = new Date(TimeUtils.millis());
                    String dateString = new SimpleDateFormat("MM-dd-yyyy").format(date).toString();
                    temp.add(i,dateString + ":" + puntosJugador + "");
                    temp.remove(temp.size()-1);
                    //scores[i] = dateString + ":" + puntosJugador + "";
                }
            }
            StringBuilder sb = new StringBuilder();
            for(String s:temp){
                sb.append(s);
                sb.append(",");
            }
            prefs.putString("highscores",sb.toString());
            prefs.flush();
        }
        else{
            ArrayList<String> temp = new ArrayList<String>();
            temp.addAll(Arrays.asList(scores));
            /*StringBuilder sb = new StringBuilder();
            for(String s:scores){
                sb.append(s);
                sb.append(",");
            }
            Date date = new Date(TimeUtils.millis());
            String dateString = new SimpleDateFormat("MM-dd-yyyy").format(date).toString();
            sb.append(dateString + ":" + puntosJugador + "");
            prefs.putString("highscores",sb.toString());
            prefs.flush();*/

            for (int i = scores.length - 1; i > 0; i--) {
                puntosActuales = Integer.parseInt(scores[i].split(":")[1]);
                try {
                    puntosSiguientes = Integer.parseInt(scores[i - 1].split(":")[1]);
                }catch(Exception e){
                    puntosSiguientes = 100000;
                }
                if (puntosJugador > puntosActuales && puntosJugador < puntosSiguientes) {
                    Date date = new Date(TimeUtils.millis());
                    String dateString = new SimpleDateFormat("MM-dd-yyyy").format(date).toString();
                    temp.add(i,dateString + ":" + puntosJugador + "");
                    //scores[i] = dateString + ":" + puntosJugador + "";
                }
            }
            if(scores.length==0){

                temp.add("Sometime in the paradox:1000000");
            }
            if(scores.length==1){
                Date date = new Date(TimeUtils.millis());
                String dateString = new SimpleDateFormat("MM-dd-yyyy").format(date).toString();
                temp.add(dateString + ":" + puntosJugador + "");
            }else{
                Date date = new Date(TimeUtils.millis());
                String dateString = new SimpleDateFormat("MM-dd-yyyy").format(date).toString();
                temp.add(dateString + ":" + puntosJugador + "");
            }
            StringBuilder sb = new StringBuilder();
            for(String s:temp){
                sb.append(s);
                sb.append(",");
            }
            prefs.putString("highscores",sb.toString());
            prefs.flush();



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

    enum EstadoJuego {
        JUGANDO,
        PAUSADO
    }

    private class EscenaPausa extends Stage{

        // La escena que se muestra cuando está pausado
        public EscenaPausa(Viewport vista, SpriteBatch batch) {

            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int)(ANCHO*0.5f), (int)(ALTO*0.45f), Pixmap.Format.RGBA8888 );
            //pixmap.setColor( 135/255f, 135/255f, 135/255f, 0.8f );
            pixmap.setColor( 0f, 0f, 0f, 0.35f );
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(ANCHO*0.5f - imgRectangulo.getWidth()*0.5f, ALTO*2.75f/10f);
            this.addActor(imgRectangulo);


            // Salir
            Texture texturaBtnSalir = new Texture("boton Home.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setSize(155, 155);
            btnSalir.setPosition(ANCHO*5.4f/10f, ALTO*3f/10f);
            btnSalir.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    main.setScreen(new PantallaMenu(main));
                }
            });
            this.addActor(btnSalir);

            // Continuar
            Texture texturaBtnContinuar = new Texture("PlayButton.png");
            TextureRegionDrawable trdContinuar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnContinuar));
            ImageButton btnContinuar = new ImageButton(trdContinuar);
            btnContinuar.setSize(155, 155);
            btnContinuar.setPosition(ANCHO*3.4f/10f, ALTO*3f/10f);
            btnContinuar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    estado = EstadoJuego.JUGANDO;
                    Gdx.input.setInputProcessor(stageNivel);
                }
            });
            this.addActor(btnContinuar);
        }
    }

}

