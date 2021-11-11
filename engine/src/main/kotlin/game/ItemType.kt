package game

import engine.registry.IRegistryEntry
import engine.registry.ResourceName

class ItemType(override var registryName: ResourceName) : IRegistryEntry {
}