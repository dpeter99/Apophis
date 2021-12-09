package engine

interface IEngineCore {
    var screenwidth: Int
    var screenheight: Int
    fun create()
    fun Init()
    fun render()
    fun dispose()
    fun resize(width: Int, height: Int)
}