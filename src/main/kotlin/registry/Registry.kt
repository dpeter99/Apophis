package registry

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import eventbus.AppEvent
import eventbus.SyncEventBus
import java.util.*
import kotlin.reflect.KProperty


class Registry<T : IRegistryEntry> {
    val entries: List<IRegistryEntry> = ArrayList()

    private val names: BiMap<String, T> = HashBiMap.create()

    fun register(name: String, itemType: T): T {
        names.put(name,itemType);
        return itemType;
    }

    fun getValues(): List<T> {
        return names.values.toList()
    }

}

class RegistryDelegate<T : IRegistryEntry> (private var registry : Registry<T>, private var item: T) {

    var registered = false;

    init {
        SyncEventBus.MAIN.subscribeTo(AppEvent.Register::class.java,callback = {
            registry.register(item.registryName,item);
            registered = true;
        })
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return item
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        //println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}

