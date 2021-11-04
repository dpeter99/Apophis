package engine.editor

import engine.Engine
import engine.entitysystem.Entity
import imgui.ImGui
import imgui.flag.ImGuiTreeNodeFlags
import util.FontAwesomeIcons

class Hierarchy(editor: EditorModule) : EditorWindow(editor) {


    override fun onGui() {
        val scene = Engine.Instance.core.shownScreen;

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