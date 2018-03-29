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
        texto = new Texto(0,0,0);
        toWrite = "Astro is seeking revenge agains Dr. Timetravelov...\n\n" +
                "- Use the joystick to move forwards and \n  backwards. \n" +
                "- Use the blue button to shoot bananas.\n" +
                "- Use the red button to throw banana grenades.";
    }

    @Override
    protected void escribirTexto(SpriteBatch batch) {
        texto.mostratMensaje(batch,toWrite,ANCHO/2-50,ALTO-100,0f,0f,0f);
    }
}
