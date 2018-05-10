package mx.itesm.another_monkey_paradox.Niveles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import mx.itesm.another_monkey_paradox.Main;
import mx.itesm.another_monkey_paradox.Objetos.Bala;
import mx.itesm.another_monkey_paradox.Objetos.Enemigo;
import mx.itesm.another_monkey_paradox.Objetos.Granada;
import mx.itesm.another_monkey_paradox.Objetos.Personaje;
import mx.itesm.another_monkey_paradox.Objetos.PowerUp;
import mx.itesm.another_monkey_paradox.Pantallas.EscenaAstroMuerto;
import mx.itesm.another_monkey_paradox.Pantallas.Pantalla;
import mx.itesm.another_monkey_paradox.Pantallas.PantallaMenu;
import mx.itesm.another_monkey_paradox.Utils.ScreenShake;
import mx.itesm.another_monkey_paradox.Utils.progressBar;

/**
 * Created by Fernando on 19/04/18.
 */

public abstract class NivelGenerico extends Pantalla implements InputProcessor{

    public NivelGenerico(Main main) {
        super(main);
    }

    protected boolean bossKilled = false;

    protected boolean powerUpVidaFlag = true;
    protected boolean powerUpGranadaFlag = true;

    protected boolean firstFilter=true;
    protected boolean secondFilter=true;

    //Sonido
    protected Sound gunSound = assetManager.get("pew.mp3");
    protected Sound boomSound = assetManager.get("boom.mp3");
    protected Sound hitSound = assetManager.get("hit.mp3");

    //Armas
    protected Array<Bala> listaBalas;
    protected Array<Granada> listaGranadas;
    protected Array<Bala> listaBalasBoss;

    //Fisica Granada
    protected float velocityY; // Velocidad de la granada

    //Direccion de Astro
    protected boolean isMovingRight = false;
    protected boolean isMovingLeft = false;

    protected boolean isFliped;
    protected float stateTime = 0;

    //Textura botones
    private Texture botonGranada = assetManager.get("BotonesDisparo/granada_icon.png");
    private Texture botonGranadaPressed = assetManager.get("BotonesDisparo/granada_icon_pressed.png");
    private Texture botonDisparo = assetManager.get("BotonesDisparo/bullet_icon.png");
    private Texture botonDisparoPressed = assetManager.get("BotonesDisparo/bullet_icon_pressed.png");;
    private Texture botonPausa = assetManager.get("pause-button.png");
    private Texture botonContinua = assetManager.get("PlayButton.png");
    private Texture botonHome = assetManager.get("boton Home.png");
    private Texture numGranadas = assetManager.get("BotonesDisparo/numGranadas.png");

    private Preferences prefs = Gdx.app.getPreferences("AnotherMonkeyPreferenceStory");
    public boolean music = prefs.getBoolean("music");

    //Botones TextureRegionDrawable
    private TextureRegionDrawable btnGranada = new TextureRegionDrawable(new TextureRegion(botonGranada));
    private TextureRegionDrawable btnGranadaPressed = new TextureRegionDrawable(new TextureRegion(botonGranadaPressed));
    private TextureRegionDrawable btnArma = new TextureRegionDrawable(new TextureRegion(botonDisparo));
    private TextureRegionDrawable btnArmaPressed = new TextureRegionDrawable(new TextureRegion(botonDisparoPressed));
    private TextureRegionDrawable btnPausa = new TextureRegionDrawable(new TextureRegion(botonPausa));
    private TextureRegionDrawable btnContinua = new TextureRegionDrawable(new TextureRegion(botonContinua));
    private TextureRegionDrawable btnHome = new TextureRegionDrawable(new TextureRegion(botonHome));
    protected Sprite granadasNum = new Sprite(numGranadas);

    //Controles del jugador
    protected final ImageButton granada = new ImageButton(btnGranada, btnGranadaPressed);
    protected final ImageButton arma = new ImageButton(btnArma, btnArmaPressed);
    protected final ImageButton pausa = new ImageButton(btnPausa);
    protected final ImageButton home = new ImageButton(btnHome);
    protected final ImageButton continua = new ImageButton(btnContinua);

