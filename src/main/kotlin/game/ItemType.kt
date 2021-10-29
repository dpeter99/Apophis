package game

import registry.IRegistryEntry

class ItemType(registryName: String) : IRegistryEntry {

    override var registryName: String = registryName;
}