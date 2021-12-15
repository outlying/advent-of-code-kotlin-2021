package day14

import java.util.*

class Rules(input: List<String>) {

    private val template: String = input[0]
    private val insertions: Map<List<Char>, Char> = input.drop(2)
        .map { it.split(" -> ") }
        .associate { it[0].toList() to it[1].toCharArray().first() }

    private val rules: Map<String, String> = input.drop(2)
        .map { it.split(" -> ") }
        .associate { it[0] to it[1] }

    private var _polymer: LinkedList<Char> = LinkedList<Char>(template.toList())
    val polymer: LinkedList<Char>
        get() = _polymer

    fun step() {

        var i = 0

        while (i <= _polymer.size - 2) {
            val pair = listOf(_polymer[i], _polymer[i + 1])
            val insert = insertions[pair] ?: throw IllegalStateException()

            _polymer.add(i + 1, insert)
            i += 2
        }
    }


    fun check(steps: Int): Long {
        var mapped = mutableMapOf<String, Long>()
        for (i in 1 until template.length) {
            val pair = "${template[i - 1]}${template[i]}"
            mapped[pair] = (mapped[pair] ?: 0) + 1
        }

        repeat(steps) {
            val newMap = mutableMapOf<String, Long>()
            mapped.forEach { (k, v) ->
                newMap[k.first() + rules[k]!!] = (newMap[k.first() + rules[k]!!] ?: 0L) + v
                newMap[rules[k] + k.last()] = (newMap[rules[k] + k.last()] ?: 0L) + v
            }
            mapped = newMap
        }

        val groups = mutableMapOf<Char, Long>()
        mapped.forEach { (t, u) -> groups[t.first()] = (groups[t.first()] ?: 0) + u }
        groups[template.last()] = groups[template.last()]!! + 1
        return (groups.values.maxOrNull()!! - groups.values.minOrNull()!!)
    }
}