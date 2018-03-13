package mx.itesm.another_monkey_paradox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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
    Texture imgVida;
    Sprite vida1, vida2, vida3;

    //Escena
    private Stage stageNivel;

    //Camara
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;

    //
    private int puntosJugador = 0;
    private Texto texto;


    public PantallaJuego(Main main) {
        this.main = main;
    }

    @Override
    public void show() {
        crearCamara();
        crearMapa();
        personaje = new Personaje(new Texture("CAMINATA 4.png"),
                    new Texture("CAMINATA 2.png"),
                    new Texture("CAMINATA 3.png"),
                    new Texture("CAMINATA 1.png"));

        fondo = new Fondo(new Texture("FondoNivel1/NIVEL 1 PAN.png"));
        batch = new SpriteBatch();
        //Gdx.input.setInputProcessor(new ProcesadorEntrada());

        //Lista Enemigos
        listaEnemigos = new Array<Enemigo>();
        Enemigo enemigo = new Enemigo(new Texture("cavernicola01/CM1 3.png"),
                                        new Texture("cavernicola01/CM1 4.png"),
                                        new Texture("cavernicola01/CM1 2.png"),
                                        new Texture("cavernicola01/CM1 1.png"));

        listaEnemigos.add(enemigo);

    }

    private void crearMapa() {
        stageNivel = new Stage(vista);

        //Objeto que dibuja texto
        texto = new Texto();

        //Vidas
        imgVida = new Texture("vida.png");
        vida1 = new Sprite(imgVida);
        vida1.setSize(70,70);
        vida1.setPosition(20, 680 - vida1.getHeight()/2);

        imgVida = new Texture("vida.png");
        vida2 = new Sprite(imgVida);
        vida2.setSize(70,70);
        vida2.setPosition(100, 680 - vida1.getHeight()/2);

        imgVida = new Texture("vida.png");
        vida3 = new Sprite(imgVida);
        vida3.setSize(70,70);
        vida3.setPosition(180, 680 - vida1.getHeight()/2);

        //Boton granadas
        TextureRegionDrawable btnGranada = new TextureRegionDrawable(new TextureRegion(new Texture("granada_icon.png")));
        TextureRegionDrawable btnGranadaPressed = new TextureRegionDrawable(new TextureRegion(new Texture("granada_icon.png")));

        granada = new ImageButton(btnGranada, btnGranadaPressed);
        granada.setSize(135, 135);
        granada.setPosition(ANCHO*3/4-granada.getWidth()/2 + 25, ALTO/4-granada.getHeight()/2 - 80);

        //boton disparo
        TextureRegionDrawable btnArma = new TextureRegionDrawable(new TextureRegion(new Texture("bullet_icon.png")));
        TextureRegionDrawable btnArmaPressed = new TextureRegionDrawable(new TextureRegion(new Texture("bullet_icon.png")));

        arma = new ImageButton(btnArma, btnArmaPressed);
        arma.setSize(135, 135);
        arma.setPosition(ANCHO*3/4-arma.getWidth()/2 + arma.getWidth() + 55, ALTO/4-arma.getHeight()/2 - 80);

        //boton pausa
        TextureRegionDrawable btnPausa = new TextureRegionDrawable(new TextureRegion(new Texture("pause-button.png")));
        TextureRegionDrawable btnPausaPressed = new TextureRegionDrawable(new TextureRegion(new Texture("pause-button.png")));

        pausa = new ImageButton(btnPausa, btnPausaPressed);
        pausa.setSize(55, 55);
        pausa.setPosition(ANCHO/2-pausa.getWidth()/2, 680 - pausa.getHeight()/2);

        Skin skin = new Skin(); // Texturas para el pad
        skin.add("fondo", new Texture("Pad/padBack.png"));
        skin.add("boton", new Texture("Pad/padKnob.png"));

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

        personaje.render(batch);

        //Dibuja enemigos
        for(Enemigo e:listaEnemigos){
            e.render(batch);
            e.setX(e.getX()+(-30*delta));
        }

        batch.end();
        stageNivel.draw();

        TextureRegion currentFrame = (TextureRegion) personaje.getAnimacion().getKeyFrame(stateTime,true);

        if(personaje.isRight()&&currentFrame.isFlipX()){
            currentFrame.flip(true,false);
        } else if(!personaje.isRight()&&!currentFrame.isFlipX()){
            currentFrame.flip(true,false);
        }else {

        }
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

    }

}
