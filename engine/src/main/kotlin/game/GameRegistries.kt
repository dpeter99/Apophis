package game

import engine.editor.inspectors.InspectorSystem
import game.entities.Asteroid
import engine.entitysystem.EntityType
import engine.registry.Registries
import engine.registry.ResourceName
import game.entities.PLayerInspector
import game.entities.Player

object GameRegistries {

    val itemTypes = Registries.makeRegistry<ItemType>(name("items"));
    val entityType = Registries.makeRegistry<EntityType<*>>(name("entities"));

    val IRON by itemTypes.deferredRegister(ItemType(name("iron")));
    val COAL by itemTypes.deferredRegister(ItemType(name("coal")));

    val ASTEROID = entityType.deferredRegister(EntityType<Asteroid>(name("asteroid")));

    val PLAYER_INSPECTOR = InspectorSystem.inspectors.deferredRegister(PLayerInspector(ResourceName("game", "player_inspector"),Player::class.java))


    fun name(name:String): ResourceName {
        return ResourceName("apophis",name);
    }

    fun Init() {

    }

}