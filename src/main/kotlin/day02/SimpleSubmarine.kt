package day02

class SimpleSubmarine(
    private val initDepth: Int = 0,
    private val initForward: Int = 0
) : ISubmarine {
    private var _depth = initDepth
    private var _forward = initForward

    override fun forward(amount: Int) {
        _forward += amount
    }

    override fun down(amount: Int) {
        _depth += amount
    }

    override fun up(amount: Int) {
        _depth -= amount
    }

    override fun distance(): Int {
        return (_depth - initDepth) * (_forward - initForward)
    }
}