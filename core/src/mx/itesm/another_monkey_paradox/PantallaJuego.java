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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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

    //astro
    Texture imgAstro;
    Sprite astro;

    private Personaje personaje;

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
        moverFondo();
        personaje = new Personaje(new Texture("CAMINATA 1.png"),
                new Texture("CAMINATA 2.png"),
                new Texture("CAMINATA 3.png"),
                new Texture("CAMINATA 4.png"));
        fondo = new Fondo(new Texture("NIVEL 1.1.png"));
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(new ProcesadorEntrada());

    }

    private void moverFondo() {
        stageNivel = new Stage(vista);

        imgBackground = new Texture("NIVEL 1.1.png");
        spriteBackground = new Sprite(imgBackground);
        spriteBackground.setPosition(0, 0);

        //Objeto que dibuja texto
        texto = new Texto();

        //Astro
        imgAstro = new Texture("astro.png");
        astro = new Sprite(imgAstro);
        astro.setPosition(ANCHO/4-astro.getWidth()/2, ALTO/2-astro.getHeight()/2);

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

        //Boton izquierda
        TextureRegionDrawable btnLeft = new TextureRegionDrawable(new TextureRegion(new Texture("left_arrow.png")));
        TextureRegionDrawable btnLeftPressed = new TextureRegionDrawable(new TextureRegion(new Texture("left_arrow.png")));

        left = new ImageButton(btnLeft, btnLeftPressed);
        left.setSize(100,100);
        left.setPosition(75, ALTO/4-left.getHeight()/2 -80);

        //Boton derecha
        TextureRegionDrawable btnRight = new TextureRegionDrawable(new TextureRegion(new Texture("right_arrow.png")));
        TextureRegionDrawable btnRightPressed = new TextureRegionDrawable(new TextureRegion(new Texture("right_arrow.png")));

        right = new ImageButton(btnRight, btnRightPressed);
        right.setSize(100,100);
        right.setPosition(75 + right.getWidth()+ 50, ALTO/4-right.getHeight()/2 -80);

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

        //click en izquierda
        left.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                dispose();
                main.setScreen(new PantallaJuego(main));
            }
        });

        right.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                dispose();
                main.setScreen(new PantallaJuego(main));
            }
        });

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

        stageNivel.addActor(left);
        stageNivel.addActor(right);
        stageNivel.addActor(granada);
        stageNivel.addActor(arma);
        stageNivel.addActor(pausa);


    }

    private void crearCamara() {
        camara = new OrthographicCamera(ANCHO,ALTO);
        camara.position.set(ANCHO/2, ALTO/2,0);
        camara.update();
        vista = new StretchViewport(ANCHO,ALTO,camara);
    }

    @Override
    public void render(float delta) {
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
        batch.end();
        stageNivel.draw();
    }


    private void actualizarObjetos(float dt) {
        fondo.mover(-dt * 30);
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
            main.setScreen(new PantallaMenu(main));
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
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
