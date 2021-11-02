package engine.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Matrix4
import glm_.vec2.Vec2

class CameraEntity : SceneEntity() {

    private var camera: OrthographicCamera;

    private var _worldSize: Float = 10F;

    var worldSize :Float
        get() = _worldSize;
        set(value) {
            _worldSize = value;
            Recalc()
        }

    val projectionMatrix: Matrix4
        get() = camera.combined;

    init {
        val w = Gdx.graphics.width.toFloat()
        val h = Gdx.graphics.height.toFloat()

        camera = OrthographicCamera(worldSize, worldSize * (h / w))

        camera.update()
    }

    fun Recalc(){
        val w = Gdx.graphics.width.toFloat()
        val h = Gdx.graphics.height.toFloat()

        camera.viewportWidth = _worldSize;
        camera.viewportHeight = _worldSize * h/w;

        camera.update();
    }

    override fun Update() {
        camera.position = this.worldPosition
        //TODO("Not yet implemented")
    }

    override fun Render(batch: Batch, parentAlpha: Float) {
        TODO("Not yet implemented")
    }


}