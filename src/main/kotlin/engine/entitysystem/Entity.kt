package engine.entitysystem

import com.badlogic.gdx.graphics.g2d.Batch
import glm_.vec2.Vec2

abstract class Entity(name: String = "") {

    var ID: Int = 0;
    lateinit var scene: Scene;

    var name: String = name;
    var parent: Entity? = null;

    var internalHierarchy = mutableListOf<Entity>()
    var hierarchy = mutableListOf<Entity>()

    /**
     * Local position
     */
    open val position: Vec2
        get() = parent?.position ?: Vec2(0,0);

    open val worldPosition: Vec2
        get() = parent?.worldPosition ?: Vec2(0,0);


    fun <T : Entity> addInternal(ent: T, fu: (T.() -> Unit)? = null){
        scene.ent_mg.addEntity(ent) {
            it.parent = this
            it.scene = this.scene;
            internalHierarchy.add(it);
            it.Setup();
            if (fu != null) {
                it.fu()
            };
        }
    }

    /**
     * Setup is the new constructor
     */
    open fun Setup() = Unit;

    open fun Init() = Unit;

    open fun Update() = Unit;

    open fun Render(batch: Batch, parentAlpha:Float) = Unit;

}