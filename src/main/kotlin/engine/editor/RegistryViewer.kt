package engine.editor

import engine.Engine
import engine.entitysystem.Entity
import engine.registry.Registries
import imgui.ImGui
import imgui.flag.ImGuiTreeNodeFlags
import util.FontAwesomeIcons

class RegistryViewer(editor: EditorModule) : EditorWindow(editor) {


    override fun onGui() {
        val scene = Engine.Instance.core.shownScreen;

        ImGui.begin("Registry");

        for (reg in Registries.regs){
            if(ImGui.collapsingHeader(reg.registryName.toString())){
                for (item in reg){
                    ImGui.text(item.registryName.toString());
                }
            }
        }

        ImGui.end();

    }





}