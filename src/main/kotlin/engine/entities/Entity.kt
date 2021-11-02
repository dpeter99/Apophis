package engine.entities

import com.badlogic.gdx.graphics.g2d.Batch
import glm_.vec2.Vec2

abstract class Entity() {

    var parent: Entity? = null;

    var internalHierarchy = listOf<Entity>()
    var hierarchy = listOf<Entity>()

    /**
     * Local position
     */
    open val position: Vec2
        get() = parent?.position ?: Vec2(0,0);

    open val worldPosition: Vec2
        get() = parent?.worldPosition ?: Vec2(0,0);



    abstract fun Update();

    abstract fun Render(batch: Batch, parentAlpha:Float);

}