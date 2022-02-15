package engine.input

import glm_.vec1.Vec1
import glm_.vec2.Vec2


interface EventProcessor{
    fun processEvent(e:Event);
}

@InputTagMarker
class InputMap{

    var actions: MutableList<Action<*>> = mutableListOf()
    private var bindings: MutableList<Binding<*>> = mutableListOf()

    fun <T> action(name:String, init: Action<T>.() -> Unit): Action<T>{
        val map = Action<T>(this)
        map.init()
        actions.add(map);
        return map
    }

    fun addBinding(b:Binding<*>){
        bindings.add(b);
    }

    fun processEvent(e:Event){
        bindings.forEach {
            it.processEvent(e);
        }
    }
}


@InputTagMarker
open class Action<T>(val inputMap: InputMap){

    var bindings = mutableListOf<Binding<T>>();

    enum class ActionType{
        Value,
        Change
    }

    fun <B : Binding<T>> internal_bind(ctor:()-> B, init: B.() -> Unit = {}):B{
        var b = ctor();
        b.init();
        bindings.add(b);
        inputMap.addBinding(b)
        return b;
    }

    fun bind(b:DeviceInput<T>, init: Binding<T>.() -> Unit = {}): Binding<T>{
        return internal_bind({DeviceBinding<T>(b, this)}, init);
    }

}

fun Action<Vec1>.compositeBind(init: CompositeBindingVec1.() -> Unit = {}): CompositeBindingVec1 {
    return this.internal_bind({CompositeBindingVec1(this)},init);
}

fun Action<Vec2>.compositeBind(init: CompositeBindingVec2.() -> Unit = {}): CompositeBindingVec2 {
    return this.internal_bind({CompositeBindingVec2(this)},init);
}

@InputTagMarker
abstract class Binding<T>(var action: Action<T>): EventProcessor{
    //abstract override fun processEvent(e:Event): Unit
}

class DeviceBinding<T>(val dev: DeviceInput<T>, action: Action<T>) : Binding<T>(action){
    override fun processEvent(e: Event): Unit {
        val event = e as InputEvent;

        if(e.path == dev.path){
            //action.update(this, e.value) // Not sure here, somehow we need to notify the one above us.
        }



    }
}

abstract class CompositeBinding_t<T>(action: Action<T>) : Binding<T>(action){

    fun <B : Binding<V>, V> internal_bind(ctor:()-> B, init: B.() -> Unit = {}):B{
        var b = ctor();
        b.init();
        //bindings.add(b);
        this.action.inputMap.addBinding(b)
        return b;
    }

    fun <V> bind(b: DeviceInput<V>, init: DeviceBinding<V>.() -> Unit = {}): Binding<V> {
        return internal_bind({DeviceBinding(b, this.action)}, init);
    }

}

class CompositeBindingVec2(action: Action<Vec2>) : CompositeBinding_t<Vec2>(action) {
    lateinit var XPos: Binding<Vec1>
    lateinit var XNeg: Binding<Vec1>
    lateinit var YPos: Binding<Vec1>
    lateinit var YNeg: Binding<Vec1>

    override fun processEvent(e: Event):Unit {
        TODO("Not yet implemented")
    }
}

class CompositeBindingVec1(action: Action<Vec1>) : CompositeBinding_t<Vec1>(action) {
    lateinit var XPos: Binding<Vec1>
    lateinit var XNeg: Binding<Vec1>

    override fun processEvent(e: Event): Unit {
        TODO("Not yet implemented")
    }
}


open class Event;

open class InputEvent(val path: String): Event()

data class MouseMovedEvent(val pos: Vec2) : InputEvent("Mouse/Pos")


class DeviceInput<T> (val path: String)

object Devices{
    private const val path = "";

    object Keyboard{
        private const val path = Devices.path + "Keyboard"

        val Q = DeviceInput<Vec1>("${this.path}/Q")
        val W = DeviceInput<Vec1>("${this.path}/W")
        val E = DeviceInput<Vec1>("${this.path}/E")
        val R = DeviceInput<Vec1>("${this.path}/R")
        val T = DeviceInput<Vec1>("${this.path}/T")
        val Z = DeviceInput<Vec1>("${this.path}/Z")
        val A = DeviceInput<Vec1>("${this.path}/A")
        val S = DeviceInput<Vec1>("${this.path}/S")
        val D = DeviceInput<Vec1>("${this.path}/D")
        val F = DeviceInput<Vec1>("${this.path}/F")
    }

    object Mouse{
        private const val path = Devices.path + "Mouse"

        val Pos = DeviceInput<Vec2>("$path/Pos");
        val LeftButton = DeviceInput<Vec2>("$path/LeftButton");
    }

}
