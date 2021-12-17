package day16

import com.google.common.truth.Truth
import readInput

fun main() {

    fun part1(input: List<String>): Int {

        val packets = Transmission.buildPackets(input[0])

        return packets.sumVersions()
    }

    fun part1(input: String) = part1(listOf(input))

    fun part2(input: List<String>): Long {
        return Transmission.buildPackets(input[0]).calculate()
    }

    fun part2(input: String) = part2(listOf(input))

    val testInput = readInput("Day16_test")

    Truth.assertThat(part1("D2FE28")).isEqualTo(6)
    Truth.assertThat(part1("38006F45291200")).isEqualTo(9)
    Truth.assertThat(part1("EE00D40C823060")).isEqualTo(7 + 2 + 4 + 1)
    Truth.assertThat(part1("8A004A801A8002F478")).isEqualTo(16)
    Truth.assertThat(part1("620080001611562C8802118E34")).isEqualTo(12)
    Truth.assertThat(part1("C0015000016115A2E0802F182340")).isEqualTo(23)
    Truth.assertThat(part1("A0016C880162017C3686B18A3D4780")).isEqualTo(31)

    Truth.assertThat(part2("C200B40A82")).isEqualTo(3)
    Truth.assertThat(part2("04005AC33890")).isEqualTo(54)
    Truth.assertThat(part2("880086C3E88112")).isEqualTo(7)
    Truth.assertThat(part2("CE00C43D881120")).isEqualTo(9)
    Truth.assertThat(part2("D8005AC2A8F0")).isEqualTo(1)
    Truth.assertThat(part2("F600BC2D8F")).isEqualTo(0)
    Truth.assertThat(part2("9C005AC2F8F0")).isEqualTo(0)
    Truth.assertThat(part2("9C0141080250320F1802104A08")).isEqualTo(1)

    val input = readInput("Day16")
    println(part1(input))
    println(part2(input))
}
