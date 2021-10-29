
import com.badlogic.gdx.backends.lwjgl.LwjglApplication

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration




fun main() {

    var game = GameCore();

    val config = LwjglApplicationConfiguration()
    LwjglApplication(game, config)

}