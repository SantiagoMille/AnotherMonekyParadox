package mx.itesm.another_monkey_paradox.PantallasDeCarga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import mx.itesm.another_monkey_paradox.Main;
import mx.itesm.another_monkey_paradox.Niveles.PantallaJuego;
import mx.itesm.another_monkey_paradox.Niveles.PantallaJuegoSurvival;
import mx.itesm.another_monkey_paradox.Pantallas.Pantalla;

/**
 * Created by Fernando on 18/03/18.
 */

public class PantallaCargandoStoryMode extends Pantalla implements Screen {

    //Texturas
    private Texture texturaCargando;
    private Sprite spriteCargando; // Para animarlo

    // Nivel
    private int level;

    public PantallaCargandoStoryMode(Main main, int level){
        super(main);
        this.level = level;
    }

    @Override
    public void show() {
        //Cargar recursos de esta pantalla
        texturaCargando = new Texture("PantallaCarga/cargando.png"); //Se bloquea, pero es una sola imagen
        spriteCargando = new Sprite(texturaCargando);
        spriteCargando.setPosition(ANCHO/2-spriteCargando.getWidth()/2,
                ALTO/2-spriteCargando.getHeight()/2);
        if(level == 1){
            cargarRecursosLevel1();
        }
        if(level==5){
            cargarRecursosSurvival();
        }
        //Ahora Inicia la carga de los recursos de la siguiente pantalla
    }

    //Estos son los recursos de la pantalla siguiente (StoryMode)
    private void cargarRecursosLevel1() {

        assetManager.load("disparo2.png",Texture.class);

        //Textura del nivel 1
        assetManager.load("FondoNivel1/NIVEL 1 PAN.png", Texture.class);

        //Textura de Astro
        assetManager.load("Astro/CAMINATA 4.png", Texture.class);
        assetManager.load("Astro/CAMINATA 2.png", Texture.class);
        assetManager.load("Astro/CAMINATA 3.png", Texture.class);
        assetManager.load("Astro/CAMINATA 1.png", Texture.class);

        //Textura de cavernicola01
        assetManager.load("cavernicola01/CM1 3.png", Texture.class);
        assetManager.load("cavernicola01/CM1 4.png", Texture.class);
        assetManager.load("cavernicola01/CM1 2.png", Texture.class);
        assetManager.load("cavernicola01/CM1 1.png", Texture.class);

        //Textura de cavernicola02
        assetManager.load("cavernicola02/CM2 3.png", Texture.class);
        assetManager.load("cavernicola02/CM2 4.png", Texture.class);
        assetManager.load("cavernicola02/CM2 2.png", Texture.class);
        assetManager.load("cavernicola02/CM2 1.png", Texture.class);

        //Textura de cavernicola03
        assetManager.load("cavernicola03/CM3 3.png", Texture.class);
        assetManager.load("cavernicola03/CM3 4.png", Texture.class);
        assetManager.load("cavernicola03/CM3 2.png", Texture.class);
        assetManager.load("cavernicola03/CM3 1.png", Texture.class);

        //Textura vida
        assetManager.load("vida.png", Texture.class);

        //Textura botones
        assetManager.load("BotonesDisparo/bullet_icon.png", Texture.class);
        assetManager.load("BotonesDisparo/bullet_icon_pressed.png", Texture.class);
        assetManager.load("BotonesDisparo/granada_icon.png", Texture.class);
        assetManager.load("BotonesDisparo/granada_icon_pressed.png", Texture.class);

        assetManager.load("pause-button.png", Texture.class);
        assetManager.load("Pad/padBack.png", Texture.class);
        assetManager.load("Pad/padKnob.png", Texture.class);
        assetManager.load("boton Home.png", Texture.class);
        assetManager.load("PlayButton.png", Texture.class);
        assetManager.load("PlayButton.png", Texture.class);

        //Textura armas
        assetManager.load("banana.png", Texture.class);
        assetManager.load("granana.png", Texture.class);

        //Textura PowerUps
        assetManager.load("Items/GRANADAS.png", Texture.class);
        assetManager.load("Items/VIDA.png", Texture.class);

        assetManager.load("ok-button.png", Texture.class);

        //Textura barra de carga de balas
        assetManager.load("BarraBalas/bananabarra.png", Texture.class);
        assetManager.load("BarraBalas/barranegra.png", Texture.class);

        //Sonidos
        assetManager.load("pew.mp3", Sound.class);
        assetManager.load("boom.mp3", Sound.class);
        assetManager.load("hit.mp3", Sound.class);
        // Se bloquea hasta cargar los recursos
        //assetManager.finishLoading();
    }

