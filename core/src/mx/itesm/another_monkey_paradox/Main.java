package mx.itesm.another_monkey_paradox;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

import mx.itesm.another_monkey_paradox.PantallasDeCarga.PantallaSplash;

public class Main extends Game {

    private final AssetManager assetManager = new AssetManager();

	@Override
	public void create () {
		//Pone la Pantalla inicial
		setScreen(new PantallaSplash(this));
	}

	public AssetManager getAssetManager(){
	    return assetManager;
    }

    @Override
    public void dispose(){
	    super.dispose();
	    assetManager.clear();
    }
}
