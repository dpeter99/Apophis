package engine.editor.inspectors

import engine.entitysystem.Entity
import engine.entitysystem.SceneEntity
import glm_.vec2.Vec2
import imgui.ImGui

object InspectorHelper {

    fun drawDefault(ent: Entity){

        var pos: FloatArray = ent.position.array;


        ImGui.beginDisabled(!(ent is SceneEntity))


        if(ImGui.inputFloat2("Pos",pos)){
            ent.position = Vec2(pos);
        }

        ImGui.endDisabled();
    }

}