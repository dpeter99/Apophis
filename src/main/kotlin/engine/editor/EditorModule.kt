package engine.editor

import engine.ImGuiLayer
import engine.editor.inspectors.InspectorSystem
import engine.entitysystem.Entity
import engine.eventbus.SyncEventBus
import engine.modules.ApplicationModule

class EditorModule : ApplicationModule {

    val windows = mutableListOf<EditorWindow>()

    var selected_ent: Entity? = null;
    var selected_inspector: Entity? = null;


    init {
        windows.add(Hierarchy(this));
        windows.add(Inspector(this));
        windows.add(SceneView(this));

        SyncEventBus.MAIN.subscribeTo(ImGuiLayer.OnGUIEvent::class.java,false,this::onGui)
    }

    override fun Init() {
        super.Init();
        InspectorSystem.init();
    }

    fun onGui(event: ImGuiLayer.OnGUIEvent) {
        super.Render()
        for (win in windows){
            win.onGui();
        }
    }

    override fun dispose() {
        //TODO("Not yet implemented")
    }

}

