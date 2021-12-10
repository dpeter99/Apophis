package engine.entitysystem

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.app.KtxScreen
import space.earlygrey.shapedrawer.ShapeDrawer





open class Scene : KtxScreen {



    var mainCamera: CameraEntity? = null

    private val batch: SpriteBatch = SpriteBatch()


    private var _hierarchy = mutableListOf<Entity>()
    var hierarchy: List<Entity> = _hierarchy

    val ent_mg:EntityManager = EntityManager();

    var time = 0F;

    var firstRun: Boolean = true;

    //##############################
    //Shape dawing
    //##############################
    private var texture: Texture
    private var region: TextureRegion
    lateinit var shape: ShapeDrawer;

    init {
        val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
        pixmap.setColor(Color.WHITE)
        pixmap.drawPixel(0, 0)
        texture = Texture(pixmap) //remember to dispose of later

        pixmap.dispose()
        region = TextureRegion(texture, 0, 0, 1, 1)


    }

    fun <T: Entity> add(ent:T, fu: (T.() -> Unit)? = null):T{
        ent_mg.addEntity(ent) {
            it.scene = this;
            _hierarchy.add(it);
            it.Setup();
            if (fu != null) {
                it.fu()
            };
        }
        return ent;
    }

    fun init(){
        shape = ShapeDrawer(batch, region)

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
        batch.projectionMatrix = mainCamera?.combinedMatrix;
        //shape.p = mainCamera?.combinedMatrix;

        Gdx.gl.glClearColor(0.5f, 0.5f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin()

        ent_mg.Render(batch,1f)

        batch.end()

    }

    override fun resize(width: Int, height: Int) {
        mainCamera?.Recalc();
    }

}