package engine.entitysystem

import com.badlogic.gdx.math.Affine2
import com.badlogic.gdx.math.Vector2
import glm_.vec2.Vec2
import util.One
import util.Zero
import util.toVec2
import util.toVector2

open class SceneEntity(name:String): Entity(name) {

    override var position: Vec2 = Vec2(0,0)

    override val worldPosition: Vec2
        get() {
            val a = Affine2()


                .translate((parent?.worldPosition ?: Vec2.Zero).toVector2())
                .scale((parent?.scale ?: Vec2(1f,1f)).toVector2())
                .rotate((parent?.worldRotation ?: 0F))


            var pos = this.position.toVector2();
            a.applyTo(pos);
            return pos.toVec2();
        }


    private var _rotation: Float = 0F;
    override var rotation: Float
        get() = _rotation
        set(value) {
            if(value < 0)
                _rotation = 360 + value;
            else if(value > 360)
                _rotation = value - 360;
            else
                _rotation = value;
        }

    override val worldRotation: Float
        get() = rotation + (parent?.worldRotation ?:  0F);



    override var scale: Vec2 = Vec2.One

    override var worldScale: Vec2 = Vec2.One
        get() = scale * (parent?.worldScale ?:  Vec2.One);

}