    //Enemigos
    protected Array<Enemigo> listaEnemigos;
    protected Enemigo enemigo;
    protected float vidaEnemigo;
    protected int vidaBoss;

    //Textura de Astro
    protected Texture astroCaminata0 = assetManager.get("Astro/CAMINATA 4.png");
    protected Texture astroCaminata1 = assetManager.get("Astro/CAMINATA 2.png");
    protected Texture astroCaminata2 = assetManager.get("Astro/CAMINATA 3.png");
    protected Texture astroCaminata3 = assetManager.get("Astro/CAMINATA 1.png");

    //Textura de Boss
    protected Texture boss;
    protected Sprite bossSprite;

    //Disparos Boss
    protected int shootCounter = 0;

    //Item boss
    protected Texture itemBosss;
    protected Sprite itemBoss;

    //Personaje
    protected Personaje personaje = new Personaje(astroCaminata0, astroCaminata1, astroCaminata2, astroCaminata3);

    //Textura Armas
    protected Texture bananaDisparo = assetManager.get("banana.png");
    protected Texture bossDisparo = assetManager.get("disparo2.png");
    protected Texture bananaGranada = assetManager.get("granana.png");

    //pad
    protected Texture padBack = assetManager.get("Pad/padBack.png");
    protected Texture padKnob = assetManager.get("Pad/padKnob.png");

    protected Texture imgpowerUpGranada = assetManager.get("Items/GRANADAS.png");
    protected Texture imgpowerUpVida = assetManager.get("Items/VIDA.png");

    protected PowerUp powerUpVida = new PowerUp(imgpowerUpVida, -100, ALTO/4, true);
    protected PowerUp powerUpGranada = new PowerUp(imgpowerUpGranada, -600, ALTO/4, true);

    //Barra de carga balas
    protected progressBar Bar = new progressBar(200, 20);
    protected Texture imgBarraBala = assetManager.get("BarraBalas/barranegra.png");
    protected Texture imgBananaBarra = assetManager.get("BarraBalas/bananabarra.png");
    protected Sprite barraBala = new Sprite(imgBarraBala);
    protected Sprite bananaBarra = new Sprite(imgBananaBarra);

    //bananas Colision
    protected Bala banana1 = new Bala(bananaDisparo,false);
    protected Bala banana2 = new Bala(bananaDisparo,false);
    protected Bala banana3 = new Bala(bananaDisparo,false);
    protected Bala banana4 = new Bala(bananaDisparo, true);
    protected Bala banana5 = new Bala(bananaDisparo, true);
    protected Bala banana6 = new Bala(bananaDisparo, true);
    protected Boolean crashRight = false;
    protected Boolean crashLeft = false;

    //Vidas
    protected ArrayList<PowerUp> vidas = new ArrayList<PowerUp>();
    protected int contador = 0;

    // Puntaje y texto
    protected int puntosJugador = 0;
    protected BitmapFont font = new BitmapFont(Gdx.files.internal("tutorial.fnt"));
    protected GlyphLayout textoGly = new GlyphLayout(font,"Score");
    protected GlyphLayout pausaText = new GlyphLayout(font,"PAUSED",new Color(0,0,0,1),1000f,1,true);;

    //Granada y texto
    protected int maxGrandas;
    protected GlyphLayout textoGlyGran = new GlyphLayout(font,"Score");

    //Tiempo espera entre colision
    protected float timeSinceCollision = 0;

    //Vibracion de la pantalla
    protected ScreenShake screenShake = new ScreenShake(50f, 3000f);

    abstract public void pasarDeNivel();

    protected int verificarColisionPersonajeItemVida(int cuenta, PowerUp PUvida) {
        Rectangle rectItem = PUvida.getSprite().getBoundingRectangle();
        Rectangle rectPersonaje = new Rectangle(personaje.getX(), personaje.getY(), personaje.getWidth(), personaje.getHeight());
        if(rectItem.overlaps(rectPersonaje)){
            //PANTALLA DE VICTORIA PROVISIONAL
            for(PowerUp vida:vidas){
                if(!vida.isActiva()){
                    vida.setActiva(true);
                    break;
                }
            }
            PUvida.setX(-500);
        }
        return cuenta + 1;
    }

