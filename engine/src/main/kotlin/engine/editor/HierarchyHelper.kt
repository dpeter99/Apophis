package engine.editor

import engine.entitysystem.Entity
import imgui.ImGui
import imgui.flag.ImGuiStyleVar
import imgui.flag.ImGuiTreeNodeFlags
import util.FontAwesomeIcons

object HierarchyHelper {

    fun DrawHierarchy(
        hierarchy: List<Entity>,
        selectionPredicate: (Entity) -> Boolean,
        selectionEvent: (Entity) -> Unit,
        subHierarchy: (Entity) -> List<Entity>
    ) {

        ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 1f, 1f);

        for (i in hierarchy) {

            var flag = ImGuiTreeNodeFlags.OpenOnArrow or ImGuiTreeNodeFlags.FramePadding;

            val sub = subHierarchy(i);
            if (sub.isEmpty()) {
                flag = flag or ImGuiTreeNodeFlags.Leaf;
            }

            if (selectionPredicate(i)) {
                flag = flag.or(ImGuiTreeNodeFlags.Selected);
            }


            val open = ImGui.treeNodeEx(FontAwesomeIcons.Cube + " " + i.name, flag)

            if (ImGui.isItemClicked()) {
                selectionEvent(i);
            }
            if (open) {
                if (!sub.isEmpty()) {
                    DrawHierarchy(
                        sub,
                        selectionPredicate,
                        selectionEvent,
                        subHierarchy
                    )
                }

                ImGui.treePop();
            }


        }

        ImGui.popStyleVar();

    }

}