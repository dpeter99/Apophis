package engine

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import engine.modules.ModuleManager
import engine.editor.EditorModule
import engine.entitysystem.Scene
import game.TestScene
import ktx.app.KtxGame


open class GameCore : KtxGame<Scene>(clearScreen = false) {

    lateinit var fb: FrameBuffer;

    var screenwidth: Int = 0;
    var screenheight: Int = 0;

    init {

    }

    override fun create() {
        super.create()


        screenwidth = Gdx.graphics.width
        screenheight = Gdx.graphics.height

        ModuleManager.modules.add(ImGuiLayer());
        ModuleManager.modules.add(EditorModule());

        currentScreen = TestScene();

        ModuleManager.Init();

        fb = FrameBuffer(Pixmap.Format.RGBA8888, screenwidth, screenheight, false);

    }

    override fun render() {

        ModuleManager.Update();

        ModuleManager.PreFrame();

        fb.begin();

        Gdx.gl.glClearColor(1f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ModuleManager.Render();

        super.render()

        ModuleManager.LateRender();

        fb.end();

        ModuleManager.AfterFrameEnd();


    }

    override fun dispose() {
        super.dispose()
        ModuleManager.Dispose();
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        fb = FrameBuffer(Pixmap.Format.RGBA8888, screenwidth, screenheight, false);
    }
}