package mx.itesm.another_monkey_paradox;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by santi on 2/13/2018.
 */

public class PantallaTutorialSurvivalMode extends PantallaTutorial {

    public PantallaTutorialSurvivalMode(Main main) {
        super(main);
    }

    @Override
    void crearTexto() {
        texto = new Texto(0.7f,0.7f,0.7f);
        toWrite = "Survival Mode:\n" +
                "\n" +
                "Sobrevive hasta que puedas, pelea contra \n" +
                "los minions del Dr. Timetravelov. " +
                "\n" +
                "Utiliza el joystick para moverte hacia adelante \n" +
                "y hacia atras.\n" +
                "Utiliza los botones para lanzar bananas.";


        toWrite = "Survive as long as you can, fight Dr. Timetravelov's \nminions.\n\n" +
                "- Use the joystick to move forwards and \n  backwards. \n" +
                "- Use the blue button to shoot bananas.\n" +
                "- Use the red button to throw banana grenades.";
    }

    @Override
    protected void escribirTexto(SpriteBatch batch) {
        texto.mostratMensaje(batch,toWrite,ANCHO/2,ALTO-230);
    }
}
