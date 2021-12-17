package day16

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Day16Test {

    @Test
    internal fun packetToBinary() {
        val result = PacketLiteral(6, 4, 2021).toString()
        val expected = "110100101111111000101"
        assertThat(result).isEqualTo(expected)
    }

    @Test
    internal fun packetOperatorToBinary() {
        val result = PacketOperator(
            version = 0,
            type = 0,
            lengthTypeID = 1,
            value = 0,
            packets = listOf()
        ).toString()
        val expected = "000000100000000000"
        assertThat(result).isEqualTo(expected)
    }

    @Test
    internal fun packetOperatorToBinaryAdvanced() {
        val result = PacketOperator(
            version = 0,
            type = 0,
            lengthTypeID = 1,
            value = 0,
            packets = listOf(
                PacketLiteral(6, 4, 2021)
            )
        ).toString()
        val expected = "000000100000000000" + "110100101111111000101"
        assertThat(result).isEqualTo(expected)
    }
}