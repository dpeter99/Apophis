
import com.badlogic.gdx.backends.lwjgl.LwjglApplication

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import engine.Engine
import engine.editor.EditorCore


fun main() {

    var game = Engine(Engine.EngineType.Editor);
    game.Run();
    //val config = Lwjgl3ApplicationConfiguration()
    //Lwjgl3Application(game, config)

}