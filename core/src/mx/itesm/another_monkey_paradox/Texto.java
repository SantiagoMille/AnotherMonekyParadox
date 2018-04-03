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

    public Texto(float r, float g,float b){
        font = new BitmapFont(Gdx.files.internal("tutorial.fnt"));
    }

    public  void mostratMensaje(SpriteBatch batch, String mensaje, float x, float y,float r, float g,float b){
        glyph = new GlyphLayout(font,mensaje, new Color(r,g,b,1),1000f,1,true);
        //glyph.setText(font, mensaje);
        float anchoText = glyph.width;
        font.draw(batch,glyph, x-anchoText/2,y);
    }

}