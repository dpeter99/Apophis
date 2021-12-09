package engine.editor

import engine.Bootstrap
import imgui.ImGui

class Hierarchy(editor: EditorModule) : EditorWindow(editor) {


    override fun onGui() {
        val scene = Bootstrap.Instance.core.shownScreen;

        ImGui.begin("Hierarchy");

        if (ImGui.treeNode(scene.toString())) {

            HierarchyHelper.DrawHierarchy(scene.hierarchy, {
                editor.selected_ent == it
            }, {
                editor.selected_ent = it;
                editor.selected_inspector = it;
            },{
                it.hierarchy;
            })

            ImGui.treePop();
        }

        ImGui.end();

    }





}