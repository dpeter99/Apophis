package engine.editor.inspectors

import engine.entitysystem.Entity
import engine.registry.IRegistryEntry
import engine.registry.ResourceName

abstract class Inspector(override var registryName: ResourceName): IRegistryEntry {

    abstract fun draw(ent: Entity)

}