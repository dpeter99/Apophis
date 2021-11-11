package engine.editor.inspectors

import com.badlogic.gdx.graphics.Color
import engine.editor.EditorCore
import engine.entitysystem.Entity
import engine.entitysystem.SceneEntity
import glm_.vec2.Vec2
import imgui.ImGui
import imgui.ImVec2
import imgui.ImVec4
import imgui.extension.imguizmo.ImGuizmo
import imgui.flag.ImGuiCol
import imgui.flag.ImGuiStyleVar
import imgui.type.ImFloat
import kotlin.jvm.internal.Ref
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createType
import kotlin.reflect.full.memberProperties

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


    val red = ButtonColorSet(
        Color( 0.8f, 0.1f, 0.15f, 1.0f),
        Color(0.9f, 0.2f, 0.2f, 1.0f),
        Color(0.8f, 0.1f, 0.15f, 1.0f)
    )

    val green = ButtonColorSet(
        Color( 0.2f, 0.7f, 0.2f, 1.0f),
        Color(0.3f, 0.8f, 0.3f, 1.0f),
        Color(0.2f, 0.7f, 0.2f, 1.0f)
    )


    fun drawDefault(ent: Entity) {

        ImGui.beginDisabled(!(ent is SceneEntity))

        var pos: FloatArray = ent.position.array;
        DrawVec2Control("Pos", ent.position);

        val rot = ImFloat(ent.rotation)
        if(DrawFloatControl("Rotation",rot)){
            ent.rotation = rot.get();
        }

        DrawVec2Control("Scale", ent.scale, 1f)

        ImGuizmo.drawCubes(
            EditorCore.Instance.shownScreen.mainCamera?.viewMatrix?.`val`,
            EditorCore.Instance.shownScreen.mainCamera?.projectionMatrix?.`val`,
            OBJECT_MATRICES[0]
        );

        ImGui.endDisabled();
    }

    fun drawBasicInspector(ent: Entity){
        drawDefault(ent);

        for (f in ent::class.memberProperties){
            if(f.annotations.any { a -> a is ShowInInspector }){

                ImGui.beginDisabled(!(f is KMutableProperty<*>));

                if(f.returnType == Float::class.createType()){

                    val rot = ImFloat((f.getter.call(ent) as Float))
                    if(DrawFloatControl(f.name,rot)){
                        if(f is KMutableProperty<*>)
                            f.setter.call(ent,rot.get());
                    }

                }

                ImGui.endDisabled();

                ImGui.text(f.name);
            }
        }

    }

    fun DrawFloatControl(label: String, values: ImFloat, resetValue: Float = 0.0f, columnWidth: Float = 100.0f): Boolean {
        val io = ImGui.getIO();
        //var boldFont = io.fonts. ->Fonts[0];

        ImGui.pushID(label);

        ImGui.columns(2);
        ImGui.setColumnWidth(0, columnWidth);
        ImGui.text(label);
        ImGui.nextColumn();

        var w =ImGui.calcItemWidth();

        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 0f, 0f);

        //val x: FloatArray = floatArrayOf(values.)
        val res = internal_float_field("X",w, values.data, resetValue, red)
            //values.element = x[0];

        ImGui.popStyleVar();

        ImGui.columns(1);

        ImGui.popID();

        return res;
    }

    fun DrawVec2Control(label: String, values: Vec2, resetValue: Float = 0.0f, columnWidth: Float = 100.0f): Boolean {
        val io = ImGui.getIO();
        //var boldFont = io.fonts. ->Fonts[0];

        ImGui.pushID(label);

        ImGui.columns(2);
        ImGui.setColumnWidth(0, columnWidth);
        ImGui.text(label);
        ImGui.nextColumn();

        val w = ImGui.calcItemWidth()/2;

        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 0f, 0f);

        var res = false;

        val x: FloatArray = floatArrayOf(values.x)
        if(internal_float_field("X",w, x,resetValue, red)) {
            values.x = x[0];
            res = true;
        }
        ImGui.sameLine();

        val y: FloatArray = floatArrayOf(values.y)
        if(internal_float_field("Y", w, y, resetValue, green)) {
            values.y = y[0];
            res = true;
        }
        //ImGui.sameLine();

        ImGui.popStyleVar();

        ImGui.columns(1);

        ImGui.popID();

        return res;
    }

    private fun internal_float_field(
        label: String,
        w: Float,
        values: FloatArray,
        resetValue: Float,
        color: ButtonColorSet
    ): Boolean {
        val lineHeight = ImGui.getFont().fontSize + ImGui.getStyle().framePaddingY * 2.0f;
        val buttonSize = ImVec2(lineHeight + 3.0f, lineHeight);

        ImGui.pushItemWidth(w);

        color.push();

        var res = false;

        //ImGui.pushFont(boldFont);
        if (ImGui.button(label, buttonSize.x, buttonSize.y)) {
            values[0] = resetValue;
            res = true;
        }
        //ImGui.PopFont();

        color.pop();

        ImGui.sameLine();

        res = res or ImGui.dragFloat("##$label", values, 0.1f, 0.0f, 0.0f, "%.2f")

        ImGui.popItemWidth();

        return res;
    }


}

data class ButtonColorSet(
    val base: Color,
    val hover: Color,
    val pressed: Color
){
    fun push(){
        ImGui.pushStyleColor(ImGuiCol.Button, base.r, base.g, base.b, base.a);
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, hover.r, hover.g, hover.b, hover.a);
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, pressed.r, pressed.g, pressed.b, pressed.a);
    }

    fun pop(){
        ImGui.popStyleColor(3);
    }
}