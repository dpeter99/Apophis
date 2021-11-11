package engine.editor.inspectors

import engine.entitysystem.CameraEntity
import engine.entitysystem.Entity
import engine.registry.ResourceName
import imgui.ImGui
import imgui.type.ImFloat

class CameraInspector(registryName: ResourceName, type: Class<CameraEntity>) : Inspector(registryName, type) {

    override fun draw(ent: Entity) {
        var cam = ent as CameraEntity;

        InspectorHelper.drawDefault(ent);

        var worldSize = ImFloat( cam.worldSize);

        if(ImGui.inputFloat("World Size",worldSize)){
            cam.worldSize = worldSize.get();
        }
    }

}