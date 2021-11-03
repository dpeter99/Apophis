package engine.entitysystem

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.app.KtxScreen


open class Scene : KtxScreen {

    var mainCamera: CameraEntity? = null

    private val batch: SpriteBatch = SpriteBatch()
    val shape = ShapeRenderer();

    private var _hierarchy = mutableListOf<Entity>()
    var hierarchy: List<Entity> = _hierarchy

    val ent_mg:EntityManager = EntityManager();

    var time = 0F;

    var firstRun: Boolean = true;

    fun add(ent:Entity, fu: (Entity.() -> Unit)? = null){
        ent_mg.addEntity(ent) {
            it.scene = this;
            _hierarchy.add(it);
            it.Setup();
            if (fu != null) {
                it.fu()
            };
        }
    }

    fun init(){
        ent_mg.Init();
    }

    override fun render(delta: Float) {
        if(firstRun){
            init()
            firstRun = false;
        }

        time += delta;

        ent_mg.Update();

        val batch: Batch = batch
        batch.projectionMatrix = mainCamera?.projectionMatrix

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin()


        ent_mg.Render(batch,1f)

        batch.end()
    }

    override fun resize(width: Int, height: Int) {
        mainCamera?.Recalc();
    }

}