package engine.editor

import engine.Engine
import imgui.ImGui
import imgui.flag.ImGuiTreeNodeFlags
import util.FontAwesomeIcons

class Hierarchy(editor: EditorModule) : EditorWindow(editor) {


    override fun onGui() {
        val scene = Engine.Instance.core.shownScreen;

        ImGui.begin("Hierarchy");

        if (ImGui.treeNode(scene.toString())) {

            for (i in scene.hierarchy) {

                var flag = ImGuiTreeNodeFlags.OpenOnArrow;
                if (editor.selected_ent == i) {
                    flag = flag.or(ImGuiTreeNodeFlags.Selected);
                }

                if(ImGui.treeNodeEx(FontAwesomeIcons.Cube + " " + i.name, flag)){
                    ImGui.treePop();
                }

                if(ImGui.isItemClicked()){
                    editor.selected_ent = i;
                    editor.selected_inspector = i;
                }
            }

            ImGui.treePop();
        }

        ImGui.end();

    }


}