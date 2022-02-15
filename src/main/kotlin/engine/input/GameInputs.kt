package engine.input

import glm_.vec1.Vec1
import glm_.vec2.Vec2

@DslMarker
annotation class InputTagMarker

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
                XPos = bind(Devices.Keyboard.W)
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