    protected int verificarColisionPersonajeItemVida(int cuenta) {
        Rectangle rectItem = powerUpVida.getSprite().getBoundingRectangle();
        Rectangle rectPersonaje = new Rectangle(personaje.getX(), personaje.getY(), personaje.getWidth(), personaje.getHeight());
        if(rectItem.overlaps(rectPersonaje)){
            //PANTALLA DE VICTORIA PROVISIONAL
            for(PowerUp vida:vidas){
                if(!vida.isActiva()){
                    vida.setActiva(true);
                    break;
                }
            }
            powerUpVida.setX(-500);
        }
        return cuenta + 1;
    }

    protected void verificarColisionPersonajeItemGranada() {
        Rectangle rectItem = powerUpGranada.getSprite().getBoundingRectangle();
        Rectangle rectPersonaje = new Rectangle(personaje.getX(), personaje.getY(), personaje.getWidth(), personaje.getHeight());
        if(rectItem.overlaps(rectPersonaje)){
            //PANTALLA DE VICTORIA PROVISIONAL
            maxGrandas++;
            powerUpGranada.setX(-500);
        }
    }

    protected void verificarVidaEnemigos() {
        for(int i = listaEnemigos.size-1;i>=0;i--){
            if(listaEnemigos.get(i).getVida() <= 0){
                listaEnemigos.removeIndex(i);
                puntosJugador += 10;

            }
        }
    }

    protected void verificarColisionBalaEnemigo(float dt, float diff, int difDelNivel) {
        if(diff<0.2) diff = 0.25f;
        Rectangle rectEnemigo;
        Bala bala;

        for(int j =listaBalas.size-1; j>=0;j--){
            bala = listaBalas.get(j);
            for(int i =listaEnemigos.size-1;i>=0;i--){

                enemigo = listaEnemigos.get(i);
                if(enemigo.right) {
                    rectEnemigo = new Rectangle(enemigo.getX() + 210, enemigo.getY(), enemigo.getWidth(), enemigo.getHeight());
                }else{
                    rectEnemigo = new Rectangle(enemigo.getX() - 210, enemigo.getY(), enemigo.getWidth(), enemigo.getHeight());
                }
                if(bala.getSprite().getBoundingRectangle().overlaps(rectEnemigo)){
                    try{
                        listaBalas.removeIndex(j);
                    }catch (Exception e){}
                    vidaEnemigo = enemigo.getVida() - (float)((12-difDelNivel)/diff);
                    enemigo.setVida(vidaEnemigo);
                    System.out.println(vidaEnemigo);
                }
                verificarVidaEnemigos();
            }
        }
    }

    protected void verificarColisionBalaRuso(float dt, float diff, int difDelNivel) {
        if(diff<0.2) diff = 0.3f;
        Rectangle rectEnemigo;
        Bala bala;

        for(int j =listaBalas.size-1; j>=0;j--){
            bala = listaBalas.get(j);
            for(int i =listaEnemigos.size-1;i>=0;i--){

                enemigo = listaEnemigos.get(i);
                if(enemigo.right) {
                    rectEnemigo = new Rectangle(enemigo.getX()+80, enemigo.getY(), enemigo.getWidth(), enemigo.getHeight());
                }else{
                    rectEnemigo = new Rectangle(enemigo.getX()-80, enemigo.getY(), enemigo.getWidth(), enemigo.getHeight());
                }
                if(bala.getSprite().getBoundingRectangle().overlaps(rectEnemigo)){
                    try {
                        listaBalas.removeIndex(j);

                    }catch (Exception e){

                    }

                    vidaEnemigo = enemigo.getVida() - (float) ((12-difDelNivel)/diff);
                    enemigo.setVida(vidaEnemigo);
                    System.out.println(vidaEnemigo);
                }
                verificarVidaEnemigos();
            }
        }
    }

