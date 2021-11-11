package engine.editor.inspectors

import com.badlogic.gdx.graphics.Color
import engine.editor.EditorCore
import engine.entitysystem.Entity
import engine.entitysystem.SceneEntity
import glm_.vec2.Vec2
import imgui.ImGui
import imgui.ImVec2
import imgui.extension.imguizmo.ImGuizmo
import imgui.flag.ImGuiCol
import imgui.flag.ImGuiStyleVar
import imgui.type.ImFloat
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
                    if(DrawFloatControl(f.name,rot, showButtons = false)){
                        if(f is KMutableProperty<*>)
                            f.setter.call(ent,rot.get());
                    }

                }

                ImGui.endDisabled();

                ImGui.text(f.name);
            }
        }

    }

    fun DrawFloatControl(label: String, values: ImFloat, resetValue: Float = 0.0f, columnWidth: Float = 100.0f, showButtons : Boolean=true): Boolean {
        val io = ImGui.getIO();
        //var boldFont = io.fonts. ->Fonts[0];

        ImGui.pushID(label);

        ImGui.columns(2);
        ImGui.setColumnWidth(0, columnWidth);
        ImGui.text(label);
        ImGui.nextColumn();

        val w = ImGui.calcItemWidth();


        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 0f, ImGui.getStyle().itemSpacingY);

        //val x: FloatArray = floatArrayOf(values.)
        ImGui.setNextItemWidth(-1f);
        val res = internal_float_field("X", w, values.data, resetValue, red, showButtons)
            //values.element = x[0];

        ImGui.popStyleVar();

        ImGui.columns(1);

        ImGui.popID();

        return res;
    }

    fun DrawVec2Control(label: String, values: Vec2, resetValue: Float = 0.0f, columnWidth: Float = 100.0f, showButtons : Boolean=true): Boolean {
        val io = ImGui.getIO();
        //var boldFont = io.fonts. ->Fonts[0];

        ImGui.pushID(label);

        ImGui.columns(2);
        ImGui.setColumnWidth(0, columnWidth);
        ImGui.text(label);
        ImGui.nextColumn();

        ImGui.setNextItemWidth(-1f)
        val w = ImGui.calcItemWidth()/2;

        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 0f, ImGui.getStyle().itemSpacingY);

        var res = false;

        val x: FloatArray = floatArrayOf(values.x)
        if(internal_float_field("X",w, x,resetValue, red, showButtons)) {
            values.x = x[0];
            res = true;
        }
        ImGui.sameLine(0f,ImGui.getStyle().itemInnerSpacingX);



        val y: FloatArray = floatArrayOf(values.y)
        if(internal_float_field("Y", w, y, resetValue, green, showButtons)) {
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
        color: ButtonColorSet,
        showButtons: Boolean
    ): Boolean {
        val lineHeight = ImGui.getFont().fontSize + ImGui.getStyle().framePaddingY * 2.0f;
        val buttonSize = ImVec2(lineHeight + 3.0f, lineHeight);

        ImGui.pushItemWidth(w);

        var res = false;

        if(showButtons) {
            rect_button(label,buttonSize,color){
                values[0] = resetValue;
                res = true;
            }
        }
        else{
            ImGui.dummy(buttonSize.x, buttonSize.y)
        }

        ImGui.sameLine();

        //res = res or ImGui.dragFloat("##$label", values, 0.1f, 0.0f, 0.0f, "%.2f")
        res = res or ImGui.dragFloat("##$label", values)

        ImGui.popItemWidth();

        return res;
    }
}

fun rect_button(label: String,buttonSize:ImVec2, color: ButtonColorSet, press: ()->Unit){
    color.push();

    if (ImGui.button(label, buttonSize.x, buttonSize.y)) {
        press();
    }

    color.pop();

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