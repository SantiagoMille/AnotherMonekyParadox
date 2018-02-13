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

class PantallaJuego implements Screen {

    private final Main main;

    public static final float ANCHO = 1280;
    public static final float ALTO = 720;

    //For Background
    Texture imgBackground;
    private Sprite spriteBackground;

    //Controles del jugador
    private ImageButton left;
    private ImageButton right;
    private ImageButton granada;
    private ImageButton arma;

    //Escena
    private Stage stageNivel;

    //Camara
    private OrthographicCamera camara;
    private Viewport vista;

    private SpriteBatch batch;

    //Iconos


    public PantallaJuego(Main main) {
        this.main = main;
    }

    @Override
    public void show() {
        crearCamara();
        moverFondo();
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(new ProcesadorEntrada());

    }

    private void moverFondo() {
        stageNivel = new Stage(vista);

        imgBackground = new Texture("NIVEL 1.1.png");
        spriteBackground = new Sprite(imgBackground);
        spriteBackground.setPosition(0, 0);

        //Boton izquierda
        TextureRegionDrawable btnLeft = new TextureRegionDrawable(new TextureRegion(new Texture("left_arrow.png")));
        TextureRegionDrawable btnLeftPressed = new TextureRegionDrawable(new TextureRegion(new Texture("left_arrow.png")));

        left = new ImageButton(btnLeft, btnLeftPressed);
        left.setPosition(ANCHO/4-left.getWidth(), ALTO/4-left.getHeight());

        //Boton derecha
        TextureRegionDrawable btnRight = new TextureRegionDrawable(new TextureRegion(new Texture("right_arrow.png")));
        TextureRegionDrawable btnRightPressed = new TextureRegionDrawable(new TextureRegion(new Texture("right_arrow.png")));

        right = new ImageButton(btnRight, btnRightPressed);
        right.setPosition(ANCHO/4-right.getWidth()/2+15, ALTO/4-right.getHeight()/2);

        //Boton granadas
        TextureRegionDrawable btnGranada = new TextureRegionDrawable(new TextureRegion(new Texture("granada_icon.png")));
        TextureRegionDrawable btnGranadaPressed = new TextureRegionDrawable(new TextureRegion(new Texture("granada_icon.png")));

        granada = new ImageButton(btnGranada, btnGranadaPressed);
        granada.setPosition(ANCHO*3/4-granada.getWidth()/2, ALTO/4-granada.getHeight()/2);

        //boton disparo
        TextureRegionDrawable btnArma = new TextureRegionDrawable(new TextureRegion(new Texture("bullet_icon.png")));
        TextureRegionDrawable btnArmaPressed = new TextureRegionDrawable(new TextureRegion(new Texture("bullet_icon.png")));

        arma = new ImageButton(btnArma, btnArmaPressed);
        arma.setPosition(ANCHO*3/4-arma.getWidth()/2 + 15, ALTO/4-arma.getHeight()/2);

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

        stageNivel.addActor(left);
        stageNivel.addActor(right);
        stageNivel.addActor(granada);
        stageNivel.addActor(arma);


    }

    private void crearCamara() {
        camara = new OrthographicCamera(ANCHO,ALTO);
        camara.position.set(ANCHO/2, ALTO/2,0);
        camara.update();
        vista = new StretchViewport(ANCHO,ALTO,camara);
    }

    @Override
    public void render(float delta) {
        //Usar v=d/t o en este caso d=v*t
        Gdx.gl.glClearColor(.3f,.6f,.3f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        spriteBackground.draw(batch);
        batch.end();
        stageNivel.draw();
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
