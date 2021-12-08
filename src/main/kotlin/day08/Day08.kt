package day08

import com.google.common.truth.Truth.assertThat
import readInput

fun main() {

    class Entry(
        val uniqueSignalPatterns: List<String>,
        val fourDigitOutputValue: List<String>,
    ) {

        fun listChatSets(): List<Set<Char>> {
            return uniqueSignalPatterns
                .plus(fourDigitOutputValue)
                .map { it.toSet() }
        }
    }

    val allChars = "abcdefg".toSet()

    fun parseInput(input: List<String>): List<Entry> {
        return input.map { line ->
            val fSplit = line.split('|').map { it.trim() }
            Entry(
                fSplit[0].split(' '),
                fSplit[1].split(' ')
            )
        }
    }

    fun part1(input: List<String>): Int {
        val entries = parseInput(input)
        val uniqueDigitsSegmentsNumber = setOf(2, 4, 3, 7)

        return entries
            .flatMap { it.fourDigitOutputValue }
            .map { it.length }
            .count { uniqueDigitsSegmentsNumber.contains(it) }
    }

    fun part2(input: List<String>): Int {
        val entries = parseInput(input)
        var sum = 0

        for (entry in entries) {
            val charSets = entry.listChatSets()
            val s: MutableMap<Int, Char> = mutableMapOf()
            val n: MutableMap<Int, Set<Char>> = mutableMapOf()

            n[1] = charSets.find { it.size == 2 } ?: throw IllegalStateException("Not found")
            n[4] = charSets.find { it.size == 4 } ?: throw IllegalStateException("Not found")
            n[7] = charSets.find { it.size == 3 } ?: throw IllegalStateException("Not found")
            n[8] = charSets.find { it.size == 7 } ?: throw IllegalStateException("Not found")

            s[0] = n[7]!!.minus(n[1]!!).first()
            s[6] = charSets
                .asSequence()
                .distinct()
                .filter { it.size == 6 } // Looking for nines
                .filter { it.containsAll(n[4]!!) }
                .filter { it.contains(s[0]!!) }
                .map { it.minus(n[4]!!) }
                .map { it.minus(s[0]!!) }
                .filter { it.size == 1 }.first().first()

            n[9] = setOf(s[0]!!, s[6]!!).plus(n[4]!!)

            // Eight minus Nine - segment 4
            s[4] = n[8]!!.minus(n[9]!!).first()

            // Two is the only size 5 number with segment 4 on
            n[2] = charSets
                .asSequence()
                .distinct()
                .filter { it.size == 5 }
                .filter { it.contains(s[4]!!) }
                .first()

            s[3] = n[2]!!.minus(n[7]!!).minus(s[4]!!).minus(s[6]!!).first()

            n[3] = setOf(s[3]!!, s[6]!!).plus(n[7]!!)

            s[1] = n[9]!!.minus(n[2]!!).minus(n[1]!!).first()
            s[5] = n[1]!!.minus(n[2]!!).first()
            s[2] = allChars.minus(s.values).first()

            n[5] = setOf(s[0]!!, s[1]!!, s[3]!!, s[5]!!, s[6]!!)
            n[6] = setOf(s[0]!!, s[1]!!, s[3]!!, s[4]!!, s[5]!!, s[6]!!)
            n[0] = n[8]!!.minus(s[3]!!)

            val reversed = n.map { it.value to it.key }.toMap()

            val number = entry.fourDigitOutputValue
                .map { reversed[it.toSet()] ?: throw IllegalStateException("Missing mapping") }
                .joinToString(separator = "")
                .toInt()

            sum += number
        }

        return sum
    }

    val testInput = readInput("Day08_test")
    assertThat(part1(testInput)).isEqualTo(26)
    assertThat(part2(testInput)).isEqualTo(61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
