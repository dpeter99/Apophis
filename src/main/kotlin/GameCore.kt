import com.badlogic.gdx.Game
import game.Scene
import ktx.app.KtxGame
import ktx.app.KtxScreen

class GameCore : KtxGame<Scene>() {

    override fun create() {
        super.create()

        currentScreen = Scene();

    }
}