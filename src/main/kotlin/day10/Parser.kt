package day10

import java.util.*


fun Char.isOpeningChar(): Boolean = Parser.openingChars.contains(this)

fun Char.isClosingChar(): Boolean = Parser.closeingChars.contains(this)

object Parser {

    val openingChars = setOf('(', '{', '<', '[')
    val closeingChars = setOf(')', '}', '>', ']')

    val startEndMap = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>'
    )

    val allChars = setOf(openingChars).plus(closeingChars)

    fun parse(input: LinkedList<Char>, ignoreErrors: Boolean = false): List<Node> {
        val nodes: MutableList<Node> = mutableListOf()

        var openActive = false

        do {
            if(input.isEmpty()) {
                break
            }
            val char = input.peek()

            if (char.isOpeningChar()) {
                input.remove()
                openActive = true
                val node = Node(char)
                nodes.add(node)

                node.addNodes(parse(input, ignoreErrors))

                continue
            }

            if (char.isClosingChar()) {
                if (openActive) {
                    val latestNode = nodes.last()
                    val matchingChar = startEndMap[latestNode.charType] ?: throw IllegalStateException("Map failed")

                    if (char != matchingChar) {
                        if(!ignoreErrors) {
                            throw IllegalClosingCharException(char, matchingChar)
                        }
                    } else {
                        input.remove()
                        openActive = false
                    }
                } else {
                    return nodes
                }
            }

        } while (input.isNotEmpty())

        return nodes
    }

    class IllegalClosingCharException(val incorrectChar: Char, matchingChar: Char?) :
        Exception("Provided closing char '$incorrectChar' is incorrect, valid char should be $matchingChar")

    class Node(val charType: Char) {

        private val _childNodes: MutableList<Node> = mutableListOf()
        @Suppress("MemberVisibilityCanBePrivate")
        val childNodes: List<Node>
            get() = _childNodes

        fun addNodes(nodes: Collection<Node>) = _childNodes.addAll(nodes)

        override fun toString(): String {
            return "$charType${childNodes.joinToString(separator = "")}${startEndMap[charType]}"
        }
    }
}