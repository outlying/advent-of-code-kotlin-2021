package day16

import com.google.common.truth.Truth
import readInput

fun main() {

    fun part1(input: List<String>): Int {

        val packets = Transmission.buildPackets(input[0])

        return packets.sumVersions()
    }

    fun part1(input: String) = part1(listOf(input))

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day16_test")

    Truth.assertThat(part1("D2FE28")).isEqualTo(6)
    Truth.assertThat(part1("8A004A801A8002F478")).isEqualTo(16)
    Truth.assertThat(part1("620080001611562C8802118E34")).isEqualTo(12)
    Truth.assertThat(part1("C0015000016115A2E0802F182340")).isEqualTo(23)
    Truth.assertThat(part1("A0016C880162017C3686B18A3D4780")).isEqualTo(31)

    Truth.assertThat(part2(testInput)).isEqualTo(0)

    val input = readInput("Day16")
    println(part1(input))
    println(part2(input))
}
