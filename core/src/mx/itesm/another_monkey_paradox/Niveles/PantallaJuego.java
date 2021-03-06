package mx.itesm.another_monkey_paradox.Niveles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Random;

import mx.itesm.another_monkey_paradox.Main;
import mx.itesm.another_monkey_paradox.Objetos.Bala;
import mx.itesm.another_monkey_paradox.Objetos.Enemigo;
import mx.itesm.another_monkey_paradox.Objetos.Granada;
import mx.itesm.another_monkey_paradox.Objetos.PowerUp;
import mx.itesm.another_monkey_paradox.Pantallas.EscenaAstroGanador;
import mx.itesm.another_monkey_paradox.Pantallas.EscenaAstroMuerto;
import mx.itesm.another_monkey_paradox.Pantallas.Pantalla;
import mx.itesm.another_monkey_paradox.Pantallas.PantallaMenu;
import mx.itesm.another_monkey_paradox.Utils.Fondo;

/**
 * Created by santi on 1/30/2018.
 */

public class PantallaJuego extends NivelGenerico implements Screen  {

    //For Background
    private Texture boss;
    private Sprite bossSprite;
    private Fondo fondo1, fondo2;

    Rectangle rectBoss;

    private int cuentaVidas = 3;

    //Enemigos
    //private Array<Enemigo> listaEnemigos;
    int vidaEnemigo = 100;
    int vidaBoss = 500;

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

    //Textura Fondos de los niveles
    private Texture fondoNivel01;
    private Texture fondoNivel02;

    // PAUSA
    private EscenaPausa escenaPausa;

    // Estados del juego
    private EstadoJuego estado;

    private Music musicNivel1 = Gdx.audio.newMusic(Gdx.files.internal("nivel1.mp3"));

    //private Enemigo enemigo;

    //PowerUps
    private Random random;
    Double randomX;
    Double randomX2;

    public PantallaJuego(Main main) {
        super(main);
        this.maxGrandas = 5;
    }

    @Override
    public void pasarDeNivel() {
        musicNivel1.stop();
        musicNivel1.dispose();
        main.setScreen(new EscenaAstroGanador(main, puntosJugador,2, cuentaVidas, maxGrandas));
    }

    @Override
    public void show() {

        crearCamara();
        crearMapa();

        //personaje = new Personaje(astroCaminata0, astroCaminata1, astroCaminata2, astroCaminata3);

        fondo1 = new Fondo(fondoNivel01);
        fondo2 = new Fondo(fondoNivel02);
        fondo2.getImagenA().setPosition(fondo1.getImagenA().getWidth(),0);
        batch = new SpriteBatch();

        Preferences prefs = Gdx.app.getPreferences("AnotherMonkeyPreferenceStory");

        difficulty = prefs.getFloat("Difficulty");

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
        listaBalasBoss = new Array<Bala>();

        //Lista Granadas
        listaGranadas = new Array<Granada>();

        estado = EstadoJuego.JUGANDO;

        musicNivel1.setLooping(true);
        if(prefs.getBoolean("music",true)) {
            musicNivel1.play();
        }

        Gdx.input.setCatchBackKey(true);

        //Gdx.input.setInputProcessor(new ProcesadorEntrada());

    }

