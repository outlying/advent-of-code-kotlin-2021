package day02

interface ISubmarine {
    fun forward(amount: Int)
    fun down(amount: Int)
    fun up(amount: Int)
    fun distance(): Int
}