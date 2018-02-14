package mx.itesm.another_monkey_paradox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

/**
 * Created by santi on 2/13/2018.
 */

public class PantallaScoresSurvival extends PantallaScores {

    public PantallaScoresSurvival(Main main) {
        super(main);
    }

    @Override
    void crearMenu() {
        super.stageMenu = new Stage(vista);

        imgBackground = new Texture("space.png");
        spriteBackground = new Sprite(imgBackground);
        spriteBackground.setPosition(0, 0);
        spriteBackground.setAlpha(0.7f);

        Skin skin = new Skin(Gdx.files.internal("skin/comic-ui.json"));

        Preferences prefs = Gdx.app.getPreferences("AnotherMonkeyPreferenceSurvival");
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
        Label scoresTitle = new Label("HIGHSCORES SURVIVAL MODE", skin);
        scoresTitle.setFontScale(4f,4f);
        scoresTitle.setAlignment(Align.center);

        /**
         * Se crean las columnas con puntuajes
         */
        Label columnName;
        Label columnScore;

        String[] allScores = scores.split(",");
        String[] allNames = names.split(",");
        int i=0;

        table.add(scoresTitle).colspan(2).fillX().height(150);
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

        stageMenu.addActor(table);

        Gdx.input.setInputProcessor(stageMenu);
    }

}
