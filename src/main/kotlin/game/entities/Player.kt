package game.entities

import com.badlogic.gdx.graphics.g2d.Batch
import engine.entitysystem.CameraEntity
import engine.entitysystem.SceneEntity

class Player() : SceneEntity("Player") {

    override fun Setup(){
        addInternal(CameraEntity()){
            this.worldSize = 50f;
        }
    }

    override fun Update() {
        //TODO("Not yet implemented")
    }

    override fun Render(batch: Batch, parentAlpha: Float) {
        //TODO("Not yet implemented")
    }
}