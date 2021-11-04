package game.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import engine.RenderHelper.drawCentered
import engine.entitysystem.CameraEntity
import engine.entitysystem.SceneEntity
import glm_.vec2.Vec2

class Player() : SceneEntity("Player") {

    val texture = Texture(Gdx.files.internal("spaceship.png"));

    override fun Setup(){
        addInternal(CameraEntity()){
            this.worldSize = 200f;
        }
    }

    override fun Update() {

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            this.position.y -= 1f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            this.position.y += 1f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            this.position.x -= 1f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            this.position.x += 1f;
        }
    }

    override fun Render(batch: Batch, parentAlpha: Float) {
        batch.drawCentered(texture,this.position, Vec2(16,20));
    }
}