package engine.registry

object Registries {

    val regs = Registry<Registry<*>>(ResourceName("core","regs"))

    fun addRegistry(name:ResourceName, reg:Registry<*>){
        regs.register(name, reg);
    }

    fun <T : IRegistryEntry> makeRegistry(name:ResourceName): Registry<T>{
        val reg = Registry<T>(name);

        addRegistry(name,reg);

        return reg;
    }

}