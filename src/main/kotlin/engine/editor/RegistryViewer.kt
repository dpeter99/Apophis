package engine.editor

import engine.Bootstrap
import engine.registry.Registries
import imgui.ImGui

class RegistryViewer(editor: EditorModule) : EditorWindow(editor) {


    override fun onGui() {
        val scene = Bootstrap.Instance.core.shownScreen;

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