package mx.itesm.another_monkey_paradox.Niveles;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import mx.itesm.another_monkey_paradox.Main;
import mx.itesm.another_monkey_paradox.Objetos.Bala;
import mx.itesm.another_monkey_paradox.Objetos.Granada;
import mx.itesm.another_monkey_paradox.Pantallas.Pantalla;

/**
 * Created by Fernando on 19/04/18.
 */

public abstract class nivelGenerico extends Pantalla {

    public nivelGenerico(Main main) {
        super(main);
    }


    protected boolean bossKilled = false;

    protected boolean powerUpVidaFlag = true;
    protected boolean powerUpGranadaFlag = true;

    protected boolean firstFilter=true;
    protected boolean secondFilter=true;

    /*
    //For Background
    protected Texture boss;
    protected Sprite bossSprite;

    //Item boss
    protected Texture itemBosss;
    protected Sprite itemBoss;

    private Fondo fondo;
    */

    //Sonido
    protected Sound gunSound = assetManager.get("pew.mp3");
    protected Sound boomSound = assetManager.get("boom.mp3");
    protected Sound hitSound = assetManager.get("hit.mp3");

    //Armas
    protected Array<Bala> listaBalas;
    protected Array<Granada> listaGranadas;
    protected Array<Bala> listaBalasBoss;

    //Fisica Granada
    protected float velocityY;     // Velocidad de la granada

    /*
    //Textura Fondos de los niveles
    private Texture fondoNivel01;
    */
    //Escena

    //Textura botones
    protected Texture botonGranada = assetManager.get("BotonesDisparo/granada_icon.png");
    protected Texture botonGranadaPressed = assetManager.get("BotonesDisparo/granada_icon_pressed.png");
    protected Texture botonDisparo = assetManager.get("BotonesDisparo/bullet_icon.png");
    protected Texture botonDisparoPressed = assetManager.get("BotonesDisparo/bullet_icon_pressed.png");;
    protected Texture botonPausa = assetManager.get("pause-button.png");
    protected Texture botonContinua = assetManager.get("PlayButton.png");
    protected Texture botonHome = assetManager.get("boton Home.png");

    //Botones TextureRegionDrawable
    protected TextureRegionDrawable btnGranada = new TextureRegionDrawable(new TextureRegion(botonGranada));
    protected TextureRegionDrawable btnGranadaPressed = new TextureRegionDrawable(new TextureRegion(botonGranadaPressed));
    protected TextureRegionDrawable btnArma = new TextureRegionDrawable(new TextureRegion(botonDisparo));
    protected TextureRegionDrawable btnArmaPressed = new TextureRegionDrawable(new TextureRegion(botonDisparoPressed));
    protected TextureRegionDrawable btnPausa = new TextureRegionDrawable(new TextureRegion(botonPausa));
    protected TextureRegionDrawable btnContinua = new TextureRegionDrawable(new TextureRegion(botonContinua));
    protected TextureRegionDrawable btnHome = new TextureRegionDrawable(new TextureRegion(botonHome));

    //Controles del jugador
    protected final ImageButton granada = new ImageButton(btnGranada, btnGranadaPressed);
    protected final ImageButton arma = new ImageButton(btnArma, btnArmaPressed);
    protected final ImageButton pausa = new ImageButton(btnPausa);
    protected final ImageButton home = new ImageButton(btnHome);
    protected final ImageButton continua = new ImageButton(btnContinua);

    //Textura de Astro
    protected Texture astroCaminata0 = assetManager.get("Astro/CAMINATA 4.png");
    protected Texture astroCaminata1 = assetManager.get("Astro/CAMINATA 2.png");
    protected Texture astroCaminata2 = assetManager.get("Astro/CAMINATA 3.png");
    protected Texture astroCaminata3 = assetManager.get("Astro/CAMINATA 1.png");

    /*
    //pad
    private Texture padBack = assetManager.get("Pad/padBack.png");
    private Texture padKnob = assetManager.get("Pad/padKnob.png");
    Skin skin = new Skin(); // Texturas para el pad
    skin.add("fondo", padBack);
    skin.add("boton", padKnob);

    Touchpad.TouchpadStyle estilo = new Touchpad.TouchpadStyle();
    estilo.background = skin.getDrawable("fondo");
    estilo.knob = skin.getDrawable("boton");

    // Crea el pad
    Touchpad pad = new Touchpad(64,estilo);     // Radio, estilo
    */


