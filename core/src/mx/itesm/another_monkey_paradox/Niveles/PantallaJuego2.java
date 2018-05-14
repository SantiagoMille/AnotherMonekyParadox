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

public class PantallaJuego2 extends NivelGenerico implements Screen  {

    //For Background
    private Texture boss;
    private Sprite bossSprite;

    Rectangle rectBoss;

    private Fondo fondo1, fondo2;

    //Enemigos
    //private Array<Enemigo> listaEnemigos;
    int vidaEnemigo = 100;
    int vidaBoss = 500;

    private float difficulty;

    //Escena
    private Stage stageNivel;

    //Textura de Cavernicola 01
    private Texture caballero01Frame0;
    private Texture caballero01Frame1;
    private Texture caballero01Frame2;
    private Texture caballero01Frame3;

    private int cuentaVidas = 3;


    //Textura de Cavernicola 02
    private Texture caballero02Frame0;
    private Texture caballero02Frame1;
    private Texture caballero02Frame2;
    private Texture caballero02Frame3;

    //Textura Fondos de los nivele
    private Texture fondoNivel02, fondoNivel01;

    // PAUSA
    private PantallaJuego2.EscenaPausa2 escenaPausa;

    // Estados del juego
    private PantallaJuego.EstadoJuego estado;

    private Music musicNivel2 = Gdx.audio.newMusic(Gdx.files.internal("nivel2.mp3"));

    //private Enemigo enemigo;

    //PowerUps
    private Random random;
    Double randomX;
    Double randomX2;

    public PantallaJuego2(Main main, int score, int cuentaVidas, int granadas) {
        super(main);
        this.maxGrandas = granadas;
        this.cuentaVidas = cuentaVidas;
        this.puntosJugador += score;
    }

