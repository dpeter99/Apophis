package game.entities

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor

abstract class Entity() {

    var parent: Entity? = null;

    var internalHierarchy = listOf<Entity>()
    var hierarchy = listOf<Entity>()

    /**
     * Local position
     */
    open val position: Vector2
        get() = parent?.position ?: Vector2.Zero;

    open val worldPosition: Vector2
        get() = parent?.worldPosition ?: Vector2.Zero;



    abstract fun Update();

    abstract fun Render();

}