    /*
    protected float stateTime = 0;

    protected boolean isMovingRight = false;
    protected boolean isMovingLeft = false;

    protected boolean isFliped;

    protected Personaje personaje;

    //Enemigos
    protected Array<Enemigo> listaEnemigos;

    //Barra de carga balas
    protected progressBar Bar;
    protected Texture imgBarraBala;
    protected Texture imgBananaBarra;
    protected Sprite barraBala;
    protected Sprite bananaBarra;


    //Vidas
    protected ArrayList<PowerUp> vidas = new ArrayList<PowerUp>();
    protected int contador = 0;

    //Disparos Boss
    protected int shootCounter =0;

    //Escena
    protected Stage stageNivel;

    // Puntaje y texto
    protected int puntosJugador = 0;
    protected BitmapFont font;
    public GlyphLayout textoGly;


    public GlyphLayout pausaText;

    //Granada y texto
    protected int maxGrandas = 5;
    public GlyphLayout textoGlyGran;

    //Textura de Cavernicola 01
    protected Texture canervicola01Frame0;
    protected Texture canervicola01Frame1;
    protected Texture canervicola01Frame2;
    private Texture canervicola01Frame3;

    //Textura de Cavernicola 02
    private Texture canervicola02Frame0;
    private Texture canervicola02Frame1;
    private Texture canervicola02Frame2;
    private Texture canervicola02Frame3;

    //Textura de Cavernicola 03
    private Texture canervicola03Frame0;
    private Texture canervicola03Frame1;
    private Texture canervicola03Frame2;
    private Texture canervicola03Frame3;


    // PAUSA
    private EscenaPausa escenaPausa;

    // Estados del juego
    private PantallaJuego.EstadoJuego estado;

    //Textura Armas
    private Texture bananaDisparo;
    private Texture bossDisparo;
    private Texture bananaGranada;

    private Enemigo enemigo;

    //bananas Colision
    private Bala banana1;
    private Bala banana2;
    private Bala banana3;
    private Bala banana4;
    private Bala banana5;
    private Bala banana6;
    private Boolean crashRight = false;
    private Boolean crashLeft = false;

    //PowerUps
    private Random random;
    private Texture imgpowerUpGranada;
    private Texture imgpowerUpVida;
    private PowerUp powerUpGranada;
    private PowerUp powerUpVida;
    private ArrayList<PowerUp> listaVidasExtra = new ArrayList<PowerUp>();
    private ArrayList<PowerUp> listaGranadasExtra = new ArrayList<PowerUp>();
    private Double randomX;
    private Double randomX2;

    Skin skin = new Skin(); // Texturas para el pad
        skin.add("fondo", padBack);
        skin.add("boton", padKnob);

    Touchpad.TouchpadStyle estilo = new Touchpad.TouchpadStyle();
    estilo.background = skin.getDrawable("fondo");
    estilo.knob = skin.getDrawable("boton");

    // Crea el pad
    Touchpad pad = new Touchpad(64,estilo);     // Radio, estilo
        pad.setBounds(-100,20,220,220); // x,y - ancho,alto
        pad.addAction(Actions.moveTo(35,25, 0.6f));


    protected class EscenaPausa extends Stage {

        // La escena que se muestra cuando está pausado
        public EscenaPausa(Viewport vista, SpriteBatch batch) {

            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int)(ANCHO*0.5f), (int)(ALTO*0.45f), Pixmap.Format.RGBA8888 );
            //pixmap.setColor( 135/255f, 135/255f, 135/255f, 0.8f );
            pixmap.setColor( 0f, 0f, 0f, 0.35f );
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(ANCHO*0.5f - imgRectangulo.getWidth()*0.5f, ALTO*2.75f/10f);
            this.addActor(imgRectangulo);


            // Salir
            Texture texturaBtnSalir = new Texture("boton Home.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setSize(155, 155);
            btnSalir.setPosition(ANCHO*5.4f/10f, ALTO*3f/10f);
            btnSalir.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    main.setScreen(new PantallaMenu(main));
                }
            });
            this.addActor(btnSalir);

            // Continuar
            Texture texturaBtnContinuar = new Texture("PlayButton.png");
            TextureRegionDrawable trdContinuar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnContinuar));
            ImageButton btnContinuar = new ImageButton(trdContinuar);
            btnContinuar.setSize(155, 155);
            btnContinuar.setPosition(ANCHO*3.4f/10f, ALTO*3f/10f);
            btnContinuar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    estado = PantallaJuego.EstadoJuego.JUGANDO;
                    Gdx.input.setInputProcessor(stageNivel);
                }
            });
            this.addActor(btnContinuar);
        }
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void pause() {
        //implementar
    }

    @Override
    public void resume() {
        //implementar
    }

    @Override
    public void dispose() {

    }
    */

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
