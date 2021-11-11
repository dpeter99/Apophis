package engine.entitysystem

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Matrix4
import engine.Engine
import util.toVector3

class CameraEntity : SceneEntity("Camera") {

    private lateinit var camera: OrthographicCamera;

    private var _worldSize: Float = 10F;
    var worldSize :Float
        get() = _worldSize;
        set(value) {
            _worldSize = value;
            Recalc()
        }

    val projectionMatrix: Matrix4
        get() = camera.projection;

    val viewMatrix: Matrix4
        get() = camera.view;

    val combinedMatrix: Matrix4
        get() = camera.combined;

    override fun Setup() {
        camera = OrthographicCamera(0f, 0f)

        Recalc();

        if(scene.mainCamera == null)
            scene.mainCamera = this;
    }

    fun Recalc(){
        val w = Engine.Instance.core.screenwidth;
        val h = Engine.Instance.core.screenheight

        camera.viewportWidth = _worldSize;
        camera.viewportHeight = _worldSize * h/w;

        camera.update();
    }

    override fun Update() {
        camera.position.set(this.worldPosition.toVector3());
        camera.update();
        //TODO("Not yet implemented")
    }

    override fun Render(batch: Batch, parentAlpha: Float) {
        //TODO("Not yet implemented")
    }


}