    @Override
    public void pasarDeNivel() {
        musicNivel2.stop();
        musicNivel2.dispose();
        main.setScreen(new EscenaAstroGanador(main, puntosJugador,3, cuentaVidas, maxGrandas));
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
        fondo2.getImagenA().setPosition(fondo1.getImagenA().getWidth(),0);
        batch = new SpriteBatch();

        //Lista Enemigos
        listaEnemigos = new Array<Enemigo>();
        for(int i=0; i<6;i++){
            enemigo = new Enemigo(caballero02Frame0, caballero02Frame1, caballero02Frame2, caballero02Frame3,true,i);
            listaEnemigos.add(enemigo);
        }
        for(int i=0; i<6;i++){
            enemigo = new Enemigo(caballero02Frame0, caballero02Frame1, caballero02Frame2, caballero02Frame3,false,i);

            listaEnemigos.add(enemigo);
        }

        //Lista Balas
        listaBalas = new Array<Bala>();
        listaBalasBoss = new Array<Bala>();

        //Lista Granadas
        listaGranadas = new Array<Granada>();

        estado = PantallaJuego.EstadoJuego.JUGANDO;

        Gdx.input.setCatchBackKey(true);

        musicNivel2.setLooping(true);
        if(prefs.getBoolean("music", true)) {
            musicNivel2.play();
        }


        //Gdx.input.setInputProcessor(new ProcesadorEntrada());
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

        boss = new Texture("Bosses/jefe_n2.png");
        itemBosss = new Texture("Bosses/item_boss2.png");

        bossSprite = new Sprite(boss);
        bossSprite.setPosition(ANCHO,ALTO);
        itemBoss = new Sprite(itemBosss);
        itemBoss.setPosition(ANCHO*3/4,ALTO/4);

        //pausaText = new GlyphLayout(font,"PAUSED",new Color(0,0,0,1),1000f,1,true);

        for(int i=0;i<5;i++){
            if(i<cuentaVidas) {
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
        pad.setBounds(40,30,200,200);               // x,y - ancho,alto

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
                Bar.setValue(Bar.getValue()-0.25f);
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
                    escenaPausa = new PantallaJuego2.EscenaPausa2(vista,batch);
                }
                Gdx.input.setInputProcessor(escenaPausa);
                pause();
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
        fondoNivel01 = assetManager.get("Fondos/NIVEL 2.1.png");
        fondoNivel02 = assetManager.get("Fondos/NIVEL 2.2.png");

        caballero01Frame0 = assetManager.get("caballero1/3.png");
        caballero01Frame1 = assetManager.get("caballero1/4.png");
        caballero01Frame2 = assetManager.get("caballero1/2.png");
        caballero01Frame3 = assetManager.get("caballero1/1.png");

        caballero02Frame0 = assetManager.get("caballero2/caballero 3.png");
        caballero02Frame1 = assetManager.get("caballero2/caballero 4.png");
        caballero02Frame2 = assetManager.get("caballero2/caballero 2.png");
        caballero02Frame3 = assetManager.get("caballero2/caballero 1.png");

        /*
        gunSound = assetManager.get("pew.mp3");
        boomSound = assetManager.get("boom.mp3");
        hitSound = assetManager.get("hit.mp3");

        imgBarraBala = assetManager.get("BarraBalas/barranegra.png");
        imgBananaBarra = assetManager.get("BarraBalas/bananabarra.png");

        imgpowerUpGranada = assetManager.get("Items/GRANADAS.png");
        imgpowerUpVida = assetManager.get("Items/VIDA.png");

        astroCaminata0 = assetManager.get("Astro/CAMINATA 4.png");
        astroCaminata1 = assetManager.get("Astro/CAMINATA 2.png");
        astroCaminata2 = assetManager.get("Astro/CAMINATA 3.png");
        astroCaminata3 = assetManager.get("Astro/CAMINATA 1.png");

        botonGranada = assetManager.get("BotonesDisparo/granada_icon.png");
        botonDisparo = assetManager.get("BotonesDisparo/bullet_icon.png");
        botonDisparoPressed = assetManager.get("BotonesDisparo/bullet_icon_pressed.png");
        botonGranadaPressed = assetManager.get("BotonesDisparo/granada_icon_pressed.png");


        botonPausa = assetManager.get("pause-button.png");
        botonContinua = assetManager.get("PlayButton.png");
        botonHome = assetManager.get("boton Home.png");
        padBack = assetManager.get("Pad/padBack.png");
        padKnob = assetManager.get("Pad/padKnob.png");

        bananaDisparo = assetManager.get("banana.png");
        bossDisparo = assetManager.get("disparo2.png");
        bananaGranada = assetManager.get("granana.png");
        */

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

        if(fondo1.getImagenA().getX()<-780&&fondo1.getImagenA().getX()>-882&&firstFilter){
            firstFilter=false;
            for(int i=0; i<6;i++){
                enemigo = new Enemigo(caballero01Frame0, caballero01Frame1, caballero01Frame2, caballero01Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<6;i++){
                enemigo = new Enemigo(caballero01Frame0, caballero01Frame1, caballero01Frame2, caballero01Frame3,false,i);

                listaEnemigos.add(enemigo);
            }
        }

        if(fondo1.getImagenA().getX()<-1480&&fondo1.getImagenA().getX()>-1582&&secondFilter){
            secondFilter=false;
            firstFilter=true;
            for(int i=0; i<6;i++){
                enemigo = new Enemigo(caballero02Frame0, caballero02Frame1, caballero02Frame2, caballero02Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<6;i++){
                enemigo = new Enemigo(caballero02Frame0, caballero02Frame1, caballero02Frame2, caballero02Frame3,false,i);

                listaEnemigos.add(enemigo);
            }
        }

        if(fondo1.getImagenA().getX()<-2180&&fondo1.getImagenA().getX()>-2282&&firstFilter){
            secondFilter=true;
            firstFilter=false;
            for(int i=0; i<6;i++){
                enemigo = new Enemigo(caballero01Frame0, caballero01Frame1, caballero01Frame2, caballero01Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<6;i++){
                enemigo = new Enemigo(caballero01Frame0, caballero01Frame1, caballero01Frame2, caballero01Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo1.getImagenA().getX()<-2880&&fondo1.getImagenA().getX()>-2982&&secondFilter){
            secondFilter=false;
            firstFilter=true;
            for(int i=0; i<6;i++){
                enemigo = new Enemigo(caballero02Frame0, caballero02Frame1, caballero02Frame2, caballero02Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<6;i++){
                enemigo = new Enemigo(caballero02Frame0, caballero02Frame1, caballero02Frame2, caballero02Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo1.getImagenA().getX()<-3580&&fondo1.getImagenA().getX()>-3600&&firstFilter){
            secondFilter=true;
            firstFilter=false;
            for(int i=0; i<6;i++){
                enemigo = new Enemigo(caballero02Frame0, caballero02Frame1, caballero02Frame2, caballero02Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<6;i++){
                enemigo = new Enemigo(caballero02Frame0, caballero02Frame1, caballero02Frame2, caballero02Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        if (fondo1.getImagenA().getX()<-randomX &&powerUpVidaFlag){
            powerUpVida.setX(ANCHO*0.75f);
            powerUpVidaFlag=false;
        }

        if (fondo1.getImagenA().getX()<-randomX2&&powerUpGranadaFlag){
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

        for(Bala balaB: listaBalasBoss){
            if(!bossKilled){
                balaB.render(batch);
            }
        }

        personaje.render(batch, stateTime, isMovingRight, isMovingLeft);
        //Dibuja enemigos
        for(Enemigo e:listaEnemigos){
            e.render(batch);



            if(estado == PantallaJuego.EstadoJuego.JUGANDO&&e.right){

                if(personaje.getX()<camara.position.x){
                    e.setX(e.getX()+(-60*delta));
                }else{
                    e.setX(e.getX()+(-100*delta));
                }
            }else if(estado == PantallaJuego.EstadoJuego.JUGANDO&&!e.right){
                if(personaje.getX()<camara.position.x){
                    e.setX(e.getX()+(60*delta));
                }else{
                    e.setX(e.getX()+(20*delta));
                }

            }
        }

        if(fondo1.getImagenA().getX()<-3999){

            shootCounter++;

            if(shootCounter>=45&&!bossKilled){
                shootCounter=0;
                Bala nueva = new Bala(bossDisparo,true);
                nueva.set(bossSprite.getX(), bossSprite.getY() + 68);
                listaBalasBoss.add(nueva);
            }

            if(personaje.getX()>=camara.position.x&&isMovingRight&&!isMovingLeft) {
                float x = bossSprite.getX();
                bossSprite.setPosition(x - (delta * 78), ALTO/4);
            }

            if(vidaBoss<=0){
                itemBoss.setPosition(bossSprite.getX(),ALTO/4);
                itemBoss.draw(batch);
                rectBoss.setPosition(-100,ALTO+100);
                bossKilled=true;
            }else{
                bossSprite.draw(batch);
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
            }

        }else if(isMovingLeft&&!isMovingRight){
            if(personaje.getX()>(camara.position.x - ANCHO/2)){
                personaje.setX(personaje.getX()+(dt*-110));
            }
            if(fondo1.getImagenA().getX()>0) {
                fondo1.mover(dt * 20);
                fondo2.mover(dt * 20);
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

        i=0;
        for(Bala balaa:listaBalasBoss){
            if(balaa.getX()>camara.position.x+ANCHO/2||balaa.getX()<camara.position.x-ANCHO/2){
                listaBalasBoss.removeIndex(i);
            }
            balaa.mover(dt * 2);
            //balaa.getSprite().rotate(10);
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

        verificarColisionBalaEnemigo(stateTime, difficulty, 2);
        verificarColisionBalaBala(stateTime);
        verificarColisionGranadaEnemigo(stateTime);
        verificarColisionBalaBoss(stateTime);
        verificarColisionGranadaBoss(stateTime);
        verificarColisionPersonajeBalaBoss(stateTime);
        verificarColisionPersonajeItemBoss();
        verificarColisionPersonajeItemGranada();
        cuentaVidas = verificarColisionPersonajeItemVida(cuentaVidas);
        verificarVidaAstro();

        timeSinceCollision += dt;
        System.out.println("TimeSinceCollision: " + timeSinceCollision);
        if (timeSinceCollision > 1.8f) {
            boolean shake = verificarColisionPersonajeEnemigo(dt);
            if(shake){
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
            escribirScore(true);
            main.setScreen(new EscenaAstroMuerto(main, puntosJugador));
        }
        cuentaVidas = 5 - vidasFalse;
    }

    /*
    private void verificarColisionPersonajeItemBoss() {
        Rectangle rectItem = itemBoss.getBoundingRectangle();
        Rectangle rectPersonaje = new Rectangle(personaje.getX(), personaje.getY(), personaje.getWidth(), personaje.getHeight());
        if(rectItem.overlaps(rectPersonaje)){
            //PANTALLA DE VICTORIA PROVISIONAL
            main.setScreen(new EscenaAstroGanador(main, puntosJugador));
        }
    }
    */

    /*
    private void verificarColisionPersonajeItemVida() {
        Rectangle rectItem = powerUpVida.getSprite().getBoundingRectangle();
        Rectangle rectPersonaje = new Rectangle(personaje.getX(), personaje.getY(), personaje.getWidth(), personaje.getHeight());
        if(rectItem.overlaps(rectPersonaje)){
            //PANTALLA DE VICTORIA PROVISIONAL
            for(PowerUp vida:vidas){
                if(!vida.isActiva()){
                    vida.setActiva(true);
                    break;
                }
            }
            powerUpVida.setX(-500);
        }
    }
    */

    /*
    private void verificarColisionPersonajeItemGranada() {
        Rectangle rectItem = powerUpGranada.getSprite().getBoundingRectangle();
        Rectangle rectPersonaje = new Rectangle(personaje.getX(), personaje.getY(), personaje.getWidth(), personaje.getHeight());
        if(rectItem.overlaps(rectPersonaje)){
            //PANTALLA DE VICTORIA PROVISIONAL
            maxGrandas++;
            powerUpGranada.setX(-500);
        }
    }
    */

    /*
    private void verificarVidaEnemigos() {
        for(int i = listaEnemigos.size-1;i>=0;i--){
            if(listaEnemigos.get(i).getVida() <= 0){
                listaEnemigos.removeIndex(i);
                puntosJugador += 10;

            }
        }
    }
    */

    /*
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
                    vidaEnemigo = enemigo.getVida() - 12;
                    enemigo.setVida(vidaEnemigo);
                    System.out.println(vidaEnemigo);
                }
                verificarVidaEnemigos();
            }
        }
    }
    */

    /*
    private void verificarColisionBalaBala(float dt) {
        Bala balaBoss;
        Bala bala;

        for(int j =listaBalas.size-1; j>=0;j--){
            bala = listaBalas.get(j);
            for(int i =listaBalasBoss.size-1;i>=0;i--){

                balaBoss = listaBalasBoss.get(i);
                if(bala.getSprite().getBoundingRectangle().overlaps(balaBoss.getSprite().getBoundingRectangle())){
                    listaBalas.removeIndex(j);
                    listaBalasBoss.removeIndex(i);
                }
            }
        }
    }
    */


    private void verificarColisionBalaBoss(float dt) {
        Bala bala;
        for(int j =listaBalas.size-1; j>=0;j--){
            bala = listaBalas.get(j);
            rectBoss = bossSprite.getBoundingRectangle();
            if(bala.getSprite().getBoundingRectangle().overlaps(rectBoss)){
                vidaBoss-=25;
                if(!bossKilled) {
                    listaBalas.removeIndex(j);
                }
            }
        }
    }

    private void verificarColisionGranadaBoss(float dt) {
        Granada granada;
        for(int j =listaGranadas.size-1; j>=0;j--){
            granada = listaGranadas.get(j);
            rectBoss = bossSprite.getBoundingRectangle();
            if(granada.getSprite().getBoundingRectangle().overlaps(rectBoss)){
                vidaBoss-=100;
                if(!bossKilled) {
                    listaGranadas.removeIndex(j);
                }
            }
        }
    }

    protected void verificarColisionPersonajeItemBoss() {
        Rectangle rectItem = itemBoss.getBoundingRectangle();
        Rectangle rectPersonaje = new Rectangle(personaje.getX(), personaje.getY(), personaje.getWidth(), personaje.getHeight());
        if(rectItem.overlaps(rectPersonaje)){
            //PANTALLA DE VICTORIA PROVISIONAL
            pasarDeNivel();
        }
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width,height);
    }

    @Override
    public void pause() {
        estado = PantallaJuego.EstadoJuego.PAUSADO;
        musicNivel2.pause();
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
        musicNivel2.stop();
        musicNivel2.dispose();
        assetManager.dispose();
    }

    enum EstadoJuego {
        JUGANDO,
        PAUSADO
    }

    private class EscenaPausa2 extends Stage{

        // La escena que se muestra cuando está pausado
        public EscenaPausa2(Viewport vista, SpriteBatch batch) {

            super(vista);

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
                    musicNivel2.stop();
                    musicNivel2.dispose();
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
                    musicNivel2.play();
                    estado = PantallaJuego.EstadoJuego.JUGANDO;
                    Gdx.input.setInputProcessor(stageNivel);
                }
            });
            this.addActor(btnContinuar);
        }
    }
}

