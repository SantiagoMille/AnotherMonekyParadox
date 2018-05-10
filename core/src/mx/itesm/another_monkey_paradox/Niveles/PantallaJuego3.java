package mx.itesm.another_monkey_paradox.Niveles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
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
 * Created by santi on 1/30/2018.
 */

public class PantallaJuego3 extends NivelGenerico implements Screen  {

    //For Background
    private Texture boss;
    private Sprite bossSprite;
    private Fondo fondo1, fondo2;

    //Enemigos
    //private Array<Enemigo> listaEnemigos;
    int vidaEnemigo = 100;
    int vidaBoss = 500;

    public boolean powerUpVidaFlag2 = true;

    private int cuentaVidas = 3;

    private float difficulty;

    //Escena
    private Stage stageNivel;

    protected PowerUp powerUpVida2 = new PowerUp(imgpowerUpVida, -100, ALTO/4, true);

    //Textura de Cavernicola 01
    private Texture ruso01Frame0;
    private Texture ruso01Frame1;
    private Texture ruso01Frame2;
    private Texture ruso01Frame3;

    //Textura de Cavernicola 02
    private Texture ruso02Frame0;
    private Texture ruso02Frame1;
    private Texture ruso02Frame2;
    private Texture ruso02Frame3;

    Rectangle rectBoss;

    //Textura Fondos de los niveles
    private Texture fondoNivel01;
    private Texture fondoNivel02;

    // PAUSA
    private PantallaJuego3.EscenaPausa3 escenaPausa;

    // Estados del juego
    private PantallaJuego.EstadoJuego estado;

    //private Enemigo enemigo;

    //PowerUps
    private Random random;
    Double randomX;
    Double randomX22;
    Double randomX2;

    public PantallaJuego3(Main main, int score, int cuentaVidas, int granadas) {
        super(main);
        this.maxGrandas = granadas;
        this.cuentaVidas = cuentaVidas;
        this.puntosJugador += score;
    }

    @Override
    public void pasarDeNivel() {
        main.setScreen(new EscenaAstroGanador(main, puntosJugador,4, cuentaVidas, maxGrandas));
    }

