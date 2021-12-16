package day16

sealed interface Packet {

    val version: Int
    val type: Int

    interface Operator : Packet {

        val packets: List<Packet>
    }

    interface Literal : Packet {

        val literal: Int
    }
}

data class PacketLiteral(
    override val version: Int,
    override val type: Int,
    override val literal: Int,
) : Packet.Literal

fun Packet.sumVersions(): Int {

    return when(this) {
        is Packet.Operator -> {
            this.version + this.packets.sumOf { it.sumVersions() }
        }
        is Packet.Literal -> {
            this.version
        }
    }
}