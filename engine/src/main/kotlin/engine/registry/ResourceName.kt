package engine.registry

class ResourceName(val namespace: String, val name: String) {


    override fun toString(): String {
        return "$namespace:$name";
    }
}