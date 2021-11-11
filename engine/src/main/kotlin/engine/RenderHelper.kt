package engine

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import glm_.vec2.Vec2

object RenderHelper {

    fun Batch.drawCentered(texture: TextureRegion, position: Vec2, size: Vec2, rotation: Float) {


        /**
        draw(
            TextureRegion region, float x, float y, float originX, float originY, float width, float height,
            float scaleX, float scaleY, float rotation)
        */

        draw(
            texture,
            position.x-size.x/2, position.y-size.y/2,
            size.x/2,size.y/2,                        //x ,y
                 //OriginY, OriginY
            size.x, size.y,             //Width Height
            1f,1f,               //Scale
            rotation
        )

        //val pos = position - (size/2f)
        //draw(texture, pos.x, pos.y,size.x,size.y);

    }

}