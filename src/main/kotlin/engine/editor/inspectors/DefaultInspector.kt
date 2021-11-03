package engine.editor.inspectors

import engine.entitysystem.Entity
import engine.registry.ResourceName
import glm_.vec2.Vec2
import imgui.ImGui

class DefaultInspector(registryName: ResourceName) : Inspector(registryName) {

    override fun draw(ent:Entity) {

        var pos: FloatArray = ent.position.array;

        if(ImGui.inputFloat3("Pos",pos)){
            ent.position = Vec2(pos);
        }
    }

}