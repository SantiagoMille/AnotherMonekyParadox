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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.*;

import java.util.ArrayList;

/**
 * Created by santi on 1/30/2018.
 */

class PantallaJuego extends Pantalla implements Screen  {

    private final Main main;

    public static final float ANCHO = 1280;
    public static final float ALTO = 720;

    private boolean firstFilter=true;

    //For Background
    Texture imgBackground;
    private Sprite spriteBackground;

    private Fondo fondo;

    //Sonido
    private Sound gunSound;
    private Sound boomSound;

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

    private float stateTime=0;

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

        estado = EstadoJuego.JUGANDO;

        //Gdx.input.setInputProcessor(new ProcesadorEntrada());

    }

    private void crearMapa() {

        cargarTexturas();

        stageNivel = new Stage(vista);

        //Objeto que dibuja texto
        font = new BitmapFont(Gdx.files.internal("tutorial.fnt"));
        textoGly = new GlyphLayout(font,"Score");
        textoGlyGran = new GlyphLayout(font,"Score");

        for(int i=0;i<3;i++){
            if(i<3) {
                vidas.add(new PowerUp(new Texture("vida.png"), camara.position.x + 10 - ANCHO / 2 + (75 * i), ALTO - 70,true));
            }else{
                vidas.add(new PowerUp(new Texture("vida.png"), camara.position.x + 10 - ANCHO / 2 + (75 * i), ALTO - 70, false));
            }
        }

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
                    //personaje.setX(personaje.getX()+3);
                    personaje.setRight(true);
                    isMovingRight=true;
                    isMovingLeft = false;
                } else if ( pad.getKnobPercentX() < -0.25 ) {   // Más de 20% IZQUIERDA
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


        botonGranada = assetManager.get("granada_icon.png");
        botonDisparo = assetManager.get("bullet_icon.png");
        botonPausa = assetManager.get("pause-button.png");
        botonContinua = assetManager.get("PlayButton.png");
        botonHome = assetManager.get("boton Home.png");
        padBack = assetManager.get("Pad/padBack.png");
        padKnob = assetManager.get("Pad/padKnob.png");

        bananaDisparo = assetManager.get("banana.png");
        bananaGranada = assetManager.get("granana.png");

        gunSound = assetManager.get("pew.mp3");
        boomSound = assetManager.get("boom.mp3");
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

        //Usar v=d/t o en este caso d=v*t
        Gdx.gl.glClearColor(.3f,.6f,.3f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        fondo.render(batch);


        if(fondo.getImagenA().getX()<-780&&fondo.getImagenA().getX()>-882&&firstFilter){
            firstFilter=false;
            for(int i=0; i<4;i++){
                enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<4;i++){
                enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo.getImagenA().getX()<-1480&&fondo.getImagenA().getX()>-1582&&firstFilter){
            firstFilter=false;
            for(int i=0; i<4;i++){
                enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<4;i++){
                enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo.getImagenA().getX()<-2180&&fondo.getImagenA().getX()>-2282&&firstFilter){
            firstFilter=false;
            for(int i=0; i<4;i++){
                enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<4;i++){
                enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo.getImagenA().getX()<-2880&&fondo.getImagenA().getX()>-2982&&firstFilter){
            firstFilter=false;
            for(int i=0; i<4;i++){
                enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<4;i++){
                enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo.getImagenA().getX()<-3580&&fondo.getImagenA().getX()>-2592&&firstFilter){
            firstFilter=false;
            for(int i=0; i<10;i++){
                enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<4;i++){
                enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        for(PowerUp e:vidas){
            if(e.isActiva()){
                e.render(batch);
            }
            //e.setX(e.getX()+(-30*delta));
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

        personaje.render(batch, stateTime, isMovingRight, isMovingLeft);
        //Dibuja enemigos
        for(Enemigo e:listaEnemigos){
            e.render(batch);
            if(estado == EstadoJuego.JUGANDO&&e.right){
                e.setX(e.getX()+(-50*delta));
            }else if(estado == EstadoJuego.JUGANDO&&!e.right){
                if(personaje.getX()<camara.position.x){
                    e.setX(e.getX()+(50*delta));
                }else{
                    e.setX(e.getX()+(5*delta));
                }

            }
        }

        for(Bala bala: listaBalas){
            bala.render(batch);
        }

        textoGly.setText(font, "Score: "+puntosJugador);
        font.draw(batch,textoGly, ANCHO/2 + 120,ALTO-15);

        textoGlyGran.setText(font, "Grenades: "+maxGrandas);
        font.draw(batch,textoGlyGran, ANCHO/2 + 340,ALTO-15);

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
                personaje.setX(personaje.getX()+(dt*75));
            }else {
                fondo.mover(-dt * 76);
            }

        }else if(isMovingLeft&&!isMovingRight){
            if(personaje.getX()>(camara.position.x - ANCHO/2)){
                personaje.setX(personaje.getX()+(dt*-75));
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
                    vidaEnemigo = enemigo.getVida() - 25;
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
                    enemigo.setVida(vidaEnemigo);
                    System.out.println(vidaEnemigo);
                }
                verificarVidaEnemigos();
            }
        }
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
            Preferences prefs = Gdx.app.getPreferences("AnotherMonkeyPreferenceStory");
            String scoresString = prefs.getString("highscores", null);
            String[] scores = scoresString.split(",");

            main.setScreen(new EscenaAstroMuerto(main));
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
            pixmap.setColor( 135/255f, 135/255f, 135/255f, 0.8f );
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
            btnSalir.setPosition(ANCHO*5.5f/10f, ALTO*3f/10f);
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
            btnContinuar.setPosition(ANCHO*3.5f/10f, ALTO*3f/10f);
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

