package mx.itesm.another_monkey_paradox.Niveles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

import mx.itesm.another_monkey_paradox.Main;
import mx.itesm.another_monkey_paradox.Objetos.Bala;
import mx.itesm.another_monkey_paradox.Objetos.Enemigo;
import mx.itesm.another_monkey_paradox.Objetos.Granada;
import mx.itesm.another_monkey_paradox.Objetos.PowerUp;
import mx.itesm.another_monkey_paradox.Pantallas.EscenaAstroGanador;
import mx.itesm.another_monkey_paradox.Pantallas.EscenaAstroMuerto;
import mx.itesm.another_monkey_paradox.Pantallas.PantallaMenu;
import mx.itesm.another_monkey_paradox.Utils.Fondo;

/**
 * Created by santi on 4/24/2018.
 */

public class PantallaJuegoSurvival extends NivelGenerico implements Screen  {
    //For Background

    private Fondo fondo1, fondo2, fondo3, fondo4;

    private float difficulty;

    //Escena
    private Stage stageNivel;

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

    //Textura de Caballero 03
    private Texture caballero02Frame0; //3
    private Texture caballero02Frame1; //2
    private Texture caballero02Frame2; //4
    private Texture caballero02Frame3; //1

    private Array<Enemigo> listaEnemigosRusos, listaEnemigosAlien;

    //Textura de Ruso 2
    private Texture ruso2Frame0; //4
    private Texture ruso2Frame1; //3
    private Texture ruso2Frame2; //2
    private Texture ruso2Frame3; //1

    //Textura de Alien
    private Texture alienFrame0; //4
    private Texture alienFrame1; //3
    private Texture alienFrame2; //2
    private Texture alienFrame3; //1

    //Textura Fondos de los niveles
    private Texture fondoNivel01, fondoNivel02, fondoNivel03, fondoNivel04;

    // PAUSA
    private PantallaJuegoSurvival.EscenaPausa2 escenaPausa;

    // Estados del juego
    private PantallaJuego.EstadoJuego estado;

    private Music musicNivel5 = Gdx.audio.newMusic(Gdx.files.internal("nivel5.mp3"));

    //private Enemigo enemigo;

    //PowerUps
    private Random random;
    Double randomX;
    Double randomX2;

    public PantallaJuegoSurvival(Main main, int granadas) {
        super(main);
        this.maxGrandas = granadas;
    }

    @Override
    public void pasarDeNivel() {

    }

    @Override
    public void show() {

        crearCamara();
        crearMapa();

        //personaje = new Personaje(astroCaminata0, astroCaminata1, astroCaminata2, astroCaminata3);

        Preferences prefs = Gdx.app.getPreferences("AnotherMonkeyPreferenceStory");

        difficulty = prefs.getFloat("Difficulty");

        fondo1 = new Fondo(fondoNivel01);
        fondo2 = new Fondo(fondoNivel02);
        fondo3 = new Fondo(fondoNivel03);
        fondo4 = new Fondo(fondoNivel04);
        fondo2.getImagenA().setPosition(fondo1.getImagenA().getWidth(),0);
        fondo3.getImagenA().setPosition(fondo2.getImagenA().getWidth()*2,0);
        fondo4.getImagenA().setPosition(fondo3.getImagenA().getWidth()*3,0);
        batch = new SpriteBatch();

        //Lista Enemigos
        listaEnemigos = new Array<Enemigo>();
        listaEnemigosRusos = new Array<Enemigo>();
        listaEnemigosAlien = new Array<Enemigo>();

        for(int i=0; i<getRandomNumber(10,13);i++){
            enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,true,i);
            listaEnemigos.add(enemigo);
        }

        for(int i=0; i<getRandomNumber(10,13);i++){
            enemigo = new Enemigo(alienFrame0, alienFrame1, alienFrame2, alienFrame3,false,i);
            listaEnemigosAlien.add(enemigo);
        }

        //Lista Balas
        listaBalas = new Array<Bala>();

        //Lista Granadas
        listaGranadas = new Array<Granada>();

        estado = PantallaJuego.EstadoJuego.JUGANDO;

        Gdx.input.setCatchBackKey(true);

        musicNivel5.setLooping(true);
        if(prefs.getBoolean("music", true)) {
            musicNivel5.play();
        }