    private void crearMapa() {

        cargarTexturas();

        stageNivel = new Stage(vista);

        //para sacar número random donde se crean los powerups
        random = new Random();

        //Objeto que dibuja texto
        //font = new BitmapFont(Gdx.files.internal("tutorial.fnt"));
        //textoGly = new GlyphLayout(font,"Score");
        //textoGlyGran = new GlyphLayout(font,"Score");

        boss = new Texture("boss_stand.png");
        itemBosss = new Texture("item_boss.png");

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
                estado = EstadoJuego.PAUSADO;
                //main.setScreen((Screen) new EscenaPausa(vista,batch));
                if(escenaPausa == null){
                    escenaPausa = new EscenaPausa(vista);
                }
                Gdx.input.setInputProcessor(escenaPausa);
                //dispose();
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
        fondoNivel01 = assetManager.get("Fondos/NIVEL 1.1.png");
        fondoNivel02 = assetManager.get("Fondos/NIVEL 1.2.png");

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

        if(estado != EstadoJuego.PAUSADO) {
            actualizarObjetos(delta, stateTime);
            System.out.println(delta);

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
            for(int i=0; i<4;i++){
                enemigo = new Enemigo(canervicola01Frame0, canervicola01Frame1, canervicola01Frame2, canervicola01Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<4;i++){
                enemigo = new Enemigo(canervicola02Frame1, canervicola02Frame1, canervicola02Frame2, canervicola02Frame3,false,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo1.getImagenA().getX()<-1480&&fondo1.getImagenA().getX()>-1582&&secondFilter){
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

        if(fondo1.getImagenA().getX()<-2180&&fondo1.getImagenA().getX()>-2282&&firstFilter){
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

        if(fondo1.getImagenA().getX()<-2880&&fondo1.getImagenA().getX()>-2982&&secondFilter){
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

        if(fondo1.getImagenA().getX()<-3580&&fondo1.getImagenA().getX()>-3600&&firstFilter){
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



            if(estado == EstadoJuego.JUGANDO&&e.right){

                if(personaje.getX()<camara.position.x){
                    e.setX(e.getX()+(-60*delta));
                }else{
                    e.setX(e.getX()+(-100*delta));
                }
            }else if(estado == EstadoJuego.JUGANDO&&!e.right){
                if(personaje.getX()<camara.position.x){
                    e.setX(e.getX()+(60*delta));
                }else{
                    e.setX(e.getX()+(20*delta));
                }

            }
        }

        if(fondo1.getImagenA().getX()<-3999){

            shootCounter++;

            if(shootCounter>=50&&!bossKilled){
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
        if(estado == EstadoJuego.PAUSADO){
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
        if (estado==EstadoJuego.PAUSADO) {
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
                personaje.setX(personaje.getX()+(dt*110));//antes 80
            }else {
                fondo1.mover(-dt * 109);//antes 79
                fondo2.mover(-dt*109);
            }//

        }else if(isMovingLeft&&!isMovingRight){
            if(personaje.getX()>(camara.position.x - ANCHO/2)){
                personaje.setX(personaje.getX()+(dt*-110));//80
            }
            if(fondo1.getImagenA().getX()>0) {
                fondo1.mover(dt * 20);//sin mover aun
                fondo2.mover(dt*20);
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

        verificarColisionBalaEnemigo(stateTime, difficulty, 0);
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
        if (timeSinceCollision > 2f) {
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





    /*
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
    */

    /*
    private void explosionGranadaRight(float x, float y){
        crashRight = true;
        banana1.set(x,y);
        banana2.set(x,y);
        banana3.set(x,y);
        banana4.set(x,y);
        banana5.set(x,y);
        banana6.set(x,y);
    }
    */

    /*
    private void explosionGranadaLeft(float x, float y){
        crashLeft = true;
        banana1.set(x,y);
        banana2.set(x,y);
        banana3.set(x,y);
        banana4.set(x,y);
        banana5.set(x,y);
        banana6.set(x,y);
    }
    */

    /*
    private void verificarColisionPersonajeEnemigo(float dt) {
        Enemigo x;
        Rectangle rectEnemigo;
        Rectangle rectPersonaje;

        for (int i = listaEnemigos.size - 1; i >= 0; i--) {
            x = listaEnemigos.get(i);
            rectEnemigo = new Rectangle(x.getX(), x.getY(), x.getWidth(), x.getHeight());
            if(!personaje.isRight()) {
                rectPersonaje = new Rectangle(personaje.getX()+50, personaje.getY(), personaje.getWidth(), personaje.getHeight());
            } else{
                rectPersonaje = new Rectangle(personaje.getX()-50, personaje.getY(), personaje.getWidth(), personaje.getHeight());
            }
            if (rectEnemigo.overlaps(rectPersonaje)) {
                if (x.getAnimacion().getKeyFrameIndex(dt) == 0){
                    for (int j = vidas.size() - 1; j >= 0; j--) {
                        if (contador >= 55) {
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
        }
        contador++;
    }
    */

    /*
    private void verificarColisionPersonajeBalaBoss(float dt) {
        Bala bala;
        Rectangle rectPersonaje;

        for (int i = listaBalasBoss.size - 1; i >= 0; i--) {
            bala = listaBalasBoss.get(i);
            rectPersonaje = new Rectangle(personaje.getX(), personaje.getY(), personaje.getWidth(), personaje.getHeight());
            if (bala.getSprite().getBoundingRectangle().overlaps(rectPersonaje)) {
                for (int j = vidas.size() - 1; j >= 0; j--) {
                    if(contador>=50) {
                        if (vidas.get(j).isActiva()) {
                            hitSound.play();
                            vidas.get(j).setActiva(false);
                            contador = 0;
                        }
                    }
                }
            }
        }
        contador++;
    }
    */

    /*
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
    */

    @Override
    public void resize(int width, int height) {
        vista.update(width,height);
    }

    @Override
    public void pause() {
        estado = EstadoJuego.PAUSADO;
        musicNivel1.pause();
    }

    @Override
    public void resume() {
        estado = EstadoJuego.JUGANDO;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        musicNivel1.stop();
        musicNivel1.dispose();
        assetManager.dispose();

    }

    enum EstadoJuego {
        JUGANDO,
        PAUSADO
    }

    private class EscenaPausa extends Stage{

        // La escena que se muestra cuando está pausado
        public EscenaPausa(Viewport vista) {

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
                    musicNivel1.stop();
                    musicNivel1.dispose();
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
                    musicNivel1.play();
                    estado = EstadoJuego.JUGANDO;
                    Gdx.input.setInputProcessor(stageNivel);
                }
            });
            this.addActor(btnContinuar);
        }
    }
}