    //Estos son los recursos de la pantalla siguiente (SurvivalMode)
    private void cargarRecursosSurvival() {

        assetManager.load("disparo2.png",Texture.class);

        //Textura del nivel 1
        assetManager.load("FondoNivel1/NIVEL 1 PAN.png", Texture.class);

        //Textura de Astro
        assetManager.load("Astro/CAMINATA 4.png", Texture.class);
        assetManager.load("Astro/CAMINATA 2.png", Texture.class);
        assetManager.load("Astro/CAMINATA 3.png", Texture.class);
        assetManager.load("Astro/CAMINATA 1.png", Texture.class);

        //Textura de cavernicola01
        assetManager.load("cavernicola01/CM1 3.png", Texture.class);
        assetManager.load("cavernicola01/CM1 4.png", Texture.class);
        assetManager.load("cavernicola01/CM1 2.png", Texture.class);
        assetManager.load("cavernicola01/CM1 1.png", Texture.class);

        //Textura de cavernicola02
        assetManager.load("cavernicola02/CM2 3.png", Texture.class);
        assetManager.load("cavernicola02/CM2 4.png", Texture.class);
        assetManager.load("cavernicola02/CM2 2.png", Texture.class);
        assetManager.load("cavernicola02/CM2 1.png", Texture.class);

        //Textura de cavernicola03
        assetManager.load("cavernicola03/CM3 3.png", Texture.class);
        assetManager.load("cavernicola03/CM3 4.png", Texture.class);
        assetManager.load("cavernicola03/CM3 2.png", Texture.class);
        assetManager.load("cavernicola03/CM3 1.png", Texture.class);

        //Textura vida
        assetManager.load("vida.png", Texture.class);

        //Textura botones
        assetManager.load("BotonesDisparo/bullet_icon.png", Texture.class);
        assetManager.load("BotonesDisparo/bullet_icon_pressed.png", Texture.class);
        assetManager.load("BotonesDisparo/granada_icon.png", Texture.class);
        assetManager.load("BotonesDisparo/granada_icon_pressed.png", Texture.class);

        assetManager.load("pause-button.png", Texture.class);
        assetManager.load("Pad/padBack.png", Texture.class);
        assetManager.load("Pad/padKnob.png", Texture.class);
        assetManager.load("boton Home.png", Texture.class);
        assetManager.load("PlayButton.png", Texture.class);
        assetManager.load("PlayButton.png", Texture.class);

        //Textura armas
        assetManager.load("banana.png", Texture.class);
        assetManager.load("granana.png", Texture.class);

        //Textura PowerUps
        assetManager.load("Items/GRANADAS.png", Texture.class);
        assetManager.load("Items/VIDA.png", Texture.class);

        assetManager.load("ok-button.png", Texture.class);

        //Textura barra de carga de balas
        assetManager.load("BarraBalas/bananabarra.png", Texture.class);
        assetManager.load("BarraBalas/barranegra.png", Texture.class);

        //Sonidos
        assetManager.load("pew.mp3", Sound.class);
        assetManager.load("boom.mp3", Sound.class);
        assetManager.load("hit.mp3", Sound.class);
        // Se bloquea hasta cargar los recursos
        //assetManager.finishLoading();
    }

    @Override
    public void render(float delta) {
        // Revisar cómo va la carga de los recursos sigiuentes
        actualizarCarga();

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteCargando.setRotation(spriteCargando.getRotation()+10); //Animacion

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        spriteCargando.draw(batch);
        batch.end();
    }

    //Revisa cómo va la carga de assets
    private void actualizarCarga(){
        if (assetManager.update()){
            if(level==1){
                main.setScreen(new PantallaJuego(main));//regresa true si ya terminó la carga
            }
            if(level==5){
                main.setScreen(new PantallaJuegoSurvival(main));
            }
        } else {
            //Aún no termina, preguntar cómo va
            float avance = assetManager.getProgress();
        }
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
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
        texturaCargando.dispose();
        assetManager.unload("PantallaCarga/cargando.png");
    }
}
