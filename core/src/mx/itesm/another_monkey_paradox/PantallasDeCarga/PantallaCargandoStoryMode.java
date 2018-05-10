package mx.itesm.another_monkey_paradox.PantallasDeCarga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import mx.itesm.another_monkey_paradox.Main;
import mx.itesm.another_monkey_paradox.Niveles.PantallaJuego;
import mx.itesm.another_monkey_paradox.Niveles.PantallaJuego2;
import mx.itesm.another_monkey_paradox.Niveles.PantallaJuego3;
import mx.itesm.another_monkey_paradox.Niveles.PantallaJuego4;
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
    private int score, vidas, granadas;

    public PantallaCargandoStoryMode(Main main, int level, int score, int vidas, int granadas){
        super(main);
        this.granadas = granadas;
        this.vidas = vidas;
        this.level = level;
        this.score = score;
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
        }else if(level == 2){
            cargarRecursosLevel2();
        }else if(level == 3){
            cargarRecursosLevel3();
        }else if(level == 4){
            cargarRecursosLevel4();
        } else if(level==5){
            cargarRecursosSurvival();
        }
        //Ahora Inicia la carga de los recursos de la siguiente pantalla
    }

    private void cargarRecursosLevel2() {
        assetManager.load("disparo2.png",Texture.class);

        //Textura del nivel 1
        assetManager.load("Fondos/NIVEL 2.1.png", Texture.class);
        assetManager.load("Fondos/NIVEL 2.2.png", Texture.class);

        //Textura de Astro
        assetManager.load("Astro/CAMINATA 4.png", Texture.class);
        assetManager.load("Astro/CAMINATA 2.png", Texture.class);
        assetManager.load("Astro/CAMINATA 3.png", Texture.class);
        assetManager.load("Astro/CAMINATA 1.png", Texture.class);

        //Textura de cavernicola01
        assetManager.load("caballero1/3.png", Texture.class);
        assetManager.load("caballero1/4.png", Texture.class);
        assetManager.load("caballero1/2.png", Texture.class);
        assetManager.load("caballero1/1.png", Texture.class);

        //Textura de cavernicola02
        assetManager.load("caballero2/caballero 3.png", Texture.class);
        assetManager.load("caballero2/caballero 4.png", Texture.class);
        assetManager.load("caballero2/caballero 2.png", Texture.class);
        assetManager.load("caballero2/caballero 1.png", Texture.class);

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

        assetManager.load("Bosses/jefe_n2.png", Texture.class);
        assetManager.load("Bosses/item_boss2.png", Texture.class);
        // Se bloquea hasta cargar los recursos
        //assetManager.finishLoading();
    }

    private void cargarRecursosLevel3() {
        assetManager.load("disparo2.png",Texture.class);

        //Textura del nivel 1
        assetManager.load("Fondos/NIVEL 3.1.jpg", Texture.class);
        assetManager.load("Fondos/NIVEL 3.2.jpg", Texture.class);

        //Textura de Astro
        assetManager.load("Astro/CAMINATA 4.png", Texture.class);
        assetManager.load("Astro/CAMINATA 2.png", Texture.class);
        assetManager.load("Astro/CAMINATA 3.png", Texture.class);
        assetManager.load("Astro/CAMINATA 1.png", Texture.class);

        //Textura de ruso01
        assetManager.load("ruso1/1.png", Texture.class);
        assetManager.load("ruso1/2.png", Texture.class);
        assetManager.load("ruso1/3.png", Texture.class);
        assetManager.load("ruso1/4.png", Texture.class);

        //Textura de ruso02
        assetManager.load("ruso2/ruso 1.png", Texture.class);
        assetManager.load("ruso2/ruso 2.png", Texture.class);
        assetManager.load("ruso2/ruso 3.png", Texture.class);
        assetManager.load("ruso2/ruso 4.png", Texture.class);

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

        assetManager.load("Bosses/jefe_n3.png", Texture.class);
        assetManager.load("Bosses/item_boss3.png", Texture.class);
        // Se bloquea hasta cargar los recursos
        //assetManager.finishLoading();
    }

    private void cargarRecursosLevel4() {
        assetManager.load("disparo2.png",Texture.class);

        //Textura del nivel 1
        assetManager.load("Fondos/NIVEL 4.1.png", Texture.class);
        assetManager.load("Fondos/NIVEL 4.2.png", Texture.class);

        //Textura de Astro
        assetManager.load("Astro/CAMINATA 4.png", Texture.class);
        assetManager.load("Astro/CAMINATA 2.png", Texture.class);
        assetManager.load("Astro/CAMINATA 3.png", Texture.class);
        assetManager.load("Astro/CAMINATA 1.png", Texture.class);

        //Textura de Alien
        assetManager.load("Alien/ALIEN 1.png", Texture.class);
        assetManager.load("Alien/ALIEN 2.png", Texture.class);
        assetManager.load("Alien/ALIEN 3.png", Texture.class);
        assetManager.load("Alien/ALIEN 4.png", Texture.class);

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

    //Estos son los recursos de la pantalla siguiente (StoryMode)
    private void cargarRecursosLevel1() {

        assetManager.load("disparo2.png",Texture.class);

        //Textura del nivel 1
        assetManager.load("Fondos/NIVEL 1.1.png", Texture.class);
        assetManager.load("Fondos/NIVEL 1.2.png", Texture.class);

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
        assetManager.load("Fondos/NIVEL 1.1.png", Texture.class);
        assetManager.load("Fondos/NIVEL 1.2.png", Texture.class);

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

        //Textura de caballero 2
        assetManager.load("caballero2/caballero 3.png", Texture.class);
        assetManager.load("caballero2/caballero 2.png", Texture.class);
        assetManager.load("caballero2/caballero 4.png", Texture.class);
        assetManager.load("caballero2/caballero 1.png", Texture.class);

        //Textura de ruso 2
        assetManager.load("ruso2/ruso 1.png", Texture.class);
        assetManager.load("ruso2/ruso 2.png", Texture.class);
        assetManager.load("ruso2/ruso 3.png", Texture.class);
        assetManager.load("ruso2/ruso 4.png", Texture.class);

        //Textura de alien
        assetManager.load("Alien/ALIEN 1.png", Texture.class);
        assetManager.load("Alien/ALIEN 2.png", Texture.class);
        assetManager.load("Alien/ALIEN 3.png", Texture.class);
        assetManager.load("Alien/ALIEN 4.png", Texture.class);

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
                this.dispose();
            }else if(level == 2){
                main.setScreen(new PantallaJuego2(main,score, vidas, granadas));
                this.dispose();
            }else if(level == 3){
                main.setScreen(new PantallaJuego3(main,score, vidas, granadas));
                this.dispose();
            }else if(level == 4){
                main.setScreen(new PantallaJuego4(main,score, vidas, granadas));
                this.dispose();
            }else if(level==5){
                main.setScreen(new PantallaJuegoSurvival(main));
                this.dispose();
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
        //assetManager.unload("PantallaCarga/cargando.png");
    }
}
