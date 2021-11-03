package engine.editor

import engine.editor.inspectors.InspectorSystem
import imgui.ImGui
import imgui.flag.ImGuiTreeNodeFlags
import imgui.flag.ImGuiWindowFlags
import imgui.type.ImBoolean

class Inspector(editor: EditorModule) : EditorWindow(editor) {

    var shown: Boolean = true



    override fun onGui() {

        ImGui.begin("Inspector", ImBoolean(shown), ImGuiWindowFlags.None);

        editor.selected_ent?.let {

            val title = it.name + " (" + it::class.java.toString() + ")";
            ImGui.text(title);

            ImGui.separator();

            val top_open = ImGui.treeNodeEx(it.name, treeSelectableFlags(editor.selected_ent, editor.selected_inspector));
            if (ImGui.isItemClicked())
                editor.selected_inspector = editor.selected_ent;

            if (top_open)
            {
                for (entity in it.internalHierarchy)
                {

                    var open: Boolean = ImGui.treeNodeEx(entity.name, treeSelectableFlags(entity, editor.selected_inspector));

                    if (ImGui.isItemClicked())
                        editor.selected_inspector = entity;

                    if (open) {
                        ImGui.treePop();
                    }

                }

                ImGui.treePop();
            }

            ImGui.separator();

            if (editor.selected_inspector != null) {
                //ShadowEngine::Debug::InspectorSystem::DrawEntityInspector(selected_inspector);
                InspectorSystem.DEFAULT.draw(editor.selected_inspector!!);
            }
        }

        ImGui.end();

    }

    fun <T> treeSelectableFlags(a:T, b:T): Int {
        var node_flags = ImGuiTreeNodeFlags.OpenOnArrow;
        if (a == b)
            node_flags = node_flags.or(ImGuiTreeNodeFlags.Selected);

        return node_flags;
    }

}