package engine

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import engine.editor.EditorCore
import engine.eventbus.AppEvent
import engine.eventbus.SyncEventBus
import engine.registry.ResourceName

class Engine(type: EngineType) {

    companion object{
        lateinit var Instance: Engine;

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
        Lwjgl3Application(core, config)
    }


}

