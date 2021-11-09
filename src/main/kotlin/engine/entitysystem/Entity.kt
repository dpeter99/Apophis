package engine.entitysystem

import com.badlogic.gdx.graphics.g2d.Batch
import glm_.vec2.Vec2
import util.One
import util.Zero



open class Entity(name: String = "") {

    var ID: Int = 0;
    lateinit var scene: Scene;

    var name: String = name;
    var parent: Entity? = null;

    var internalHierarchy = mutableListOf<Entity>()
    var hierarchy = mutableListOf<Entity>()

    /**
     * Local position
     */
    open var position: Vec2 = Vec2.Zero
        get() = parent?.position ?: Vec2(0,0);

    open val worldPosition: Vec2
        get() = parent?.worldPosition ?: Vec2(0,0);


    /**
     * Rotation
     */
    open var rotation: Float = 0F
        get() = parent?.rotation ?: 0F;

    open val worldRotation: Float
        get() = parent?.worldRotation ?: 0F;



    /**
     * Scale
     */
    open var scale: Vec2 = Vec2.One
        get() = parent?.scale ?: Vec2.One

    open var worldScale: Vec2 = Vec2.One
        get() = parent?.worldScale ?: Vec2.One;



    fun <T : Entity> addInternal(ent: T, fu: (T.() -> Unit)? = null): T {
        scene.ent_mg.addEntity(ent) {
            it.parent = this
            it.scene = this.scene;
            internalHierarchy.add(it);
            it.Setup();
            if (fu != null) {
                it.fu()
            };
        }

        return ent
    }

    fun <T : Entity> add(ent: T, fu: (T.() -> Unit)? = null): T {
        scene.ent_mg.addEntity(ent) {
            it.parent = this
            it.scene = this.scene;
            hierarchy.add(it);
            it.Setup();
            if (fu != null) {
                it.fu()
            };
        }

        return ent;
    }

    /**
     * Setup is the new constructor
     */
    open fun Setup() = Unit;

    open fun Init() = Unit;

    open fun Update() = Unit;

    open fun Render(batch: Batch, parentAlpha:Float) = Unit;


}