package engine.registry

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap

class SecondaryMappingRegistry<T, S>(registryName: ResourceName) : Registry<T>(registryName) where T : IRegistryEntry, T: ISecondaryMapped<S>{

    var sec_mapping: BiMap<S,T> = HashBiMap.create();

    override fun register(name: ResourceName, itemType: T): T {
        super.register(name, itemType)
        sec_mapping.put(itemType.sec_registryName,itemType);
        return itemType;
    }

    fun get(key:S):T?{
        return sec_mapping.get(key);
    }
}

