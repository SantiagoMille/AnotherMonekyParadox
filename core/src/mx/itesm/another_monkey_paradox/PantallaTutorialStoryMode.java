package mx.itesm.another_monkey_paradox;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by santi on 2/13/2018.
 */

public class PantallaTutorialStoryMode extends PantallaTutorial {

    public PantallaTutorialStoryMode(Main main) {
        super(main);
    }

    @Override
    void crearTexto() {
        texto = new Texto();
        toWrite = "Story Mode:\n" +
                "\n" +
                "Astro esta en busca de cobrar venganza contra \n" +
                "el Dr. Timetravelov.\n" +
                "\n" +
                "Utiliza el joystick para moverte hacia adelante \n" +
                "y hacia atras.\n" +
                "Utiliza los botones para lanzar bananas.";
    }

    @Override
    protected void escribirTexto(SpriteBatch batch) {
        texto.mostratMensaje(batch,toWrite,ANCHO/2,ALTO-120);
    }
}
