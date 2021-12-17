package day16

sealed interface Packet {

    val version: Int
    val type: Int

    interface Operator : Packet {
        val lengthTypeID: Int
        val value: Int
        val packets: List<Packet>
    }

    interface Literal : Packet {
        val literal: Long
    }
}

fun Packet.calculate(): Long {

    return when (this) {
        is Packet.Literal -> this.literal
        is Packet.Operator -> {
            when (this.type) {
                0 -> {
                    packets.sumOf { it.calculate() }
                }
                1 -> {
                    packets.fold(1L) { acc, packet -> acc * packet.calculate() }
                }
                2 -> {
                    packets.map { it.calculate() }.minOrNull() ?: throw IllegalStateException("Minimum not found")
                }
                3 -> {
                    packets.map { it.calculate() }.maxOrNull() ?: throw IllegalStateException("Maximum not found")
                }
                5 -> {
                    if(packets[0].calculate() > packets[1].calculate()) 1 else 0
                }
                6 -> {
                    if(packets[0].calculate() < packets[1].calculate()) 1 else 0
                }
                7 -> {
                    if(packets[0].calculate() == packets[1].calculate()) 1 else 0
                }
                else -> throw IllegalStateException("Illegal operation type ${this.type}")
            }
        }
    }
}

data class PacketLiteral(
    override val version: Int,
    override val type: Int,
    override val literal: Long,
) : Packet.Literal {

    override fun toString(): String {

        val result = StringBuilder()

        var literalBinary = java.lang.Long.toBinaryString(literal)

        var modFour = literalBinary.length % 4
        if (modFour != 0) {
            literalBinary = literalBinary.padStart(literalBinary.length + (4 - modFour), '0')
        }

        val windows = literalBinary.windowed(4, 4)
        literalBinary = windows
            .mapIndexed { index, item ->
                if (index == windows.lastIndex) {
                    "0$item"
                } else {
                    "1$item"
                }
            }
            .joinToString("")

        result.append(Integer.toBinaryString(version).padStart(3, '0'))
        result.append(Integer.toBinaryString(type).padStart(3, '0'))
        result.append(literalBinary)

        modFour = result.length % 4
        if (modFour != 0) {
            //result.append("0".repeat(4 - modFour))
        }

        return result.toString()
    }
}

data class PacketOperator(
    override val version: Int,
    override val type: Int,
    override val lengthTypeID: Int,
    override val value: Int,
    override val packets: List<Packet>
) : Packet.Operator {

    override fun toString(): String {

        val result = StringBuilder()

        result.append(Integer.toBinaryString(version).padStart(3, '0'))
        result.append(Integer.toBinaryString(type).padStart(3, '0'))
        result.append(lengthTypeID)

        result.append(Integer.toBinaryString(value).padStart(if (lengthTypeID == 0) 15 else 11, '0'))

        result.append(packets.joinToString(""))

        return result.toString()
    }
}


fun Packet.sumVersions(): Int {

    return when (this) {
        is Packet.Operator -> {
            this.version + this.packets.sumOf { it.sumVersions() }
        }
        is Packet.Literal -> {
            this.version
        }
    }
}