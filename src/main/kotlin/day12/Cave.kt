package day12

data class Cave(val name: String) {

    val isSmall = name.all { it.isLowerCase() }

    private val _connected: MutableSet<Cave> = mutableSetOf()
    val connected
        get() = _connected

    fun addConnection(otherCave: Cave) {
        if (!_connected.contains(otherCave)) {
            _connected.add(otherCave)
        }
        if (!otherCave._connected.contains(this)) {
            otherCave._connected.add(this)
        }
    }

    override fun toString(): String {
        return name
    }
}