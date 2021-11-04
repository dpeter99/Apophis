package engine.entitysystem

import glm_.vec2.Vec2
import util.Zero

open class SceneEntity(name:String): Entity(name) {

    override var position: Vec2 = Vec2(0,0)

    override val worldPosition: Vec2
        get() = this.position + (parent?.worldPosition ?: Vec2.Zero);


}