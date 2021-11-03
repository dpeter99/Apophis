package engine.editor.inspectors

import engine.entitysystem.Entity
import glm_.vec2.Vec2
import imgui.ImGui

object InspectorHelper {

    fun drawDefault(ent: Entity){

        var pos: FloatArray = ent.position.array;

        if(ImGui.inputFloat3("Pos",pos)){
            ent.position = Vec2(pos);
        }

    }

}