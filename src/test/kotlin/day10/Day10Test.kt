package day10

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import java.util.*

class Day10Test {

    val inputsValid = listOf(
        "[]",
        "[[]]",
        "[[][]]",
        "[[][][]]",
        "[[][[[[][]]][]]]",
        "[[[][][][]][[[[[][][[][[]]]]]]]]",
        "[<>]",
        "[<(()({}))[{<>}]>]",
        "[<>]",
        "[<>]"
    )
    val maxValidInputLegth = inputsValid.maxOf { it.length }

    val inputsCorrupted = listOf(
        "[}"
    )
    val maxinvalidInputLegth = inputsCorrupted.maxOf { it.length }


    @Test
    internal fun validInputs() {
        for (input in inputsValid) {
            val result = try {
                Parser.parse(LinkedList(input.toList()))
            } catch (e: Exception) {
                throw IllegalStateException("Error while parsing: $input", e)
            }
            val resultString = result.joinToString(separator = "")

            println("Input: ${input.padEnd(maxValidInputLegth, ' ')} Result: $resultString")

            assertThat(resultString).isEqualTo(input)
        }
    }

    @Test
    internal fun corruptedInputs() {
        val result = Parser.parse(LinkedList("[({(<(())[]>[[{[]{<()<>>".toList()))


        val resultString = result.joinToString(separator = "")

        println(resultString)

        assertThat(resultString).isEqualTo("[({(<(())[]>[[{[]{<()<>>" + "}}]])})]")
    }
}