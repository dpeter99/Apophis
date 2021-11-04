package engine

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import glm_.vec2.Vec2

object RenderHelper {

    fun Batch.drawCentered(texture: Texture, position: Vec2, size: Vec2) {
        val pos = position - (size/2f)
        draw(texture, pos.x, pos.y,size.x,size.y);
    }

}