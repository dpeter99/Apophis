package game.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import engine.Bootstrap
import engine.EngineCore
import engine.Time
import engine.entitysystem.SceneEntity


class MainGameUI() : SceneEntity("Game UI") {

    private lateinit var table: Table

    private lateinit var speedIndicator: Label

    val texture = Texture(Gdx.files.internal("spaceship.png"));

    lateinit var GUIBatch: Batch
    lateinit var GuiCamera: OrthographicCamera

    override fun Setup(){

        GUIBatch = SpriteBatch();

        GuiCamera = OrthographicCamera(
            EngineCore.Instance.screenwidth.toFloat(),
            EngineCore.Instance.screenheight.toFloat())

        table = Table()
        table.zIndex = 2;
        table.setPosition(0f,0f)

        table.columnDefaults(5)
        table.bottom()
        //table.add(Image(TextureRegion(texture)));


        speedIndicator = Label("0km/s", Label.LabelStyle(BitmapFont(), Color.GRAY))
        table.add(speedIndicator)

        table.setDebug(true) // This is optional, but enables debug lines for tables.
    }

    override fun Update() {
        super.Update()
        table.act(Time.DeltaTime);

        var player = scene.ent_mg.entities.find { it.name == "Player" } as Player;

        speedIndicator.setText( player.speed.toString() + "km/s");
    }

    override fun Render(batch: Batch, parentAlpha: Float) {

        batch.end();

        RefreshRendering()


        table.draw(GUIBatch,parentAlpha);

        table.debugTable();

        GUIBatch.end()

        batch.projectionMatrix = scene.mainCamera?.combinedMatrix;
        batch.transformMatrix  = Matrix4();
        batch.begin();

    }

    private fun RefreshRendering() {
        val w = Bootstrap.Instance.core.screenwidth;
        val h = Bootstrap.Instance.core.screenheight

        GuiCamera.viewportWidth = w.toFloat();
        GuiCamera.viewportHeight = h.toFloat();
        GuiCamera.position.set(w / 2f, h / 2f, 0f);
        GuiCamera.update();

        table.setScale(1f, 1f)
        table.setSize(w.toFloat(), h.toFloat());
        table.setPosition(0f, 0f)

        GUIBatch.projectionMatrix = GuiCamera.combined;
        GUIBatch.begin()
    }


}