    protected void verificarColisionBalaBala(float dt) {
        Bala balaBoss;
        Bala bala;

        for(int j =listaBalas.size-1; j>=0;j--){
            bala = listaBalas.get(j);
            for(int i =listaBalasBoss.size-1;i>=0;i--){

                balaBoss = listaBalasBoss.get(i);
                if(bala.getSprite().getBoundingRectangle().overlaps(balaBoss.getSprite().getBoundingRectangle())){
                    try{
                        listaBalas.removeIndex(j);
                        listaBalasBoss.removeIndex(i);
                    }catch (Exception e){}
                }
            }
        }
    }

    protected void verificarColisionGranadaEnemigo(float dt) {
        Rectangle rectEnemigo;
        Granada granada;
        for(int j =listaGranadas.size-1; j>=0;j--){
            granada = listaGranadas.get(j);
            for(int i =listaEnemigos.size-1;i>=0;i--){
                enemigo = listaEnemigos.get(i);
                if(enemigo.right) {
                    rectEnemigo = new Rectangle(enemigo.getX() + 210, enemigo.getY(), enemigo.getWidth(), enemigo.getHeight());
                }else{
                    rectEnemigo = new Rectangle(enemigo.getX() - 210, enemigo.getY(), enemigo.getWidth(), enemigo.getHeight());
                }
                if(granada.getSprite().getBoundingRectangle().overlaps(rectEnemigo)){
                    if(music){boomSound.play();}
                    try{
                        listaGranadas.removeIndex(j);
                    }catch (Exception e){}
                    vidaEnemigo = enemigo.getVida() - 100;
                    if(enemigo.right) {
                        explosionGranadaRight(rectEnemigo.getX(), rectEnemigo.getY() + enemigo.getHeight()/3);
                    } else {
                        explosionGranadaLeft(rectEnemigo.getX()+enemigo.getWidth(), rectEnemigo.getY() + enemigo.getHeight()/3);
                    }
                    enemigo.setVida(vidaEnemigo);
                    verificarVidaEnemigos();
                }
            }
        }
    }

    protected void verificarColisionGranadaRuso(float dt) {
        Rectangle rectEnemigo;
        Granada granada;
        for(int j =listaGranadas.size-1; j>=0;j--){
            granada = listaGranadas.get(j);
            for(int i =listaEnemigos.size-1;i>=0;i--){
                enemigo = listaEnemigos.get(i);
                if(enemigo.right) {
                    rectEnemigo = new Rectangle(enemigo.getX() + 84, enemigo.getY(), enemigo.getWidth(), enemigo.getHeight());
                }else{
                    rectEnemigo = new Rectangle(enemigo.getX() - 84, enemigo.getY(), enemigo.getWidth(), enemigo.getHeight());
                }
                if(granada.getSprite().getBoundingRectangle().overlaps(rectEnemigo)){
                    if(music){boomSound.play();}
                    try {
                        listaGranadas.removeIndex(j);
                    }catch (Exception e){ }
                    vidaEnemigo = enemigo.getVida() - 100;
                    if(enemigo.right) {
                        explosionGranadaRight(rectEnemigo.getX(), rectEnemigo.getY() + enemigo.getHeight()/3);
                    } else {
                        explosionGranadaLeft(rectEnemigo.getX()+enemigo.getWidth(), rectEnemigo.getY() + enemigo.getHeight()/3);
                    }
                    enemigo.setVida(vidaEnemigo);
                    verificarVidaEnemigos();
                }
            }
        }
    }


    protected void explosionGranadaRight(float x, float y){
        crashRight = true;
        banana1.set(x,y);
        banana2.set(x,y);
        banana3.set(x,y);
        banana4.set(x,y);
        banana5.set(x,y);
        banana6.set(x,y);
    }

    protected void explosionGranadaLeft(float x, float y){
        crashLeft = true;
        banana1.set(x,y);
        banana2.set(x,y);
        banana3.set(x,y);
        banana4.set(x,y);
        banana5.set(x,y);
        banana6.set(x,y);
    }

