package mx.itesm.another_monkey_paradox;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Fernando on 20/02/18.
 */

class PantallaUtil extends Pantalla {

    private final Main juego;

    private Fondo fondo;
    private Personaje personaje;

    public PantallaUtil(Main juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        fondo = new Fondo(new Texture("FondoNivel1/NIVEL 1 PAN.png"));
        personaje = new Personaje(new Texture("CAMINATA 1.png"),
                new Texture("CAMINATA 2.png"),
                new Texture("CAMINATA 3.png"),
                new Texture("CAMINATA 4.png"));
    }

    @Override
    public void render(float delta) {
        //Actualizar
        actualizarObjetos(delta);
        //Dibujar
        borrarPantalla();

        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        fondo.render(batch);
        personaje.render(batch);
        batch.end();
    }

    private void actualizarObjetos(float dt) {
        fondo.mover(-dt * 30);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}