package mx.itesm.another_monkey_paradox.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import mx.itesm.another_monkey_paradox.Main;
import mx.itesm.another_monkey_paradox.Utils.Texto;

/**
 * Created by adrian on 2/9/2018.
 */

public class PantallaTutorial extends Pantalla implements Screen {

    //Escena
    private Stage stageMenu;

    //For Background
    Texture imgBackground;
    private Sprite spriteBackground;

    //background music
    private Music musicMenu = Gdx.audio.newMusic(Gdx.files.internal("loboloco.mp3"));

    //To write on scree
    public Texto texto;
    public Texto title;
    public String toWrite;

    public PantallaTutorial(Main main, String toWrite) {
        super(main);
        this.toWrite = toWrite;
    }

    @Override
    public void show() {
        crearCamara();
        crearMainView();
        batch = new SpriteBatch();
        musicMenu.setLooping(true);
        crearTexto();
        //musicMenu.play();
    }

    public void crearTexto(){
        texto = new Texto(0,0,0);
    }

    private void crearMainView() {
        stageMenu = new Stage(vista);

        imgBackground = new Texture("pantall_tutorial.png");
        spriteBackground = new Sprite(imgBackground);
        spriteBackground.setPosition(0, 0);
        //spriteBackground.setAlpha(0.7f);

        //Cuadro oscuro
        Pixmap pixmap = new Pixmap((int)(ANCHO), (int)(ALTO), Pixmap.Format.RGBA8888 );
        pixmap.setColor( 164/255f, 164/255f, 164/255f, 0.5f );
        pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
        Texture texturaRectangulo = new Texture( pixmap );
        pixmap.dispose();
        Image imgRectangulo = new Image(texturaRectangulo);
        imgRectangulo.setPosition(0,0);

        //Boton Return
        TextureRegionDrawable trdReturn = new TextureRegionDrawable(new TextureRegion(new Texture("go-back.png")));

        ImageButton btnReturn = new ImageButton(trdReturn);
        btnReturn.setPosition(30, ALTO-30-btnReturn.getHeight());

        //Click en boton Return
        btnReturn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Gdx.app.log("ClickListener","Si se clickeoooo");
                main.setScreen(new PantallaMenu(main));
            }
        });

        stageMenu.addActor(imgRectangulo);
        stageMenu.addActor(btnReturn);

        Gdx.input.setInputProcessor(stageMenu);
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
        Gdx.gl.glClearColor(.1f,.4f,.9f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        spriteBackground.draw(batch);
        escribirTexto(batch);
        batch.end();
        stageMenu.draw();

    }

    public void escribirTexto(SpriteBatch batch){
        texto.mostratMensaje(batch,toWrite,ANCHO/2-50,ALTO-100,0f,0f,0f);
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
