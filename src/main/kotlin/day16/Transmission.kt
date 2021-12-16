package day16

object Transmission {

    fun buildPackets(input: String): Packet {

        val binaryString = input.map { map[it] }.joinToString(separator = "")

        val version = binaryString.substring(0, 3).toInt(2)
        when (val type = binaryString.substring(3, 6).toInt(2)) {
            4 -> {

                var lastFound = false

                val windows = binaryString
                    .drop(6)
                    .windowed(5, 5, false)
                    .takeWhile {
                        val isLastGroup = it[0] == '0'

                        if (!isLastGroup) {
                            true
                        } else {
                            if (lastFound) {
                                false
                            } else {
                                lastFound = true
                                true
                            }
                        }
                    }

                val literal = windows.map { it.drop(1) }.joinToString("").toInt(2)

                return PacketLiteral(
                    version,
                    type,
                    literal
                )
            }
            else -> {
                TODO("Operator")
            }
        }
    }

    private val map = mapOf(
        '0' to "0000",
        '1' to "0001",
        '2' to "0010",
        '3' to "0011",
        '4' to "0100",
        '5' to "0101",
        '6' to "0110",
        '7' to "0111",
        '8' to "1000",
        '9' to "1001",
        'A' to "1010",
        'B' to "1011",
        'C' to "1100",
        'D' to "1101",
        'E' to "1110",
        'F' to "1111",
    )
}