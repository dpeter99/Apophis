package engine.editor.inspectors

import engine.Bootstrap
import engine.entitysystem.CameraEntity
import engine.entitysystem.Entity
import engine.registry.Registries
import engine.registry.ResourceName
import engine.registry.SecondaryMappingRegistry

object InspectorSystem {

    //val inspectors = Registries.makeRegistry<Inspector>(ResourceName("engine","inspectors"));
    val inspectors = Registries.addRegistry(SecondaryMappingRegistry<Inspector,Class<*>>(ResourceName("engine","inspectors")));


    val DEFAULT by inspectors.deferredRegister(DefaultInspector(Bootstrap.resName("default")));
    val CAMERA by inspectors.deferredRegister(CameraInspector(Bootstrap.resName("camera"), CameraEntity::class.java));

    fun init(){

    }

    fun drawInspectorFor(ent: Entity){

        var insp = inspectors.get(ent::class.java)
        if(insp != null){
            insp.draw(ent);
        }
        else{
            DEFAULT.draw(ent);
        }

    }

}