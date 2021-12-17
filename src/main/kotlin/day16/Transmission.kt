package day16

object Transmission {

    fun buildPackets(input: String): Packet {
        val binaryString = input.map { map[it] }.joinToString(separator = "")
        return buildPacketsFromBinary(binaryString)
    }

    fun buildPacketsFromBinary(binaryString: String): Packet {

        val version = try {
            binaryString.substring(0, 3).toInt(2)
        } catch (e: Exception) {
            throw IllegalStateException("Failed version for $binaryString", e)
        }

        when (val type = binaryString.substring(3, 6).toInt(2)) {
            4 -> {

                val windows = binaryString
                    .drop(6)
                    .windowed(5, 5, false)

                val filteredWindows = mutableListOf<String>()
                var i = 0
                do {
                    var lastNotFound = true
                    val window = windows[i]

                    if (window[0] == '0') {
                        lastNotFound = false
                    }

                    filteredWindows.add(window)
                    i += 1
                } while (lastNotFound)

                val literal = filteredWindows.map { it.drop(1) }.joinToString("").toLong(2)

                return PacketLiteral(
                    version,
                    type,
                    literal
                )
            }
            else -> {

                val subpackets = mutableListOf<Packet>()
                val lengthTypeId = "${binaryString[6]}".toInt()

                var value = 0

                when (lengthTypeId) {
                    0 -> {
                        val subpacketsTotalLength = binaryString.substring(7, 22).toInt(2) // l:15
                        value = subpacketsTotalLength
                        var subpacketsFragment = binaryString.substring(22, 22 + subpacketsTotalLength)

                        var countedSumPackageLength = 0

                        while (countedSumPackageLength != subpacketsTotalLength) {
                            val packet = try {
                                buildPacketsFromBinary(subpacketsFragment)
                            } catch (e: Exception) {
                                throw IllegalStateException("Failed while building subpacket for $binaryString", e)
                            }
                            subpackets.add(packet)
                            val packetLength = packet.toString().length
                            countedSumPackageLength += packetLength
                            subpacketsFragment = subpacketsFragment.drop(packetLength)
                        }
                    }
                    1 -> {

                        val subpacketsCount = binaryString.substring(7, 18).toInt(2) // l:11
                        value = subpacketsCount
                        var subpacketsFragment = binaryString.drop(18)

                        for (i in 0 until subpacketsCount) {
                            val packet = try {
                                buildPacketsFromBinary(subpacketsFragment)
                            } catch (e: Exception) {
                                throw IllegalStateException(
                                    "Failed while building subpacket index $i (total: $subpacketsCount) for $binaryString",
                                    e
                                )
                            }

                            subpackets.add(packet)
                            val packetString = packet.toString()
                            subpacketsFragment = subpacketsFragment.drop(packetString.length)
                        }
                    }

                    else -> throw IllegalStateException("Incorrect length type ID $lengthTypeId")
                }

                return PacketOperator(
                    version = version,
                    type = type,
                    lengthTypeID = lengthTypeId,
                    value = value,
                    packets = subpackets,
                )
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