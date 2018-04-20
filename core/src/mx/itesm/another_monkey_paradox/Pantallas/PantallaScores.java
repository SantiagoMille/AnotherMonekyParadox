package mx.itesm.another_monkey_paradox.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.itesm.another_monkey_paradox.Main;
import mx.itesm.another_monkey_paradox.PantallasDeCarga.PantallaSplash;
import mx.itesm.another_monkey_paradox.Utils.Texto;

/**
 * Created by santi on 2/9/2018.
 */

public abstract class PantallaScores extends Pantalla implements Screen{

    public boolean survival;

    //Camara
    public OrthographicCamera camara;
    public Viewport vista;

    //Escena
    public Stage stageMenu;

    public Texto title;

    public SpriteBatch batch;

    //For Background
    public Texture imgBackground;
    public Sprite spriteBackground;

    public PantallaScores(Main main) {
        super(main);
    }

    @Override
    public void show() {
        crearCamara();
        crearMenu();
        batch = new SpriteBatch();
    }

    abstract void crearMenu();

    private void crearCamara() {
        camara = new OrthographicCamera(ANCHO,ALTO);
        camara.position.set(ANCHO/2, ALTO/2,0);
        camara.update();
        vista = new StretchViewport(ANCHO,ALTO,camara);
    }

    @Override
    public void render(float delta) {
        //Usar v=d/t o en este caso d=v*t
        Gdx.gl.glClearColor(127/255f,135/255f,160/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        spriteBackground.draw(batch);
        if(survival) {
            title.mostratMensaje(batch, "HIGHSCORES SURVIVAL MODE", ANCHO / 3-10, ALTO - 50, 1, 1, 1);
        }else{
            title.mostratMensaje(batch, "HIGHSCORES STORY MODE", ANCHO / 3-10, ALTO - 50, 1, 1, 1);
        }
        batch.end();
        stageMenu.draw();
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
        PantallaSplash.musicMenu.dispose();
    }


}