        //Gdx.input.setInputProcessor(new ProcesadorEntrada());

    }

    private static int getRandomNumber(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Max must be greater than Min");
        }
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

    private void crearMapa() {

        cargarTexturas();

        Gdx.input.setCatchBackKey(true);

        stageNivel = new Stage(vista);

        //para sacar número random donde se crean los powerups
        random = new Random();

        //Objeto que dibuja texto
        //font = new BitmapFont(Gdx.files.internal("tutorial.fnt"));
        //textoGly = new GlyphLayout(font,"Score");
        //textoGlyGran = new GlyphLayout(font,"Score");



        //pausaText = new GlyphLayout(font,"PAUSED",new Color(0,0,0,1),1000f,1,true);

        for(int i=0;i<5;i++){
            if(i<3) {
                vidas.add(new PowerUp(new Texture("vida.png"), camara.position.x + 10 - ANCHO / 2 + (75 * i), ALTO - 70,true));
            }else{
                vidas.add(new PowerUp(new Texture("vida.png"), camara.position.x + 10 - ANCHO / 2 + (75 * i), ALTO - 70, false));
            }
        }

        //Granadas Colisión
        //banana1 = new Bala(bananaDisparo,false);
        banana1.set(-100,-100);

        //banana2 = new Bala(bananaDisparo, false);
        banana2.set(-100,-100);

        //banana3 = new Bala(bananaDisparo, false);
        banana3.set(-100,-100);

        //banana4 = new Bala(bananaDisparo, true);
        banana4.set(-100,-100);

        //banana5 = new Bala(bananaDisparo, true);
        banana5.set(-100,-100);

        //banana6 = new Bala(bananaDisparo, true);
        banana6.set(-100,-100);

        randomX = random.nextDouble()*4000;
        randomX2 = random.nextDouble()*4000;

        //Barra Bala
        Bar.setPosition(745, ALTO*0.93f);
        barraBala.setPosition(740, (ALTO*0.92f)+3);
        bananaBarra.setPosition(700, (ALTO*0.90f));

        granada.setPosition(ANCHO*3/4-granada.getWidth()/2 + 25, ALTO/4-granada.getHeight()/2 - 80);
        granadasNum.setPosition(ANCHO*0.712f-granadasNum.getWidth()/2, ALTO/4-granadasNum.getHeight()/2 - 40);

        /*
        //boton disparo
        TextureRegionDrawable btnArma = new TextureRegionDrawable(new TextureRegion(botonDisparo));
        TextureRegionDrawable btnArmaPressed = new TextureRegionDrawable(new TextureRegion(botonDisparoPressed));
        arma = new ImageButton(btnArma, btnArmaPressed);
        */
        arma.setPosition(ANCHO*3/4-arma.getWidth()/2 + arma.getWidth() + 55, ALTO/4-arma.getHeight()/2 - 80);

        /*
        //boton pausa
        TextureRegionDrawable btnPausa = new TextureRegionDrawable(new TextureRegion(botonPausa));
        pausa = new ImageButton(btnPausa);
        */
        pausa.setSize(55, 55);
        pausa.setPosition(ANCHO/2-pausa.getWidth()/2, 680 - pausa.getHeight()/2);

        /*
        //boton continua
        TextureRegionDrawable btnContinua = new TextureRegionDrawable(new TextureRegion(botonContinua));
        continua = new ImageButton(btnContinua);
        */
        continua.setSize(55, 55);
        continua.setPosition(ANCHO/2-continua.getWidth()/2, 680 - continua.getHeight()/2);

        /*
        //boton Home
        TextureRegionDrawable btnHome = new TextureRegionDrawable(new TextureRegion(botonHome));
        home = new ImageButton(btnContinua);
        */
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
        pad.setBounds(40,30,210,210);               // x,y - ancho,alto

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
                if(Bar.getValue() > 0.1) {
                    if(music){gunSound.play();}
                    if (!isFliped) {
                        Bala nueva = new Bala(bananaDisparo, false);
                        nueva.set(personaje.getX() + 105, personaje.getY() + 68);
                        listaBalas.add(nueva);
                    } else {
                        if(music){gunSound.play();}
                        Bala nueva = new Bala(bananaDisparo, true);
                        nueva.set(personaje.getX(), personaje.getY() + 68);
                        listaBalas.add(nueva);
                    }
                }
                Bar.setValue(Bar.getValue()-0.1f);
            }
        });

        // Comportamiento de Boton Pausa
        pausa.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                estado = PantallaJuego.EstadoJuego.PAUSADO;
                //main.setScreen((Screen) new EscenaPausa2(vista,batch));
                if(escenaPausa == null){
                    escenaPausa = new PantallaJuegoSurvival.EscenaPausa2(vista,batch);
                }
                Gdx.input.setInputProcessor(escenaPausa);
                dispose();
            }
        });

        stageNivel.addActor(granada);
        stageNivel.addActor(arma);
        stageNivel.addActor(pausa);
        stageNivel.addActor(pad);
        stageNivel.addActor(Bar);

        Gdx.input.setInputProcessor(stageNivel);
    }

    private void cargarTexturas(){

        //Si llega a este punto es porque ya cargó los assets
        // Cuando termina de cargar las texturas, las leemos
        fondoNivel01 = assetManager.get("Fondos/NIVEL 1.1.png");
        fondoNivel02 = assetManager.get("Fondos/NIVEL 2.2.png");
        fondoNivel03 = assetManager.get("Fondos/NIVEL 3.1.jpg");
        fondoNivel04 = assetManager.get("Fondos/NIVEL 4.2.png");

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

        caballero02Frame0 = assetManager.get("caballero2/caballero 3.png", Texture.class);
        caballero02Frame1 = assetManager.get("caballero2/caballero 2.png", Texture.class);
        caballero02Frame2 =assetManager.get("caballero2/caballero 4.png", Texture.class);
        caballero02Frame3 =assetManager.get("caballero2/caballero 1.png", Texture.class);

        ruso2Frame0 = assetManager.get("ruso2/ruso 1.png", Texture.class);
        ruso2Frame1 = assetManager.get("ruso2/ruso 2.png", Texture.class);
        ruso2Frame2 = assetManager.get("ruso2/ruso 3.png", Texture.class);
        ruso2Frame3 = assetManager.get("ruso2/ruso 4.png", Texture.class);

        alienFrame0 = assetManager.get("Alien/ALIEN 1.png", Texture.class);
        alienFrame1 = assetManager.get("Alien/ALIEN 2.png", Texture.class);
        alienFrame2 = assetManager.get("Alien/ALIEN 3.png", Texture.class);
        alienFrame3 = assetManager.get("Alien/ALIEN 4.png", Texture.class);

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

        if(estado != PantallaJuego.EstadoJuego.PAUSADO) {
            actualizarObjetos(delta, stateTime);

        }

        if(!arma.isPressed()){
            Bar.setValue(Bar.getValue()+0.02f);
        }

        //Bar.setValue(Bar.getValue() + 0.005f);

        if(!powerUpVidaFlag && isMovingRight){
            powerUpVida.setX(powerUpVida.getX()-(delta*80));
        }

        if(!powerUpGranadaFlag && isMovingRight){
            powerUpGranada.setX(powerUpGranada.getX()-(delta*80));
        }

        //Usar v=d/t o en este caso d=v*t
        Gdx.gl.glClearColor(.3f,.6f,.3f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        fondo1.render(batch);
        fondo2.render(batch);
        fondo3.render(batch);
        fondo4.render(batch);

        if(fondo1.getImagenA().getX()<-780&&fondo1.getImagenA().getX()>-882&&firstFilter){
            firstFilter=false;
            for(int i=0; i<getRandomNumber(10,15);i++){
                enemigo = new Enemigo(ruso2Frame0, ruso2Frame1,ruso2Frame2, ruso2Frame3,true,i);
                listaEnemigosRusos.add(enemigo);
            }
            for(int i=0; i<getRandomNumber(10,17);i++){
                enemigo = new Enemigo(canervicola02Frame0, canervicola02Frame1, canervicola02Frame2, canervicola02Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo1.getImagenA().getX()<-890&&fondo1.getImagenA().getX()>-1200&&firstFilter){
            firstFilter=false;
            for(int i=0; i<getRandomNumber(10,17);i++){
                enemigo = new Enemigo(alienFrame0, alienFrame1, alienFrame2, alienFrame3,true,i);
                listaEnemigosAlien.add(enemigo);
            }
            for(int i=0; i<getRandomNumber(10,15);i++){
                enemigo = new Enemigo(ruso2Frame0, ruso2Frame1, ruso2Frame2, ruso2Frame3,false,i);
                listaEnemigosRusos.add(enemigo);
            }
        }

        if(fondo1.getImagenA().getX()<-900&&fondo1.getImagenA().getX()>-1450&&firstFilter){
            firstFilter=false;
            for(int i=0; i<getRandomNumber(10,13);i++){
                enemigo = new Enemigo(caballero02Frame0, caballero02Frame1, caballero02Frame2, caballero02Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<getRandomNumber(10,13);i++){
                enemigo = new Enemigo(alienFrame0, alienFrame1, alienFrame2, alienFrame3,false,i);
                listaEnemigosAlien.add(enemigo);
            }
        }


        if(fondo1.getImagenA().getX()<-1480&&fondo1.getImagenA().getX()>-1582&&secondFilter){
            secondFilter=false;
            firstFilter=true;
            for(int i=0; i<getRandomNumber(10,15);i++){
                enemigo = new Enemigo(canervicola03Frame0, canervicola03Frame1, canervicola03Frame2, canervicola03Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<getRandomNumber(10,15);i++){
                enemigo = new Enemigo(ruso2Frame0, ruso2Frame1, ruso2Frame2, ruso2Frame3,false,i);
                listaEnemigosRusos.add(enemigo);
            }
        }

        if(fondo1.getImagenA().getX()<-1600&&fondo1.getImagenA().getX()>-2110&&secondFilter){
            secondFilter=false;
            firstFilter=true;
            for(int i=0; i<getRandomNumber(10,13);i++){
                enemigo = new Enemigo(caballero02Frame0, caballero02Frame1, caballero02Frame2, caballero02Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<getRandomNumber(10,13);i++){
                enemigo = new Enemigo(alienFrame0, alienFrame1, alienFrame2, alienFrame3,false,i);
                listaEnemigosAlien.add(enemigo);
            }
        }

        if(fondo1.getImagenA().getX()<-2180&&fondo1.getImagenA().getX()>-2282&&firstFilter){
            secondFilter=true;
            firstFilter=false;
            for(int i=0; i<getRandomNumber(10,15);i++){
                enemigo = new Enemigo(canervicola02Frame0, canervicola02Frame1, canervicola02Frame2, canervicola02Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<getRandomNumber(10,15);i++){
                enemigo = new Enemigo(caballero02Frame0, caballero02Frame1, caballero02Frame2, caballero02Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo1.getImagenA().getX()<-2300&&fondo1.getImagenA().getX()>-2850&&secondFilter){
            secondFilter=false;
            firstFilter=true;
            for(int i=0; i<getRandomNumber(10,13);i++){
                enemigo = new Enemigo(ruso2Frame0, ruso2Frame1, ruso2Frame2, ruso2Frame3,true,i);
                listaEnemigosRusos.add(enemigo);
            }
            for(int i=0; i<getRandomNumber(10,13);i++){
                enemigo = new Enemigo(caballero02Frame0, caballero02Frame1, caballero02Frame2, caballero02Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo1.getImagenA().getX()<-2880&&fondo1.getImagenA().getX()>-2982&&secondFilter){
            secondFilter=false;
            firstFilter=true;
            for(int i=0; i<getRandomNumber(10,15);i++){
                enemigo = new Enemigo(ruso2Frame0, ruso2Frame1, ruso2Frame2, ruso2Frame3,true,i);
                listaEnemigosRusos.add(enemigo);
            }
            for(int i=0; i<getRandomNumber(10,15);i++){
                enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo1.getImagenA().getX()<-3000&&fondo1.getImagenA().getX()>-3500&secondFilter){
            secondFilter=false;
            firstFilter=true;
            for(int i=0; i<getRandomNumber(10,13);i++){
                enemigo = new Enemigo(caballero02Frame0, caballero02Frame1, caballero02Frame2, caballero02Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<getRandomNumber(10,13);i++){
                enemigo = new Enemigo(alienFrame0, alienFrame1, alienFrame2, alienFrame3,false,i);
                listaEnemigosAlien.add(enemigo);
            }
        }

        if(fondo1.getImagenA().getX()<-3580&&fondo1.getImagenA().getX()>-3600&&firstFilter){
            secondFilter=true;
            firstFilter=false;
            for(int i=0; i<getRandomNumber(9,15);i++){
                enemigo = new Enemigo(ruso2Frame0, ruso2Frame1, ruso2Frame2, ruso2Frame3,true,i);
                listaEnemigosRusos.add(enemigo);
            }
            for(int i=0; i<getRandomNumber(9,15);i++){
                enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        //////////////////////////////////////

        if(fondo3.getImagenA().getX()<-780&&fondo3.getImagenA().getX()>-882&&firstFilter){
            firstFilter=false;
            for(int i=0; i<getRandomNumber(10,15);i++){
                enemigo = new Enemigo(ruso2Frame0, ruso2Frame1,ruso2Frame2, ruso2Frame3,true,i);
                listaEnemigosRusos.add(enemigo);
            }
            for(int i=0; i<getRandomNumber(10,15);i++){
                enemigo = new Enemigo(canervicola02Frame0, canervicola02Frame1, canervicola02Frame2, canervicola02Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo3.getImagenA().getX()<-890&&fondo3.getImagenA().getX()>-1200&&firstFilter){
            firstFilter=false;
            for(int i=0; i<getRandomNumber(10,15);i++){
                enemigo = new Enemigo(alienFrame0, alienFrame1, alienFrame2, alienFrame3,true,i);
                listaEnemigosAlien.add(enemigo);
            }
            for(int i=0; i<getRandomNumber(10,15);i++){
                enemigo = new Enemigo(ruso2Frame0, ruso2Frame1, ruso2Frame2, ruso2Frame3,false,i);
                listaEnemigosRusos.add(enemigo);
            }
        }

        if(fondo3.getImagenA().getX()<-900&&fondo3.getImagenA().getX()>-1450&&firstFilter){
            firstFilter=false;
            for(int i=0; i<getRandomNumber(10,13);i++){
                enemigo = new Enemigo(caballero02Frame0, caballero02Frame1, caballero02Frame2, caballero02Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<getRandomNumber(10,13);i++){
                enemigo = new Enemigo(alienFrame0, alienFrame1, alienFrame2, alienFrame3,false,i);
                listaEnemigosAlien.add(enemigo);
            }
        }


        if(fondo3.getImagenA().getX()<-1480&&fondo3.getImagenA().getX()>-1582&&secondFilter){
            secondFilter=false;
            firstFilter=true;
            for(int i=0; i<getRandomNumber(10,15);i++){
                enemigo = new Enemigo(canervicola03Frame0, canervicola03Frame1, canervicola03Frame2, canervicola03Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<getRandomNumber(10,15);i++){
                enemigo = new Enemigo(ruso2Frame0, ruso2Frame1, ruso2Frame2, ruso2Frame3,false,i);
                listaEnemigosRusos.add(enemigo);
            }
        }

        if(fondo3.getImagenA().getX()<-1600&&fondo3.getImagenA().getX()>-2110&&secondFilter){
            secondFilter=false;
            firstFilter=true;
            for(int i=0; i<getRandomNumber(10,13);i++){
                enemigo = new Enemigo(caballero02Frame0, caballero02Frame1, caballero02Frame2, caballero02Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<getRandomNumber(10,13);i++){
                enemigo = new Enemigo(alienFrame0, alienFrame1, alienFrame2, alienFrame3,false,i);
                listaEnemigosAlien.add(enemigo);
            }
        }

        if(fondo3.getImagenA().getX()<-2180&&fondo3.getImagenA().getX()>-2282&&firstFilter){
            secondFilter=true;
            firstFilter=false;
            for(int i=0; i<getRandomNumber(10,15);i++){
                enemigo = new Enemigo(canervicola02Frame0, canervicola02Frame1, canervicola02Frame2, canervicola02Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<getRandomNumber(10,15);i++){
                enemigo = new Enemigo(caballero02Frame0, caballero02Frame1, caballero02Frame2, caballero02Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo3.getImagenA().getX()<-2300&&fondo3.getImagenA().getX()>-2850&&secondFilter){
            secondFilter=false;
            firstFilter=true;
            for(int i=0; i<getRandomNumber(10,13);i++){
                enemigo = new Enemigo(ruso2Frame0, ruso2Frame1, ruso2Frame2, ruso2Frame3,true,i);
                listaEnemigosRusos.add(enemigo);
            }
            for(int i=0; i<getRandomNumber(10,13);i++){
                enemigo = new Enemigo(caballero02Frame0, caballero02Frame1, caballero02Frame2, caballero02Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo3.getImagenA().getX()<-2880&&fondo3.getImagenA().getX()>-2982&&secondFilter){
            secondFilter=false;
            firstFilter=true;
            for(int i=0; i<getRandomNumber(10,15);i++){
                enemigo = new Enemigo(ruso2Frame0, ruso2Frame1, ruso2Frame2, ruso2Frame3,true,i);
                listaEnemigosRusos.add(enemigo);
            }
            for(int i=0; i<getRandomNumber(10,15);i++){
                enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo3.getImagenA().getX()<-3000&&fondo3.getImagenA().getX()>-3500&secondFilter){
            secondFilter=false;
            firstFilter=true;
            for(int i=0; i<getRandomNumber(10,13);i++){
                enemigo = new Enemigo(caballero02Frame0, caballero02Frame1, caballero02Frame2, caballero02Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<getRandomNumber(10,13);i++){
                enemigo = new Enemigo(alienFrame0, alienFrame1, alienFrame2, alienFrame3,false,i);
                listaEnemigosAlien.add(enemigo);
            }
        }

        if(fondo3.getImagenA().getX()<-3580&&fondo3.getImagenA().getX()>-3600&&firstFilter){
            secondFilter=true;
            firstFilter=false;
            for(int i=0; i<getRandomNumber(9,15);i++){
                enemigo = new Enemigo(ruso2Frame0, ruso2Frame1, ruso2Frame2, ruso2Frame3,true,i);
                listaEnemigosRusos.add(enemigo);
            }
            for(int i=0; i<getRandomNumber(9,15);i++){
                enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        if (fondo1.getImagenA().getX()<-randomX &&powerUpVidaFlag){
            powerUpVida.setX(ANCHO*0.75f);
            powerUpVidaFlag=false;
        }

        if (fondo2.getImagenA().getX()<-randomX2&&powerUpGranadaFlag){
            powerUpGranada.setX(ANCHO*0.75f);
            powerUpGranadaFlag=false;
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

            if(estado == PantallaJuego.EstadoJuego.JUGANDO&&e.right){

                if(personaje.getX()<camara.position.x){
                    e.setX(e.getX()+(-60*delta));
                }else{
                    e.setX(e.getX()+(-80*delta));
                }
            }else if(estado == PantallaJuego.EstadoJuego.JUGANDO&&!e.right){
                if(personaje.getX()<camara.position.x){
                    e.setX(e.getX()+(60*delta));
                }else{
                    e.setX(e.getX()+(15*delta));
                }

            }
        }

        for(Enemigo e:listaEnemigosRusos){
            e.render(batch);

            if(estado == PantallaJuego.EstadoJuego.JUGANDO&&e.right){

                if(personaje.getX()<camara.position.x){
                    e.setX(e.getX()+(-60*delta));
                }else{
                    e.setX(e.getX()+(-80*delta));
                }
            }else if(estado == PantallaJuego.EstadoJuego.JUGANDO&&!e.right){
                if(personaje.getX()<camara.position.x){
                    e.setX(e.getX()+(60*delta));
                }else{
                    e.setX(e.getX()+(15*delta));
                }

            }
        }

        for(Enemigo e:listaEnemigosAlien){
            e.render(batch);

            if(estado == PantallaJuego.EstadoJuego.JUGANDO&&e.right){

                if(personaje.getX()<camara.position.x){
                    e.setX(e.getX()+(-60*delta));
                }else{
                    e.setX(e.getX()+(-80*delta));
                }
            }else if(estado == PantallaJuego.EstadoJuego.JUGANDO&&!e.right){
                if(personaje.getX()<camara.position.x){
                    e.setX(e.getX()+(60*delta));
                }else{
                    e.setX(e.getX()+(15*delta));
                }

            }
        }

        //Texto Score
        textoGly.setText(font, "Score: "+ puntosJugador);
        font.draw(batch,textoGly, ANCHO/2 + 370,ALTO-15);

        granadasNum.draw(batch);
        textoGlyGran.setText(font, "" + maxGrandas);
        font.draw(batch,textoGlyGran, 899, 161);

        //Texto Pausa
        if(estado == PantallaJuego.EstadoJuego.PAUSADO){
            font.draw(batch,pausaText, ANCHO/4-175,ALTO*13/20);
        }

        powerUpGranada.render(batch);
        powerUpVida.render(batch);
        barraBala.draw(batch);
        banana1.render(batch);
        banana2.render(batch);
        banana3.render(batch);
        banana4.render(batch);
        banana5.render(batch);
        banana6.render(batch);
        batch.end();

        stageNivel.act();
        stageNivel.draw();

        batch.begin();
        bananaBarra.draw(batch);
        batch.end();

        // Botón PAUSA
        if (estado== PantallaJuego.EstadoJuego.PAUSADO) {
            escenaPausa.draw(); // Solo si está pausado muestra la imagen
        }

        Gdx.app.log("render", "fps="+Gdx.graphics.getFramesPerSecond());

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            main.setScreen(new PantallaMenu(main));
        }


    }

    private void actualizarObjetos(float dt, float stateTime) {

        //Movimiento del personaje
        if(isMovingRight&&!isMovingLeft){
            if(personaje.getX()<camara.position.x){
                personaje.setX(personaje.getX()+(dt*110));
            }else {
                fondo1.mover(-dt * 109);
                fondo2.mover(-dt * 109);
                fondo3.mover(-dt * 109);
                fondo4.mover(-dt * 109);
            }

        }else if(isMovingLeft&&!isMovingRight){
            if(personaje.getX()>(camara.position.x - ANCHO/2)){
                personaje.setX(personaje.getX()+(dt*-110));
            }
            if(fondo1.getImagenA().getX()>0) {
                fondo1.mover(dt * 20);
                fondo2.mover(dt * 20);
                fondo3.mover(dt * 20);
                fondo4.mover(dt * 20);
            }
        }

        //Balas
        int i=0;
        for(Bala bala:listaBalas){
            if (bala.getX() > camara.position.x + ANCHO / 2 || bala.getX() < camara.position.x - ANCHO / 2) {
                listaBalas.removeIndex(i);
            }
            if (bala.fliped) {
                bala.mover(dt * 2);
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
            if(banana1.getX()<ANCHO/20-banana2.getWidth()||banana1.getX()>ANCHO-ANCHO/20){
                crashRight = false;
                banana1.set(ANCHO,ALTO);
                banana2.set(ANCHO,ALTO);
                banana3.set(ANCHO,ALTO);
                banana4.set(ANCHO,ALTO);
                banana5.set(ANCHO,ALTO);
                banana6.set(ANCHO,ALTO);
            }
            banana1.mover(-dt*2);
            banana2.moverY(dt*2);
            banana2.mover(-dt*2);
            banana3.moverY(-dt*2);
            banana3.mover(-dt*2);
            banana4.mover(dt*2);
            banana5.moverY(dt*2);
            banana6.moverY(-dt*2);
            banana5.mover(dt*2);
            banana6.mover(dt*2);
        }

        if(crashLeft){
            if(banana4.getX()>ANCHO-ANCHO/20||banana4.getX()<ANCHO/20-banana5.getWidth()){
                crashRight = false;
                banana1.set(ANCHO,ALTO);
                banana2.set(ANCHO,ALTO);
                banana3.set(ANCHO,ALTO);
                banana4.set(ANCHO,ALTO);
                banana5.set(ANCHO,ALTO);
                banana6.set(ANCHO,ALTO);
            }
            banana1.mover(-dt*2);
            banana2.moverY(dt*2);
            banana2.mover(-dt*2);
            banana3.moverY(-dt*2);
            banana3.mover(-dt*2);
            banana4.mover(dt*2);
            banana5.moverY(dt*2);
            banana6.moverY(-dt*2);
            banana5.mover(dt*2);
            banana6.mover(dt*2);
        }

        verificarColisionBalaEnemigo(stateTime,difficulty, 7);
        verificarColisionGranadaEnemigo(stateTime);
        verificarColisionPersonajeItemGranada();
        verificarColisionPersonajeItemVida(3);
        verificarColisionBalaRusoo(difficulty,7);
        verificarColisionGranadaRusoo();

        verificarColisionGranadaEnemigoAlien(stateTime);
        verificarColisionBalaEnemigoAlien(stateTime,difficulty,7);
        verificarVidaAstro();

        timeSinceCollision += dt;
        System.out.println("TimeSinceCollision: " + timeSinceCollision);
        if (timeSinceCollision > 1.8f) {
            boolean shake = verificarColisionPersonajeEnemigo(dt);
            boolean shake2 = verificarColisionPersonajeAlienn(stateTime);
            boolean shake3 = verificarColisionPersonajeEnemigoRuso(stateTime);
            if(shake||shake2||shake3){
                Gdx.input.vibrate(350);
                screenShake.update(dt, camara);
                batch.setProjectionMatrix(camara.combined);
                camara.update();
                camara.position.x = ANCHO/2;
                camara.position.y = ALTO/2;
                timeSinceCollision = 0;
            }
        }

    }

    protected void verificarVidaAstro(){
        int vidasFalse = 0;
        for (int i = vidas.size() - 1; i >= 0; i--){
            if(vidas.get(i).isActiva() == false){
                vidasFalse++;
            }
        }
        if(vidasFalse == vidas.size()){
            escribirScore(false);
            main.setScreen(new EscenaAstroMuerto(main, puntosJugador));
        }
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width,height);
    }

    @Override
    public void pause() {
        estado = PantallaJuego.EstadoJuego.PAUSADO;
        musicNivel5.pause();
    }

    @Override
    public void resume() {
        estado = PantallaJuego.EstadoJuego.JUGANDO;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        musicNivel5.stop();
        musicNivel5.dispose();

    }

    enum EstadoJuego {
        JUGANDO,
        PAUSADO
    }

    private class EscenaPausa2 extends Stage{

        // La escena que se muestra cuando está pausado
        public EscenaPausa2(Viewport vista, SpriteBatch batch) {

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
                    musicNivel5.stop();
                    musicNivel5.dispose();
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
                    estado = PantallaJuego.EstadoJuego.JUGANDO;
                    Gdx.input.setInputProcessor(stageNivel);
                    musicNivel5.play();
                }
            });
            this.addActor(btnContinuar);
        }
    }

    protected void verificarColisionBalaRusoo(float diff, int difDelNivel) {
        if(diff<0.2) diff = 0.2f;
        Rectangle rectEnemigo;
        Bala bala;

        for(int j =listaBalas.size-1; j>=0;j--){
            bala = listaBalas.get(j);
            for(int i =listaEnemigosRusos.size-1;i>=0;i--){

                enemigo = listaEnemigosRusos.get(i);
                if(enemigo.right) {
                    rectEnemigo = new Rectangle(enemigo.getX()+80, enemigo.getY(), enemigo.getWidth(), enemigo.getHeight());
                }else{
                    rectEnemigo = new Rectangle(enemigo.getX()-80, enemigo.getY(), enemigo.getWidth(), enemigo.getHeight());
                }
                if(bala.getSprite().getBoundingRectangle().overlaps(rectEnemigo)){
                    try {
                        listaBalas.removeIndex(j);

                    }catch (Exception e){

                    }

                    vidaEnemigo = enemigo.getVida() - (float)((12-difDelNivel)/diff);
                    enemigo.setVida(vidaEnemigo);
                    System.out.println(vidaEnemigo);
                }
                verificarVidaEnemigosR();
            }
        }
    }

    protected boolean verificarColisionPersonajeAlienn(float dt) {
        Enemigo x;
        Rectangle rectEnemigo;
        Rectangle rectPersonaje;
        boolean hit = false;

        for (int i = listaEnemigosAlien.size - 1; i >= 0; i--) {
            x = listaEnemigosAlien.get(i);
            if(x.isRight()){
                rectEnemigo = new Rectangle(x.getX()+130, x.getY(), x.getWidth(), x.getHeight());
            } else {
                rectEnemigo = new Rectangle(x.getX()-130, x.getY(), x.getWidth(), x.getHeight());
            }
            if(!personaje.isRight()) {
                rectPersonaje = new Rectangle(personaje.getX()+25, personaje.getY(), personaje.getWidth(), personaje.getHeight());
            } else{
                rectPersonaje = new Rectangle(personaje.getX()-25, personaje.getY(), personaje.getWidth(), personaje.getHeight());
            }
            if (rectEnemigo.overlaps(rectPersonaje)) {
                if (x.getAnimacion().getKeyFrameIndex(dt) == 0){
                    for (int j = vidas.size() - 1; j >= 0; j--) {
                        if (contador >= 1) {
                            if (vidas.get(j).isActiva()) {
                                if(music){hitSound.play();}
                                vidas.get(j).setActiva(false);
                                hit = true;
                                contador = 0;
                            }
                        }
                    }
                }
            }
        }
        contador++;
        return hit;
    }

    protected boolean verificarColisionPersonajeEnemigoRuso(float dt) {
        Enemigo x;
        Rectangle rectEnemigo;
        Rectangle rectPersonaje;
        boolean hit = false;

        for (int i = listaEnemigosRusos.size - 1; i >= 0; i--) {
            x = listaEnemigosRusos.get(i);
            rectEnemigo = new Rectangle(x.getX(), x.getY(), x.getWidth(), x.getHeight());
            if(!personaje.isRight()) {
                rectPersonaje = new Rectangle(personaje.getX()+25, personaje.getY(), personaje.getWidth(), personaje.getHeight());
            } else{
                rectPersonaje = new Rectangle(personaje.getX()-25, personaje.getY(), personaje.getWidth(), personaje.getHeight());
            }
            if (rectEnemigo.overlaps(rectPersonaje)) {
                if (x.getAnimacion().getKeyFrameIndex(dt) == 0){
                    for (int j = vidas.size() - 1; j >= 0; j--) {
                        if (contador >= 1) {
                            if (vidas.get(j).isActiva()) {
                                if(music){hitSound.play();}
                                vidas.get(j).setActiva(false);
                                hit = true;
                                contador = 0;
                            }
                        }
                    }
                }
            }
        }
        contador++;
        return hit;
    }


    protected void verificarColisionGranadaEnemigoAlien(float dt) {
        Rectangle rectEnemigo;
        Granada granada;
        for(int j =listaGranadas.size-1; j>=0;j--){
            granada = listaGranadas.get(j);
            for(int i =listaEnemigosAlien.size-1;i>=0;i--){
                enemigo = listaEnemigosAlien.get(i);
                if(enemigo.right) {
                    rectEnemigo = new Rectangle(enemigo.getX() + 210, enemigo.getY(), enemigo.getWidth(), enemigo.getHeight());
                }else{
                    rectEnemigo = new Rectangle(enemigo.getX() - 210, enemigo.getY(), enemigo.getWidth(), enemigo.getHeight());
                }
                if(granada.getSprite().getBoundingRectangle().overlaps(rectEnemigo)){
                    if(music){boomSound.play();}
                    try{
                        listaGranadas.removeIndex(j);
                    }catch (Exception e){}
                    vidaEnemigo = enemigo.getVida() - 100;
                    if(enemigo.right) {
                        explosionGranadaRight(rectEnemigo.getX(), rectEnemigo.getY() + enemigo.getHeight()/3);
                    } else {
                        explosionGranadaLeft(rectEnemigo.getX()+enemigo.getWidth(), rectEnemigo.getY() + enemigo.getHeight()/3);
                    }
                    enemigo.setVida(vidaEnemigo);
                    verificarVidaEnemigosA();
                }
            }
        }
    }

    protected void verificarColisionBalaEnemigoAlien(float dt, float diff, int difDelNivel) {
        if(diff<0.2) diff = 0.2f;
        Rectangle rectEnemigo;
        Bala bala;

        for(int j =listaBalas.size-1; j>=0;j--){
            bala = listaBalas.get(j);
            for(int i =listaEnemigosAlien.size-1;i>=0;i--){

                enemigo = listaEnemigosAlien.get(i);
                if(enemigo.right) {
                    rectEnemigo = new Rectangle(enemigo.getX() + 210, enemigo.getY(), enemigo.getWidth(), enemigo.getHeight());
                }else{
                    rectEnemigo = new Rectangle(enemigo.getX() - 210, enemigo.getY(), enemigo.getWidth(), enemigo.getHeight());
                }
                if(bala.getSprite().getBoundingRectangle().overlaps(rectEnemigo)){
                    try{
                        listaBalas.removeIndex(j);
                    }catch (Exception e){}
                    vidaEnemigo = enemigo.getVida() - ((12-difDelNivel)/diff);
                    enemigo.setVida(vidaEnemigo);
                    System.out.println(vidaEnemigo);
                }
                verificarVidaEnemigosA();
            }
        }
    }

    protected void verificarColisionGranadaRusoo() {
        Rectangle rectEnemigo;
        Granada granada;
        for(int j =listaGranadas.size-1; j>=0;j--){
            granada = listaGranadas.get(j);
            for(int i =listaEnemigosRusos.size-1;i>=0;i--){
                enemigo = listaEnemigosRusos.get(i);
                if(enemigo.right) {
                    rectEnemigo = new Rectangle(enemigo.getX() + 84, enemigo.getY(), enemigo.getWidth(), enemigo.getHeight());
                }else{
                    rectEnemigo = new Rectangle(enemigo.getX() - 84, enemigo.getY(), enemigo.getWidth(), enemigo.getHeight());
                }
                if(granada.getSprite().getBoundingRectangle().overlaps(rectEnemigo)){
                    if(music){boomSound.play();}
                    try {
                        listaGranadas.removeIndex(j);
                    }catch (Exception e){ }
                    vidaEnemigo = enemigo.getVida() - 100;
                    if(enemigo.right) {
                        explosionGranadaRight(rectEnemigo.getX(), rectEnemigo.getY() + enemigo.getHeight()/3);
                    } else {
                        explosionGranadaLeft(rectEnemigo.getX()+enemigo.getWidth(), rectEnemigo.getY() + enemigo.getHeight()/3);
                    }
                    enemigo.setVida(vidaEnemigo);
                    verificarVidaEnemigosR();
                }
            }
        }
    }

    protected void verificarVidaEnemigosR() {
        for(int i = listaEnemigosRusos.size-1;i>=0;i--){
            if(listaEnemigosRusos.get(i).getVida() <= 0){
                listaEnemigosRusos.removeIndex(i);
                puntosJugador += 10;

            }
        }
    }

    protected void verificarVidaEnemigosA() {
        for(int i = listaEnemigosAlien.size-1;i>=0;i--){
            if(listaEnemigosAlien.get(i).getVida() <= 0){
                listaEnemigosAlien.removeIndex(i);
                puntosJugador += 10;

            }
        }
    }

}

