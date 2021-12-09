package engine.editor

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import engine.EngineCore
import engine.IEngineCore
import engine.ImGuiModule
import engine.entitysystem.Scene
import engine.modules.ModuleManager

class EditorCore : EngineCore(directDraw = false) {

    companion object{
        lateinit var Instance: EditorCore;
    }

    init {
        Instance = this;
    }

    override fun create() {
        super.create()

        ModuleManager.modules.add(ImGuiModule());
        ModuleManager.modules.add(EditorModule());
    }

    override fun dispose() {
        super.dispose()
    }

    override fun render() {



        super.render()
    }

    override fun resize(width: Int, height: Int) {

    }

    fun resizeFB(width:Int,height:Int){
        this.screenwidth = width;
        this.screenheight = height;

    }

}