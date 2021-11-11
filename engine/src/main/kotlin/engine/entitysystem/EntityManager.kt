package engine.entitysystem

import com.badlogic.gdx.graphics.g2d.Batch

class EntityManager {

    var next_ID: Int = 0;
    var entities = mutableListOf<Entity>()

    fun <T: Entity> addEntity(entity: T, fu: (T) -> Unit){
        entities.add(entity);
        entity.ID = next_ID;
        next_ID++;
        fu(entity);
    }

    fun Update(){
        for (ent in entities){
            ent.Update();
        }
    }

    fun Init(){
        for (ent in entities){
            ent.Init();
        }
    }

    fun Render(batch: Batch, parentAlpha:Float){
        for (ent in entities){
            ent.Render(batch, parentAlpha);
        }
    }

}