    protected boolean verificarColisionPersonajeEnemigo(float dt) {
        Enemigo x;
        Rectangle rectEnemigo;
        Rectangle rectPersonaje;
        boolean hit = false;

        for (int i = listaEnemigos.size - 1; i >= 0; i--) {
            x = listaEnemigos.get(i);
            rectEnemigo = new Rectangle(x.getX(), x.getY(), x.getWidth(), x.getHeight());
            if(!personaje.isRight()) {
                rectPersonaje = new Rectangle(personaje.getX()+25, personaje.getY(), personaje.getWidth(), personaje.getHeight());
            } else{
                rectPersonaje = new Rectangle(personaje.getX()-25, personaje.getY(), personaje.getWidth(), personaje.getHeight());
            }
            if (rectEnemigo.overlaps(rectPersonaje)) {
                if (x.getAnimacion().getKeyFrameIndex(dt) == 0){
                    for (int j = vidas.size() - 1; j >= 0; j--) {
                        if (contador >= 1) {
                            if (vidas.get(j).isActiva()) {
                                if(music){hitSound.play();}
                                vidas.get(j).setActiva(false);
                                hit = true;
                                contador = 0;
                            }
                        }
                    }
                }
            }
        }
        contador++;
        return hit;
    }

    protected boolean verificarColisionPersonajeAlien(float dt) {
        Enemigo x;
        Rectangle rectEnemigo;
        Rectangle rectPersonaje;
        boolean hit = false;

        for (int i = listaEnemigos.size - 1; i >= 0; i--) {
            x = listaEnemigos.get(i);
            if(x.isRight()){
                rectEnemigo = new Rectangle(x.getX()+130, x.getY(), x.getWidth(), x.getHeight());
            } else {
                rectEnemigo = new Rectangle(x.getX()-130, x.getY(), x.getWidth(), x.getHeight());
            }
            if(!personaje.isRight()) {
                rectPersonaje = new Rectangle(personaje.getX()+25, personaje.getY(), personaje.getWidth(), personaje.getHeight());
            } else{
                rectPersonaje = new Rectangle(personaje.getX()-25, personaje.getY(), personaje.getWidth(), personaje.getHeight());
            }
            if (rectEnemigo.overlaps(rectPersonaje)) {
                if (x.getAnimacion().getKeyFrameIndex(dt) == 0){
                    for (int j = vidas.size() - 1; j >= 0; j--) {
                        if (contador >= 1) {
                            if (vidas.get(j).isActiva()) {
                                if(music){hitSound.play();}
                                vidas.get(j).setActiva(false);
                                hit = true;
                                contador = 0;
                            }
                        }
                    }
                }
            }
        }
        contador++;
        return hit;
    }

    protected void verificarColisionPersonajeBalaBoss(float dt) {
        Bala bala;
        Rectangle rectPersonaje;

        for (int i = listaBalasBoss.size - 1; i >= 0; i--) {
            bala = listaBalasBoss.get(i);
            rectPersonaje = new Rectangle(personaje.getX(), personaje.getY(), personaje.getWidth(), personaje.getHeight());
            if (bala.getSprite().getBoundingRectangle().overlaps(rectPersonaje)) {
                for (int j = vidas.size() - 1; j >= 0; j--) {
                    if(contador>=50) {
                        if (vidas.get(j).isActiva()) {
                            if(music){
                                hitSound.play();
                                Gdx.input.vibrate(350);
                                screenShake.update(dt, camara);
                            }
                            vidas.get(j).setActiva(false);
                            contador = 0;
                        }
                    }
                }
            }
        }
        contador++;
    }

    protected void verificarVidaAstro(){
        int vidasFalse = 0;
        for (int i = vidas.size() - 1; i >= 0; i--){
            if(vidas.get(i).isActiva() == false){
                vidasFalse++;
            }
        }
        if(vidasFalse == vidas.size()){
            escribirScore(true);
            main.setScreen(new EscenaAstroMuerto(main, puntosJugador));
        }
    }

