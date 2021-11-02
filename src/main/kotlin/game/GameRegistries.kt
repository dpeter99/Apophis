package game

import engine.eventbus.AppEvent
import engine.eventbus.SyncEventBus
import game.entities.Asteroid
import engine.entities.EntityType
import engine.registry.Registries
import engine.registry.ResourceName

object GameRegistries {

    val itemTypes = Registries.makeRegistry<ItemType>(name("items"));
    val entityType = Registries.makeRegistry<EntityType<*>>(name("entities"));

    val IRON by itemTypes.deferredRegister(ItemType(name("iron")));
    val COAL by itemTypes.deferredRegister(ItemType(name("coal")));

    val ASTEROID = entityType.deferredRegister(EntityType<Asteroid>(name("asteroid")));

    fun doRegister(){
        SyncEventBus.MAIN.post(AppEvent.RegistryCreation);

        SyncEventBus.MAIN.post(AppEvent.Register);
    }

    fun name(name:String): ResourceName {
        return ResourceName("apophis",name);
    }

}


fun main() {

    GameRegistries.doRegister();

    GameRegistries.itemTypes.getValues().forEach {
        System.out.println(it.registryName);
    }

    //System.out.println("asd");

}