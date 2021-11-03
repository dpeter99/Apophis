package engine.editor.inspectors

import engine.entitysystem.CameraEntity
import engine.entitysystem.Entity
import engine.registry.ResourceName
import imgui.ImGui

class CameraInspector(registryName: ResourceName, type: Class<CameraEntity>) : Inspector(registryName, type) {

    override fun draw(ent: Entity) {
        InspectorHelper.drawDefault(ent);

        ImGui.text("Test");
    }

}