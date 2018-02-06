package mx.itesm.another_monkey_paradox;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Prueba de Adrian Hola
//hola prros, prueba 2
//Camara prros
//Prueba Diego
public class Main extends Game {

	@Override
	public void create () {
		//Pone la Pantalla inicial
		setScreen(new PantallaSplash(this));
		//setScreen(new PantallaMenu(this));
	}
}
