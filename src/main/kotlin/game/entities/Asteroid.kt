package game.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import engine.editor.inspectors.ShowInInspector
import engine.entitysystem.Entity
import engine.entitysystem.SceneEntity

class Asteroid(name: String = "Asteroid"): SceneEntity(name) {

    @ShowInInspector
    var test: Float = 0F

    val texture = Texture(Gdx.files.internal("cobblestone.png"));

    override fun Update() {
        //TODO "Not yet implemented"
    }

    override fun Render(batch: Batch, parentAlpha: Float) {
        val pos = this.position + 16f/2f;
        batch.draw(texture, -pos.x, -pos.y,16f,16f);

    }

}