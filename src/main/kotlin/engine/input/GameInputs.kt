package engine.input

import com.sun.org.apache.xpath.internal.operations.Bool
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

            }
        }

        action<Vec2>("Cursor"){
            bind(Devices.Mouse.Pos)
        }

        action<Float>("Speed"){

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



class Action<T>{

    enum class ActionType{
        Value,
        Change
    }

    fun bind(b:DeviceInput<T>, init: Binding<T>.() -> Unit = {}): Binding<T>{
        var b = DeviceBinding(b)
        b.init()
        return b
    }

}

fun Action<Float>.compositeBind(init: CompositeBindingVec1.() -> Unit = {}): CompositeBindingVec1{
    var b = DeviceBinding(b)
    b.init()
    return b
}


fun <T> Action<T>.compositeBind(init: CompositeBindingVec2.() -> Unit) where T : glm_.vec2.Vec2{

}

abstract class Binding<T>{
    abstract fun processEvent(e:Unit): T
}

class DeviceBinding<T>(val dev: DeviceInput<T>) : Binding<T>(){

    override fun processEvent(e: Unit): T {
        TODO("Not yet implemented")
    }

}

class CompositeBindingVec2 : Binding<Vec2>() {
    lateinit var XPos: Binding<Boolean | Vec1>
    lateinit var XNeg: Binding
    lateinit var YPos: Binding
    lateinit var YNeg: Binding
}

class CompositeBindingVec1 : Binding<Vec1>() {
    lateinit var XPos: Binding
    lateinit var XNeg: Binding
}

class DeviceInput<T> (val path: String){

}

object Devices{
    private const val path = "";

    object Keyboard{
        private const val path = Devices.path + "Keyboard"

        val W = DeviceInput<Boolean>("${this.path}/W")
        const val S = "${this.path}/S"
        const val A = "${this.path}/A"
        const val D = "${this.path}/D"
    }

    object Mouse{
        private const val path = Devices.path + "Mouse"

        val Pos = DeviceInput<Vec2>("$path/Pos");
    }

}
