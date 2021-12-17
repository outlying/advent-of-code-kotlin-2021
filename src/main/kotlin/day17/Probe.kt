package day17

class Probe(
    velocity: Vector,
    position: Point = 0 to 0,
) {

    private var _velocity = velocity
    val velocity
        get() = _velocity

    private var _position = position
    val position
        get() = _position

    fun moveByStep() {
        _position += velocity
        _velocity = velocity.copy(
            first = when {
                velocity.x > 0 -> velocity.x - 1
                velocity.first < 0 -> velocity.first + 1
                velocity.first == 0 -> 0
                else -> throw IllegalStateException("Something unexpected")
            },
            second = velocity.second - 1
        )
    }
}