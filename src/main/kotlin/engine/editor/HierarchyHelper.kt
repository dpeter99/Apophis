package engine.editor

import engine.entitysystem.Entity
import imgui.ImGui
import imgui.flag.ImGuiStyleVar
import imgui.flag.ImGuiTreeNodeFlags
import util.FontAwesomeIcons
import java.util.function.BiPredicate
import java.util.function.Predicate

object HierarchyHelper {

    fun DrawHierarchy(hierarchy: List<Entity>,
                      selectionPredicate: (Entity)->Boolean,
                      selectionEvent: (Entity) -> Unit,
                      subHierarchy: (Entity)-> List<Entity>){

        ImGui.pushStyleVar(ImGuiStyleVar.CellPadding, 500f,100f);

        for (i in hierarchy) {

            var flag = ImGuiTreeNodeFlags.OpenOnArrow;

            val sub = subHierarchy(i);
            if(sub.isEmpty()){
                flag = flag or ImGuiTreeNodeFlags.Leaf;
            }

            if (selectionPredicate(i)) {
                flag = flag.or(ImGuiTreeNodeFlags.Selected);
            }



            if(ImGui.treeNodeEx(FontAwesomeIcons.Cube + " " + i.name, flag)){

                if(!sub.isEmpty()) {
                    DrawHierarchy(
                        sub,
                        selectionPredicate,
                        selectionEvent,
                        subHierarchy
                    )
                }

                ImGui.treePop();
            }

            if(ImGui.isItemClicked()){
                selectionEvent(i);
            }
        }

        ImGui.popStyleVar();

    }

}