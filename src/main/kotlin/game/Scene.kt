package game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.ScreenUtils
import game.entities.Entity
import imgui.ImGui
import ktx.app.KtxScreen


class Scene : KtxScreen {

    //private val viewport: Viewport = FitViewport()

    private var camera: OrthographicCamera;
    private val batch: SpriteBatch = SpriteBatch()
    val shape = ShapeRenderer();

    var hierarchy = listOf<Entity>()

    private var bucket: Rectangle;

    init {
        val w = Gdx.graphics.width.toFloat()
        val h = Gdx.graphics.height.toFloat()

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        camera = OrthographicCamera(40F, 30 * (h / w))

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0F)
        camera.update()

        bucket = Rectangle()
        bucket.x = (800 / 2 - 64 / 2).toFloat()
        bucket.y = 20f
        bucket.width = 64f
        bucket.height = 64f
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0F, 0F, 0.2f, 1F);

        shape.projectionMatrix = camera.combined;
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(0F,0F,30F,30F);
        shape.end();

        var f = 0f
        with(ImGui) {
            text("Hello, world %d", 123)
            //inputText("string")
            //sliderFloat("float", ::f, 0f, 1f)
        }

    }

    override fun resize(width: Int, height: Int) {
        camera.viewportWidth = 30f;
        camera.viewportHeight = 30f * height/width;
        camera.update();
    }
}