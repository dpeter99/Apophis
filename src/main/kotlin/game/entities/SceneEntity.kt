package game.entities

import com.badlogic.gdx.math.Vector2

abstract class SceneEntity: Entity() {

    override var position: Vector2 = Vector2.Zero

    override val worldPosition: Vector2
        get() = Vector2.Zero // this.position + (parent?.worldPosition ?: Vector2.Zero);

}