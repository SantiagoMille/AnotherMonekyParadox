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
        texto = new Texto();
        toWrite = "Survival Mode:\n" +
                "\n" +
                "Sobrevive hasta que puedas, pelea contra \n" +
                "los minions del Dr. Timetravelov. " +
                "\n" +
                "Utiliza las flechas para moverte hacia adelante \n" +
                "y hacia atras.\n" +
                "Utiliza los botones para lanzar bananas.";
    }

    @Override
    protected void escribirTexto(SpriteBatch batch) {
        texto.mostratMensaje(batch,toWrite,ANCHO/2,ALTO-120);
    }
}
