package game.entities

import engine.editor.inspectors.Inspector
import engine.editor.inspectors.InspectorHelper
import engine.entitysystem.CameraEntity
import engine.entitysystem.Entity
import engine.registry.ResourceName
import glm_.vec2.Vec2
import imgui.ImGui
import imgui.type.ImFloat

class PLayerInspector(registryName: ResourceName, type: Class<Player>) : Inspector(registryName, type) {

    override fun draw(ent: Entity) {
        var cam = ent as Player;

        InspectorHelper.drawDefault(ent);

        ImGui.spacing()

        var heading = cam.heading.array;
        if(ImGui.inputFloat2("Heading",heading)){
            cam.heading = Vec2(heading);
        }

        var speed = ImFloat(cam.speed);
        if(ImGui.inputFloat("Speed",speed)){
            cam.speed = speed.get();
        }
    }

}