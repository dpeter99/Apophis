package engine

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import engine.editor.EditorCore
import engine.registry.ResourceName

class Bootstrap(type: EngineType) {

    companion object{
        lateinit var Instance: Bootstrap;

        fun resName(name: String): ResourceName {
            return ResourceName("engine", name);
        }
    }

    val core: EngineCore

    enum class EngineType {
        Game,
        Editor
    }

    init {
        Instance = this;

        core = when (type) {
            EngineType.Editor -> EditorCore()

            EngineType.Game -> EngineCore()
        }
    }

    fun Run() {
        val config = Lwjgl3ApplicationConfiguration()
        config.setWindowedMode(1000,650);
        config.useOpenGL3(true,4,0);
        config.enableGLDebugOutput(true,System.out);

        Lwjgl3Application(core, config)
    }


}

