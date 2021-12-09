package engine

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.math.Matrix4
import engine.editor.EditorCore
import engine.entitysystem.Scene
import engine.eventbus.AppEvent
import engine.eventbus.SyncEventBus
import engine.modules.ModuleManager
import game.GameRegistries
import game.TestScene
import ktx.app.KtxGame


open class EngineCore(private val directDraw:Boolean = true) : KtxGame<Scene>(clearScreen = false), IEngineCore {

    companion object{
        lateinit var Instance: EngineCore;
    }

    init {
        Instance = this;
    }


    lateinit var fb: FrameBuffer;

    override var screenwidth: Int = 0;
    override var screenheight: Int = 0;

    var firstUpdate: Boolean = true;

    private lateinit var batch: SpriteBatch;

    override fun create() {
        super.create()

        screenwidth = Gdx.graphics.width
        screenheight = Gdx.graphics.height

        currentScreen = TestScene();

        fb = FrameBuffer(Pixmap.Format.RGBA8888, screenwidth, screenheight, false);
        batch = SpriteBatch();
    }

    override fun Init(){
        ModuleManager.Init();
        GameRegistries.Init();

        SyncEventBus.MAIN.post(AppEvent.RegistryCreation);

        SyncEventBus.MAIN.post(AppEvent.Register);
    }

    override fun render() {
        if(firstUpdate){
            Init();
            firstUpdate = false;
        }

        if(fb.width != screenwidth ||
            fb.height != screenheight
        ){
            fb = FrameBuffer(Pixmap.Format.RGBA8888, screenwidth, screenheight, true);
            (this.currentScreen as Scene).mainCamera?.Recalc();
        }

        Time.DeltaTime = Gdx.graphics.getDeltaTime();

        ModuleManager.Update();

        ModuleManager.PreFrame();

        fb.begin();


        ModuleManager.Render();

        super.render()

        ModuleManager.LateRender();


        fb.end();

        ModuleManager.AfterFrameEnd();

        if(directDraw){
            batch.begin();
            batch.projectionMatrix.setToOrtho2D(0f, 0f, this.screenwidth.toFloat(), this.screenheight.toFloat());
            batch.draw(fb.colorBufferTexture, 0f, 0f, this.screenwidth.toFloat(), this.screenheight.toFloat(), 0f, 0f, 1f, 1f)
            batch.end();
        }
    }

    override fun dispose() {
        super.dispose()
        ModuleManager.Dispose();
    }

    override fun resize(width: Int, height: Int) {
        //super.resize(width, height)
        //fb = FrameBuffer(Pixmap.Format.RGBA8888, screenwidth, screenheight, false);
        this.screenwidth = width;
        this.screenheight = height;
    }
}