package day17

import java.lang.IllegalStateException

class Prober(input: String) {

    val area: Area

    init {
        val match = REGEXP.matchEntire(input) ?: throw IllegalArgumentException("Invalid input")

        with(match.groupValues) {
            area = (get(1).toInt()..get(2).toInt()) to (get(3).toInt()..get(4).toInt())
        }
    }

    fun simulation(velocity: Vector): Simulation {
        return Simulation(velocity, area)
    }

    fun minXVelocityToReachArea(): Int {
        for(vX in 0..area.xRange.first) {
            val traveled = (vX downTo 0).sum()
            if(traveled >= area.xRange.first) {
                return vX
            }
        }
        throw IllegalStateException("Impossible for some reason")
    }

    fun calculateOptimalXVelocity(): Int {
        for(vX in 0..area.xRange.last) {
            val xTravel = (vX downTo 0).fold(0) { acc, i -> acc + i }
            if(xTravel in area.xRange) {
                return vX
            }
        }
        throw IllegalStateException("Unable to find X velocity")
    }

    fun calculateOptimalYVelocity(): Int {
        for(vX in area.yRange) {
        }
        throw IllegalStateException("Unable to find X velocity")
    }

    companion object {

        private val REGEXP = "target area: x=(-?\\d+)\\.\\.(-?\\d+), y=(-?\\d+)\\.\\.(-?\\d+)".toRegex()
    }
}