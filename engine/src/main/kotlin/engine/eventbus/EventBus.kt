package engine.eventbus

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class AsyncEventBus {

    private val flows = mutableMapOf<Class<*>, MutableStateFlow<*>>()

    /**
     * Gets a MutableStateFlow for events of the given type. Creates new if one doesn't exist.
     * @return MutableStateFlow for events that are instances of clazz
     */
    internal fun <T : Any> forEvent(clazz: Class<T>): MutableStateFlow<T?> {
        val flow = flows.getOrPut(clazz) {
            MutableStateFlow<T?>(null)
        }
        return flow as MutableStateFlow<T?>
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
    fun <T : Any> getFlow(clazz: Class<T>): Flow<T> {
        return forEvent(clazz).asStateFlow().filterNotNull()
    }

    @JvmOverloads
    fun <T : Any> subscribeTo(
        clazz: Class<T>,
        skipRetained: Boolean = false,
        callback: suspend (event: T) -> Unit
    ) {

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            throw throwable
        }

        val job = CoroutineScope(Job() + Dispatchers.Default + exceptionHandler).launch {
            forEvent(clazz)
                .drop(if (skipRetained) 1 else 0)
                .filterNotNull()
                .collect {  callback(it) }
        }
    }

    fun <T : Any> post(event: T, retain: Boolean = true) {
        val flow = forEvent(event.javaClass)

        flow.tryEmit(event).also {
            if (!it)
                throw IllegalStateException("StateFlow cannot take element, this should never happen")
        }
        if (!retain) {
            // without starting a coroutine here, the event is dropped immediately
            // and not delivered to subscribers
            CoroutineScope(Job() + Dispatchers.Unconfined).launch {
                dropEvent(event.javaClass)
            }
        }
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
        val MAIN = AsyncEventBus();
    }

}

enum class AppEvent {
    RegistryCreation,
    Register
}
