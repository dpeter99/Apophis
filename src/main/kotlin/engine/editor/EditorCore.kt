package engine.editor

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import engine.Engine
import engine.GameCore
import engine.ImGuiLayer
import engine.entitysystem.Scene
import engine.modules.ModuleManager
import game.TestScene

class EditorCore : GameCore() {

    companion object{
        lateinit var Instance: EditorCore;
    }

    init {
        Instance = this;
    }

    override fun create() {
        super.create()
    }

    override fun dispose() {
        super.dispose()
        ModuleManager.Dispose();
    }

    override fun render() {

        if(fb.width != screenwidth ||
            fb.height != screenheight
        ){
            fb = FrameBuffer(Pixmap.Format.RGBA8888, screenwidth, screenheight, false);
            (this.currentScreen as Scene).mainCamera?.Recalc();
        }

        super.render()
    }

    override fun resize(width: Int, height: Int) {

    }

    fun resizeFB(width:Int,height:Int){
        this.screenwidth = width;
        this.screenheight = height;


    }

}