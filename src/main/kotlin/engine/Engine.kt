package engine

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import engine.editor.EditorCore

class Engine(type: EngineType) {

    companion object{
        lateinit var Instance: Engine;
    }

    val core: GameCore

    enum class EngineType {
        Game,
        Editor
    }

    init {
        Instance = this;

        core = when (type) {
            EngineType.Editor -> EditorCore()

            EngineType.Game -> GameCore()
        }
    }

    fun Run() {
        val config = Lwjgl3ApplicationConfiguration()
        Lwjgl3Application(core, config)
    }


}

