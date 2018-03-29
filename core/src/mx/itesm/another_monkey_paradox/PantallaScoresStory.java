package mx.itesm.another_monkey_paradox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

/**
 * Created by santi on 2/13/2018.
 */

public class PantallaScoresStory extends PantallaScores {
    public PantallaScoresStory(Main main) {
        super(main);
        survival = false;
    }

    @Override
    void crearMenu() {
        super.stageMenu = new Stage(vista);

        title = new Texto(1,1,1);

        imgBackground = new Texture("logros.png");
        spriteBackground = new Sprite(imgBackground);
        spriteBackground.setPosition(0, 0);
        //spriteBackground.setAlpha(0.7f);

        Skin skin = new Skin(Gdx.files.internal("skin/comic-ui.json"));

        Preferences prefs = Gdx.app.getPreferences("AnotherMonkeyPreferenceStory");
        String names = prefs.getString("names", null);
        if(names==null){
            // prefs.putString("names", "Astro");
            names = "Astro,";
        }
        String scores = prefs.getString("highscores", null);
        if(scores==null){
            //prefs.putString("highscores", "10000");
            scores = "10000,";
        }

        Table table = new Table(skin);
        table.defaults().pad(10f);
        table.setFillParent(true);
        table.setPosition(table.getX(),table.getY()+250);

        /**
         * Se hace el titulo de scores
         */
        //Label scoresTitle = new Label("HIGHSCORES STORY MODE", skin);
        //scoresTitle.setFontScale(4f,4f);
        //scoresTitle.setAlignment(Align.center);

        /**
         * Se crean las columnas con puntuajes
         */
        Label columnName;
        Label columnScore;

        String[] allScores = scores.split(",");
        String[] allNames = names.split(",");
        int i=0;

        //table.add(scoresTitle).colspan(2).fillX().height(150);
        table.row();
        for(String name:allNames){
            columnName=new Label(name+": ", skin);
            columnName.setFontScale(3f,3f);
            table.add(columnName);
            columnScore= new Label(allScores[i], skin);
            columnScore.setFontScale(3f,3f);
            table.add(columnScore);
            i++;
            table.row();
        }

        //Boton Return
        TextureRegionDrawable trdReturn = new TextureRegionDrawable(new TextureRegion(new Texture("go-back.png")));
        ImageButton btnReturn = new ImageButton(trdReturn);
        btnReturn.setPosition(30, ALTO-30-btnReturn.getHeight());

        //Click en boton Return
        btnReturn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                main.setScreen(new PantallaMenu(main));
            }
        });

        stageMenu.addActor(table);
        stageMenu.addActor(btnReturn);

        Gdx.input.setInputProcessor(stageMenu);
    }
}
