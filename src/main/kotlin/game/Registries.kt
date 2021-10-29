package game

import eventbus.AppEvent
import eventbus.SyncEventBus
import game.entities.EntityType
import registry.Registry
import registry.RegistryDelegate

object Registries {

    val itemTypes = Registry<ItemType>();

    val IRON by RegistryDelegate(itemTypes,ItemType("iron"));


    val entityType = Registry<EntityType>();


    fun doRegister(){
        SyncEventBus.MAIN.post(AppEvent.RegistryCreation);

        SyncEventBus.MAIN.post(AppEvent.Register);
    }

}

fun main() {

    Registries.doRegister();

    Registries.itemTypes.getValues().forEach {
        System.out.println(it.registryName);
    }

    System.out.println("asd");

}