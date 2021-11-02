import engine.entities.Scene
import game.TestScene
import ktx.app.KtxGame

class GameCore : KtxGame<Scene>() {

    override fun create() {
        super.create()

        currentScreen = TestScene();

    }
}