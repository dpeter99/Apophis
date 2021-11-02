package engine.entities

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.app.KtxScreen


open class Scene : KtxScreen {
    private var camera: CameraEntity;
    private val batch: SpriteBatch = SpriteBatch()
    val shape = ShapeRenderer();

    var hierarchy = mutableListOf<Entity>()

    //private val viewport: Viewport = FitViewport(40f,40f);

    init {
        camera = CameraEntity();

    }

    override fun render(delta: Float) {
        camera.worldSize = 160f;

        val batch: Batch = batch
        batch.projectionMatrix = camera.projectionMatrix
        batch.begin()


        for (i in hierarchy){
            i.Render(batch,1f);
        }

        batch.end()
    }

    override fun resize(width: Int, height: Int) {
        camera.Recalc();
    }
}