    protected void escribirScore(boolean isStoryMode) {
        Preferences prefs;
        if(isStoryMode) {
            prefs = Gdx.app.getPreferences("AnotherMonkeyPreferenceStory");
        }else{
            prefs = Gdx.app.getPreferences("AnotherMonkeyPreferenceSurvival");
        }
        String scoresString = prefs.getString("highscores", null);
        String[] scores;
        if(scoresString==null){
            scores = new String[0];
        }else {
            scores = scoresString.split(",");
        }
        int puntosActuales;
        int puntosSiguientes;
        if(scores.length>=6) {
            ArrayList<String> temp = new ArrayList<String>();
            temp.addAll(Arrays.asList(scores));
            for (int i = scores.length - 1; i > 0; i--) {
                puntosActuales = Integer.parseInt(scores[i].split(":")[1]);
                puntosSiguientes =0;
                try {
                    puntosSiguientes = Integer.parseInt(scores[i - 1].split(":")[1]);
                }catch (Exception e){
                    puntosActuales = 100000;
                }
                if (puntosJugador > puntosActuales && puntosJugador <= puntosSiguientes) {
                    Date date = new Date(TimeUtils.millis());
                    String dateString = new SimpleDateFormat("MM-dd-yyyy").format(date).toString();
                    temp.add(i,dateString + ":" + puntosJugador + "");
                    temp.remove(temp.size()-1);
                    //scores[i] = dateString + ":" + puntosJugador + "";
                }
            }
            StringBuilder sb = new StringBuilder();
            for(String s:temp){
                sb.append(s);
                sb.append(",");
            }
            prefs.putString("highscores",sb.toString());
            prefs.flush();
        }
        else{
            ArrayList<String> temp = new ArrayList<String>();
            temp.addAll(Arrays.asList(scores));
            /*StringBuilder sb = new StringBuilder();
            for(String s:scores){
                sb.append(s);
                sb.append(",");
            }
            Date date = new Date(TimeUtils.millis());
            String dateString = new SimpleDateFormat("MM-dd-yyyy").format(date).toString();
            sb.append(dateString + ":" + puntosJugador + "");
            prefs.putString("highscores",sb.toString());
            prefs.flush();*/

            for (int i = scores.length - 1; i > 0; i--) {
                puntosActuales = Integer.parseInt(scores[i].split(":")[1]);
                try {
                    puntosSiguientes = Integer.parseInt(scores[i - 1].split(":")[1]);
                }catch(Exception e){
                    puntosSiguientes = 100000;
                }
                if (puntosJugador > puntosActuales && puntosJugador < puntosSiguientes) {
                    Date date = new Date(TimeUtils.millis());
                    String dateString = new SimpleDateFormat("MM-dd-yyyy").format(date).toString();
                    temp.add(i,dateString + ":" + puntosJugador + "");
                    //scores[i] = dateString + ":" + puntosJugador + "";
                }
            }
            if(scores.length==0){

                temp.add("Sometime in the paradox:1000000");
            }
            if(scores.length==1){
                Date date = new Date(TimeUtils.millis());
                String dateString = new SimpleDateFormat("MM-dd-yyyy").format(date).toString();
                temp.add(dateString + ":" + puntosJugador + "");
            }else{
                Date date = new Date(TimeUtils.millis());
                String dateString = new SimpleDateFormat("MM-dd-yyyy").format(date).toString();
                temp.add(dateString + ":" + puntosJugador + "");
            }
            StringBuilder sb = new StringBuilder();
            for(String s:temp){
                sb.append(s);
                sb.append(",");
            }
            prefs.putString("highscores",sb.toString());
            prefs.flush();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            main.setScreen(new PantallaMenu(main));
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    /** The style for a {@link Touchpad}.
     * @author Josh Street */
    public static class TouchpadStyle {
        /** Stretched in both directions. Optional. */
        public Drawable background;

        /** Optional. */
        public Drawable knob;

        public TouchpadStyle () {
        }

        public TouchpadStyle (Drawable background, Drawable knob) {
            this.background = background;
            this.knob = knob;
        }

        public TouchpadStyle (Touchpad.TouchpadStyle style) {
            this.background = style.background;
            this.knob = style.knob;
        }
    }

}