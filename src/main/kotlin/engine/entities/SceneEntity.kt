package engine.entities

import glm_.vec2.Vec2
import util.Zero

abstract class SceneEntity: Entity() {

    override var position: Vec2 = Vec2(0,0)

    override val worldPosition: Vec2
        get() = this.position + (parent?.worldPosition ?: Vec2.Zero);

}