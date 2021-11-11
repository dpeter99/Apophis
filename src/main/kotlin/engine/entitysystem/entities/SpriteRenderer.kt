package engine.entitysystem.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import engine.RenderHelper.drawCentered
import engine.entitysystem.SceneEntity
import glm_.vec2.Vec2

class SpriteRenderer(name: String = "Sprite Renderer") : SceneEntity(name) {

    val texture = Texture(Gdx.files.internal("spaceship.png"));

    val region = TextureRegion(texture);

    val size: Float = 10F;

    override fun Render(batch: Batch, parentAlpha: Float) {
        val height = worldScale.y * size;
        val width = texture.width * (height / texture.height);

        batch.drawCentered(region,this.worldPosition, Vec2(width, height), this.worldRotation);

        scene.shape.setColor(Color.BLUE)
        scene.shape.circle(worldPosition.x, worldPosition.y, 1f);
    }
}