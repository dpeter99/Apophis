package engine.input

import glm_.vec1.Vec1
import glm_.vec2.Vec2

@DslMarker
annotation class InputTagMarker{

}

open class InputDefinition{


    fun inputMap(init: InputMap.() -> Unit): InputMap{
        val map = InputMap()
        map.init()
        return map
    }

}

class GameInputs : InputDefinition() {

    var inGameMap = inputMap {
        action<Vec2>("Move"){
            compositeBind {
                XPos =
            }
        }

        action<Vec2>("Cursor"){
            bind(Devices.Mouse.Pos)
        }

        action<Vec1>("Speed"){
            compositeBind {
                XPos = bind(Devices.Keyboard.Q)
                XNeg = bind(Devices.Keyboard.E)
            }
        }

    }

}

@InputTagMarker
class InputMap{
    fun <T> action(name:String, init: Action<T>.() -> Unit): Action<T>{
        val map = Action<T>()
        map.init()
        return map
    }
}


@InputTagMarker
open class Action<T>{

    enum class ActionType{
        Value,
        Change
    }

    fun <B : Binding<T>> internal_bind(ctor:()-> B, init: B.() -> Unit = {}):B{
        var b = ctor();
        b.init();
        return b;
    }

    fun bind(b:DeviceInput<T>, init: Binding<T>.() -> Unit = {}): Binding<T>{
        return internal_bind({DeviceBinding<T>(b)}, init);
    }

}

fun Action<Vec1>.compositeBind(init: CompositeBindingVec1.() -> Unit = {}): CompositeBindingVec1 {
    return this.internal_bind({CompositeBindingVec1()},init);
}

fun Action<Vec2>.compositeBind(init: CompositeBindingVec2.() -> Unit = {}): CompositeBindingVec2 {
    return this.internal_bind({CompositeBindingVec2()},init);
}

@InputTagMarker
abstract class Binding<T>{
    abstract fun processEvent(e:Unit): T
}

class DeviceBinding<T>(val dev: DeviceInput<T>) : Binding<T>(){
    override fun processEvent(e: Unit): T {
        TODO("Not yet implemented")
    }
}

abstract class CompositeBinding_t<T> : Binding<T>(){

    fun bind(init: Binding<Vec1>.() -> Unit = {}): Binding<Vec1>{

    }

}

class CompositeBindingVec2 : Binding<Vec2>() {
    lateinit var XPos: Binding<Vec1>
    lateinit var XNeg: Binding<Vec1>
    lateinit var YPos: Binding<Vec1>
    lateinit var YNeg: Binding<Vec1>

    override fun processEvent(e: Unit): Vec2 {
        TODO("Not yet implemented")
    }
}

class CompositeBindingVec1 : Binding<Vec1>() {
    lateinit var XPos: Binding<Vec1>
    lateinit var XNeg: Binding<Vec1>

    override fun processEvent(e: Unit): Vec1 {
        TODO("Not yet implemented")
    }
}




class DeviceInput<T> (val path: String){

}

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
    }

}
