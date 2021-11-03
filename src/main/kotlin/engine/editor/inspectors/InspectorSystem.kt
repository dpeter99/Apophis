package engine.editor.inspectors

import engine.registry.Registries
import engine.registry.ResourceName
import game.GameRegistries
import game.ItemType

object InspectorSystem {

    val inspectors = Registries.makeRegistry<Inspector>(ResourceName("engine","inspectors"));

    val DEFAULT by inspectors.deferredRegister(DefaultInspector(ResourceName("engine","default")));

}