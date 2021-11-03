package engine.editor.inspectors

import engine.entitysystem.CameraEntity
import engine.entitysystem.Entity
import engine.registry.IRegistryEntry
import engine.registry.ISecondaryMapped
import engine.registry.ResourceName
import java.util.*

abstract class Inspector(override var registryName: ResourceName, type: Class<*>): IRegistryEntry, ISecondaryMapped<Class<*>> {

    abstract fun draw(ent: Entity)

    override var sec_registryName: Class<*> = type;
}