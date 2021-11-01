package game

import eventbus.AppEvent
import eventbus.SyncEventBus
import game.entities.Asteroid
import game.entities.EntityType
import registry.Registries
import registry.Registry
import registry.ResourceName

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