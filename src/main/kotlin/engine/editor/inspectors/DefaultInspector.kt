package engine.editor.inspectors

import engine.entitysystem.CameraEntity
import engine.entitysystem.Entity
import engine.registry.ResourceName
import imgui.ImGui

class DefaultInspector(registryName: ResourceName) : Inspector(registryName, Object::class.java) {

    override fun draw(ent:Entity) {

        InspectorHelper.drawBasicInspector(ent)
    }

}