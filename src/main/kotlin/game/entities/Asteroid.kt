package game.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import engine.entitysystem.Entity

class Asteroid(name: String = "Asteroid"): Entity(name) {

    val texture = Texture(Gdx.files.internal("cobblestone.png"));

    override fun Update() {
        //TODO "Not yet implemented"
    }

    override fun Render(batch: Batch, parentAlpha: Float) {
        val pos_x = 16f/2f;
        batch.draw(texture, -pos_x, -pos_x,16f,16f);

    }

}