package engine.editor.inspectors

import engine.editor.EditorCore
import engine.entitysystem.Entity
import engine.entitysystem.SceneEntity
import glm_.vec2.Vec2
import imgui.ImGui
import imgui.extension.imguizmo.ImGuizmo
import imgui.type.ImFloat

object InspectorHelper {

    private val INPUT_CAMERA_VIEW = floatArrayOf(
        1f, 0f, 0f, 0f,
        0f, 1f, 0f, 0f,
        0f, 0f, 1f, 0f,
        -10f, -10f, -10f, 1f
    )

    private val OBJECT_MATRICES = arrayOf(
        floatArrayOf(
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f
        ), floatArrayOf(
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            2f, 0f, 0f, 1f
        ), floatArrayOf(
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            2f, 0f, 2f, 1f
        ), floatArrayOf(
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 2f, 1f
        )
    )

    private val IDENTITY_MATRIX = floatArrayOf(
        1f, 0f, 0f, 0f,
        0f, 1f, 0f, 0f,
        0f, 0f, 1f, 0f,
        0f, 0f, 0f, 1f
    )

    fun drawDefault(ent: Entity){




        ImGui.beginDisabled(!(ent is SceneEntity))

        var pos: FloatArray = ent.position.array;
        if(ImGui.inputFloat2("Pos",pos)){
            ent.position = Vec2(pos);
        }

        var rot: ImFloat = ImFloat(ent.rotation);
        if(ImGui.inputFloat("Rot",rot)){
            ent.rotation = rot.get();
        }

        var scale: FloatArray = ent.scale.array;
        if(ImGui.inputFloat2("Scale",scale)){
            ent.scale = Vec2(scale);
        }

        ImGuizmo.drawCubes(
            EditorCore.Instance.shownScreen.mainCamera?.viewMatrix?.`val`,
            EditorCore.Instance.shownScreen.mainCamera?.projectionMatrix?.`val`,
            OBJECT_MATRICES[0]
        );

        ImGui.endDisabled();



    }

}