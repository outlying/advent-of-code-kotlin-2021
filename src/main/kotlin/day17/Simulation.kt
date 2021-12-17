package day17

class Simulation(velocity: Vector, private val area: Area) {

    private var _probe: Probe = Probe(velocity)
    val probe: Probe
        get() = _probe

    fun step() {
        _probe.moveByStep()
    }

    fun passedArea(): Boolean {
        return probe.position.x > area.xRange.last || probe.position.y < area.yRange.first
    }

    fun isInTarget(): Boolean {
        return probe.position.x in area.xRange && probe.position.y in area.yRange
    }
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
    return (this.first + other.first) to (this.second + other.second)
}
