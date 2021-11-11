package engine.editor

import imgui.ImGui
import imgui.flag.ImGuiStyleVar

class SceneView(editor: EditorModule) : EditorWindow(editor) {

    override fun onGui() {

        ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0.0f, 0.0f);

        ImGui.begin("Scene")

        val rect = ImGui.getContentRegionAvail()

        if(EditorCore.Instance.fb.width != rect.x.toInt() ||
            EditorCore.Instance.fb.height != rect.y.toInt()) {
            EditorCore.Instance.resizeFB(rect.x.toInt(), rect.y.toInt());
        }

        ImGui.image(EditorCore.Instance.fb.colorBufferTexture.textureObjectHandle,rect.x,rect.y,0F,1f,1f,0f);


        ImGui.end()

        ImGui.popStyleVar();
    }

}