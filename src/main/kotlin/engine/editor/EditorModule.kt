package engine.editor

import engine.modules.ApplicationModule
import engine.OnGUIEvent
import engine.entitysystem.Entity
import engine.eventbus.SyncEventBus

class EditorModule : ApplicationModule {

    val windows = mutableListOf<EditorWindow>()

    var selected_ent: Entity? = null;
    var selected_inspector: Entity? = null;


    init {
        windows.add(Hierarchy(this));
        windows.add(Inspector(this));
        windows.add(SceneView(this));

        SyncEventBus.MAIN.subscribeTo(OnGUIEvent::class.java,false,this::onGui)
    }

    fun onGui(event:OnGUIEvent) {
        super.Render()
        for (win in windows){
            win.onGui();
        }
    }

    override fun dispose() {
        //TODO("Not yet implemented")
    }

}

abstract class EditorWindow(val editor:EditorModule) {

    abstract fun onGui();

}