    @Override
    public void show() {

        crearCamara();
        crearMapa();

        Preferences prefs = Gdx.app.getPreferences("AnotherMonkeyPreferenceStory");

        difficulty = prefs.getFloat("Difficulty");

        fondo1 = new Fondo(fondoNivel01);
        fondo2 = new Fondo(fondoNivel02);
        fondo2.getImagenA().setPosition(fondo1.getImagenA().getWidth(),0);
        //personaje = new Personaje(astroCaminata0, astroCaminata1, astroCaminata2, astroCaminata3);

        //fondo = new Fondo(fondoNivel02);
        batch = new SpriteBatch();

        //Lista Enemigos
        listaEnemigos = new Array<Enemigo>();
        for(int i=0; i<8;i++){
            enemigo = new Enemigo(ruso01Frame0, ruso01Frame1, ruso01Frame2, ruso01Frame3,true,i);
            listaEnemigos.add(enemigo);
        }
        for(int i=0; i<8;i++){
            enemigo = new Enemigo(ruso01Frame0, ruso01Frame1, ruso01Frame2, ruso01Frame3,false,i);

            listaEnemigos.add(enemigo);
        }

        //Lista Balas
        listaBalas = new Array<Bala>();
        listaBalasBoss = new Array<Bala>();

        //Lista Granadas
        listaGranadas = new Array<Granada>();



        estado = PantallaJuego.EstadoJuego.JUGANDO;

        Gdx.input.setCatchBackKey(true);

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

        boss = new Texture("Bosses/jefe_n3.png");
        itemBosss = new Texture("Bosses/item_boss3.png");

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
        randomX22 = random.nextDouble()*4000;
        randomX2 = random.nextDouble()*4000;

        //Barra Bala
        //Bar = new progressBar(200, 20);
        Bar.setPosition((ANCHO/3)-25, ALTO*0.93f);
        //barraBala = new Sprite(imgBarraBala);
        barraBala.setPosition((ANCHO/3)-30, (ALTO*0.92f)+3);
        //bananaBarra = new Sprite(imgBananaBarra);

        bananaBarra.setPosition((ANCHO/3)-(imgBananaBarra.getWidth()/2)-33, (ALTO*0.90f));

        //Power Ups
        //powerUpVida = new PowerUp(imgpowerUpVida, -100, ALTO/4, true);
        //powerUpGranada = new PowerUp(imgpowerUpGranada, -600, ALTO/4, true);

        /*
        //Boton granadas
        TextureRegionDrawable btnGranada = new TextureRegionDrawable(new TextureRegion(botonGranada));
        TextureRegionDrawable btnGranadaPressed = new TextureRegionDrawable(new TextureRegion(botonGranadaPressed));
        granada = new ImageButton(btnGranada, btnGranadaPressed);
        */
        granada.setPosition(ANCHO*3/4-granada.getWidth()/2 + 25, -100);
        granada.addAction(Actions.moveTo(ANCHO*3/4-granada.getWidth()/2 + 25, ALTO/4-granada.getHeight()/2 - 80, 0.6f));

        /*
        //boton disparo
        TextureRegionDrawable btnArma = new TextureRegionDrawable(new TextureRegion(botonDisparo));
        TextureRegionDrawable btnArmaPressed = new TextureRegionDrawable(new TextureRegion(botonDisparoPressed));
        arma = new ImageButton(btnArma, btnArmaPressed);
        */
        arma.setPosition(ANCHO*3/4-arma.getWidth()/2 + arma.getWidth() + 55, -100);
        arma.addAction(Actions.moveTo(ANCHO*3/4-arma.getWidth()/2 + arma.getWidth() + 55, ALTO/4-arma.getHeight()/2 - 80, 0.6f));

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
                        nueva.set(personaje.getX() + 105, personaje.getY() + 63);
                        listaBalas.add(nueva);
                    } else {
                        if(music){gunSound.play();}
                        Bala nueva = new Bala(bananaDisparo, true);
                        nueva.set(personaje.getX(), personaje.getY() + 63);
                        listaBalas.add(nueva);
                    }
                }
                Bar.setValue(Bar.getValue()-0.18f);
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
                    escenaPausa = new PantallaJuego3.EscenaPausa3(vista,batch);
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
        //fondoNivel02 = assetManager.get("Fondos/NIVEL 2 PAN.png");

        fondoNivel01 = assetManager.get("Fondos/NIVEL 3.1.jpg");
        fondoNivel02 = assetManager.get("Fondos/NIVEL 3.2.jpg");

        ruso01Frame0 = assetManager.get("ruso1/3.png");
        ruso01Frame1 = assetManager.get("ruso1/4.png");
        ruso01Frame2 = assetManager.get("ruso1/2.png");
        ruso01Frame3 = assetManager.get("ruso1/1.png");

        ruso02Frame0 = assetManager.get("ruso2/ruso 3.png");
        ruso02Frame1 = assetManager.get("ruso2/ruso 4.png");
        ruso02Frame2 = assetManager.get("ruso2/ruso 2.png");
        ruso02Frame3 = assetManager.get("ruso2/ruso 1.png");
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

        if(!powerUpVidaFlag2 && isMovingRight){
            powerUpVida2.setX(powerUpVida2.getX()-(delta*80));
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
            for(int i=0; i<10;i++){
                enemigo = new Enemigo(ruso01Frame0, ruso01Frame1, ruso01Frame2, ruso01Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<10;i++){
                enemigo = new Enemigo(ruso02Frame0, ruso02Frame1, ruso02Frame2, ruso02Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo1.getImagenA().getX()<-1480&&fondo1.getImagenA().getX()>-1582&&secondFilter){
            secondFilter=false;
            firstFilter=true;
            for(int i=0; i<8;i++){
                enemigo = new Enemigo(ruso02Frame0, ruso02Frame1, ruso02Frame2, ruso02Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<8;i++){
                enemigo = new Enemigo(ruso01Frame0, ruso01Frame1, ruso01Frame2, ruso01Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo1.getImagenA().getX()<-2180&&fondo1.getImagenA().getX()>-2282&&firstFilter){
            secondFilter=true;
            firstFilter=false;
            for(int i=0; i<8;i++){
                enemigo = new Enemigo(ruso01Frame0, ruso01Frame1, ruso01Frame2, ruso01Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<8;i++){
                enemigo = new Enemigo(ruso02Frame0, ruso02Frame1, ruso02Frame2, ruso02Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo1.getImagenA().getX()<-2880&&fondo1.getImagenA().getX()>-2982&&secondFilter){
            secondFilter=false;
            firstFilter=true;
            for(int i=0; i<8;i++){
                enemigo = new Enemigo(ruso02Frame0, ruso02Frame1, ruso02Frame2, ruso02Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<6;i++){
                enemigo = new Enemigo(ruso01Frame0, ruso01Frame1, ruso01Frame2, ruso01Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
        }

        if(fondo1.getImagenA().getX()<-3580&&fondo1.getImagenA().getX()>-3600&&firstFilter){
            secondFilter=true;
            firstFilter=false;
            for(int i=0; i<10;i++){
                enemigo = new Enemigo(ruso01Frame0, ruso01Frame1, ruso01Frame2, ruso01Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
            for(int i=0; i<6;i++){
                enemigo = new Enemigo(ruso02Frame0, ruso02Frame1, ruso02Frame2, ruso02Frame3,true,i);
                listaEnemigos.add(enemigo);
            }
        }

        if (fondo1.getImagenA().getX()<-randomX &&powerUpVidaFlag){
            powerUpVida.setX(ANCHO*0.75f);
            powerUpVidaFlag=false;
        }

        if (fondo1.getImagenA().getX()<-randomX22 &&powerUpVidaFlag2){
            powerUpVida2.setX(ANCHO*0.75f);
            powerUpVidaFlag2=false;
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

            if(shootCounter>=40&&!bossKilled){
                shootCounter=0;
                Bala nueva = new Bala(bossDisparo,true);
                nueva.set(bossSprite.getX()-5, bossSprite.getY() + 43);
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
        font.draw(batch,textoGly, ANCHO/2 + 95,ALTO-15);

        textoGlyGran.setText(font, "Grenades: "+ maxGrandas);
        font.draw(batch,textoGlyGran, ANCHO/2 + 360,ALTO-15);

        //Texto Pausa
        if(estado == PantallaJuego.EstadoJuego.PAUSADO){
            font.draw(batch,pausaText, ANCHO/4-175,ALTO*13/20);
        }

        powerUpGranada.render(batch);
        powerUpVida.render(batch);
        powerUpVida2.render(batch);
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
                personaje.setX(personaje.getX()+(dt*80));
            }else {
                fondo1.mover(-dt * 79);
                fondo2.mover(-dt*79);
            }

        }else if(isMovingLeft&&!isMovingRight){
            if(personaje.getX()>(camara.position.x - ANCHO/2)){
                personaje.setX(personaje.getX()+(dt*-80));
            }
            if(fondo1.getImagenA().getX()>0) {
                fondo1.mover(dt * 20);
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

        verificarColisionBalaRuso(stateTime,difficulty, 3);
        verificarColisionBalaBala(stateTime);
        verificarColisionGranadaRuso(stateTime);
        verificarColisionBalaBoss(stateTime);
        verificarColisionGranadaBoss(stateTime);
        verificarColisionPersonajeBalaBoss(stateTime);
        verificarColisionPersonajeItemBoss();
        verificarColisionPersonajeItemGranada();
        cuentaVidas = verificarColisionPersonajeItemVida(cuentaVidas);
        cuentaVidas = verificarColisionPersonajeItemVida(cuentaVidas, powerUpVida2);
        verificarVidaAstro();

        timeSinceCollision += dt;
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
            main.setScreen(new EscenaAstroMuerto(main, puntosJugador, true));
        }
        cuentaVidas = 5 - vidasFalse;
    }

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


    }

    enum EstadoJuego {
        JUGANDO,
        PAUSADO
    }

    private class EscenaPausa3 extends Stage{

        // La escena que se muestra cuando está pausado
        public EscenaPausa3(Viewport vista, SpriteBatch batch) {

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
                    estado = PantallaJuego.EstadoJuego.JUGANDO;
                    Gdx.input.setInputProcessor(stageNivel);
                }
            });
            this.addActor(btnContinuar);
        }
    }
}