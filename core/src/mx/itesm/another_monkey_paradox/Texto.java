package mx.itesm.another_monkey_paradox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by santi on 2/13/2018.
 */

public class Texto {
    private BitmapFont font;
    public GlyphLayout glyph;

    public Texto(){
        font = new BitmapFont(Gdx.files.internal("tutorial.fnt"));
        glyph = new GlyphLayout();
    }

    public  void mostratMensaje(SpriteBatch batch, String mensaje, float x, float y){
        if(!mensaje.equals("")){
            glyph.setText(font,mensaje);
        }
        float anchoText = glyph.width;
        font.draw(batch,glyph, x-anchoText/2,y);
    }

    public void setText(String mensaje){
        glyph.setText(font,mensaje);
    }
}