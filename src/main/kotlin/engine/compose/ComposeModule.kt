package engine.compose


import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.ComposeScene
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.GL20.GL_FRAMEBUFFER_BINDING
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer
import engine.Bootstrap
import engine.modules.ApplicationModule
import org.jetbrains.skia.*
import org.jetbrains.skia.FramebufferFormat.Companion.GR_GL_RGBA8
import org.lwjgl.opengl.GL11

class ComposeModule: ApplicationModule {


    lateinit var fb: FrameBuffer;
    private lateinit var batch: SpriteBatch;

    lateinit var surface: Surface
    lateinit var context:DirectContext


    lateinit var composeScene: ComposeScene

    var p = 0;

    //val glfwDispatcher = GlfwCoroutineDispatcher()

    override fun Init() {
        super.Init()

        batch = SpriteBatch();

        context = DirectContext.makeGL()

        val w = Bootstrap.Instance.core.screenwidth;
        val h = Bootstrap.Instance.core.screenheight;

        fb = FrameBuffer(com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888, w, h, false);

        val fbId = fb.framebufferHandle //GL40.glGetInteger(GL_FRAMEBUFFER_BINDING)
        print(fbId);
        val renderTarget = BackendRenderTarget.makeGL(w, h, 0, 8, fbId, GR_GL_RGBA8)
        surface = Surface.makeFromBackendRenderTarget(
            context, renderTarget, SurfaceOrigin.BOTTOM_LEFT, SurfaceColorFormat.RGBA_8888, ColorSpace.sRGB
        )


        composeScene = ComposeScene()

        composeScene.setContent {
            Button({}) {
                Text("Hello!")
            }
        }
    }

    override fun PreFrame() {
        super.PreFrame()

        val w = Bootstrap.Instance.core.screenwidth;
        val h = Bootstrap.Instance.core.screenheight;

        fb.begin()

        //val fbId = GL11.glGetInteger(GL_FRAMEBUFFER_BINDING)
        //val renderTarget = BackendRenderTarget.makeGL(w, h, 0, 8, fbId, GR_GL_RGBA8)
        //surface = Surface.makeFromBackendRenderTarget(
        //    context, renderTarget, SurfaceOrigin.BOTTOM_LEFT, SurfaceColorFormat.RGBA_8888, ColorSpace.sRGB
        //)

        p++
        p = if (p > 500) 0 else p
        surface.canvas.clear(Color.makeLerp(Color.RED, Color.YELLOW,p/500f))
        //surface.canvas.clear(Color.RED)

        //composeScene.constraints = Constraints(maxWidth = w, maxHeight = h)
        //composeScene.render(surface.canvas, System.nanoTime())

        //surface.flushAndSubmit()
        surface.flush();
        //context.flush()
        //context.submit(true)

        fb.end();
        //Gdx.gl20.glBindFramebuffer(GL20.GL_FRAMEBUFFER, 0)

    }

    override fun LateRender () {
        val w = Bootstrap.Instance.core.screenwidth;
        val h = Bootstrap.Instance.core.screenheight;

        batch.begin();
        batch.projectionMatrix.setToOrtho2D(0f, 0f, w.toFloat(), h.toFloat());
        batch.draw(fb.colorBufferTexture, 0f, 0f, w.toFloat(), h.toFloat(), 0f, 0f, 1f, 1f)
        batch.end();

    }

    override fun dispose() {

    }
}