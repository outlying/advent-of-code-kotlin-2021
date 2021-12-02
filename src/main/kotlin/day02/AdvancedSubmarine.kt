package day02

class AdvancedSubmarine(
    private val initDepth: Int = 0,
    private val initForward: Int = 0,
    private val initAim: Int = 0
) : ISubmarine {
    private var _depth = initDepth
    private var _forward = initForward
    private var _aim = initAim

    override fun forward(amount: Int) {
        _forward += amount
        _depth += amount * _aim
    }

    override fun down(amount: Int) {
        _aim += amount
    }

    override fun up(amount: Int) {
        _aim -= amount
    }

    override fun distance(): Int {
        return (_depth - initDepth) * (_forward - initForward)
    }
}