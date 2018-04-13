package mx.itesm.another_monkey_paradox;

/**
 * Created by Fernando on 10/04/18.
 */

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

public class progressBar extends ProgressBar{

    public progressBar(int width, int height) {
        super(0f, 1f, 0.01f, false, new ProgressBarStyle());
        getStyle().background = getColoredDrawable(width, height, Color.BLACK);
        getStyle().knob = getColoredDrawable(0, height, Color.CYAN);
        getStyle().knobBefore = getColoredDrawable(width, height, Color.CYAN);

        setWidth(width);
        setHeight(height);

        setAnimateDuration(0.0f);
        setValue(1f);

        setAnimateDuration(0.25f);
    }

    public static Drawable getColoredDrawable(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();

        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));

        pixmap.dispose();

        return drawable;
    }

}
