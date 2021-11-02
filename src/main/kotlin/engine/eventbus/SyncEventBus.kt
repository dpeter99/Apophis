package engine.eventbus

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow

class SyncEventBus {

    private val flows = mutableMapOf<Class<*>, Event<*>>()

    /**
     * Gets a MutableStateFlow for events of the given type. Creates new if one doesn't exist.
     * @return MutableStateFlow for events that are instances of clazz
     */
    internal fun <T : Any> forEvent(clazz: Class<T>): Event<T> {
        val flow = flows.getOrPut(clazz) {
            Event<T>()
        }
        return flow as Event<T>
    }

    /**
     * Gets a Flow for events of the given type.
     *
     * **This flow never completes.**
     *
     * The returned Flow is _hot_ as it is based on a [SharedFlow]. This means a call to [collect] never completes normally, calling [toList] will suspend forever, etc.
     *
     * You are entirely responsible to cancel this flow. To cancel this flow, the scope in which the coroutine is running needs to be cancelled.
     * @see [SharedFlow]
     */
    fun <T : Any> getFlow(clazz: Class<T>): Event<T> {
        return forEvent(clazz)
    }

    @JvmOverloads
    fun <T : Any> subscribeTo(
        clazz: Class<T>,
        skipRetained: Boolean = false,
        callback: (event: T) -> Unit
    ) {

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            throw throwable
        }

        forEvent(clazz).plusAssign {
            callback(it);
        }
    }

    fun <T : Any> post(event: T, retain: Boolean = true) {
        val flow = forEvent(event.javaClass)

        flow.invoke(event);
    }

    /**
     *  Removes retained event of type [clazz]
     */
    fun <T> dropEvent(clazz: Class<T>) {
        if (!flows.contains(clazz)) return
        val channel = flows[clazz] as MutableStateFlow<T?>
        channel.tryEmit(null)
    }

    companion object{
        val MAIN = SyncEventBus();
    }

}