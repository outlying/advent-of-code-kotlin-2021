package day12

class System(input: List<String>, private val simple: Boolean = true) {

    val caves: MutableMap<String, Cave> = mutableMapOf()

    init {
        input
            .map { it.split("-") }
            .onEach {
                val nameA = it[0]
                val nameB = it[1]
                val caveA = caves.getOrPut(nameA) { Cave(nameA) }
                val caveB = caves.getOrPut(nameB) { Cave(nameB) }

                caveA.addConnection(caveB)
            }
    }

    val startCave = caves["start"] ?: throw IllegalStateException("No start cave")
    val endCave = caves["end"] ?: throw IllegalStateException("No end cave")

    fun getAllRoutes(): List<List<Cave>> {
        return pathsSimple(listOf(startCave), mutableMapOf())
    }

    fun getAllAdvancedRoutes(): List<List<Cave>> {
        return pathsAdvanced(listOf(startCave), mutableMapOf())
    }

    fun pathsSimple(roadSoFar: List<Cave>, visits: MutableMap<Cave, Int>): List<List<Cave>> {
        val lastVisited = roadSoFar.lastOrNull() ?: throw IllegalStateException("We should have last visited")
        visits[lastVisited] = visits.getOrDefault(lastVisited, 0) + 1

        val excluded = visits.filter { (cave, visits) -> cave.isSmall && visits > 0 }.keys
        val localExcluded: Set<Cave> = mutableSetOf<Cave>().plus(excluded).plus(roadSoFar.toSet().filter { it.isSmall })

        if (lastVisited == endCave) {
            return listOf(roadSoFar)
        }

        return lastVisited.connected
            .filter { !localExcluded.contains(it) }
            .flatMap {
                pathsSimple(roadSoFar.plus(it), visits.toMutableMap())
            }
    }

    fun pathsAdvanced(roadSoFar: List<Cave>, visits: MutableMap<Cave, Int>): List<List<Cave>> {
        val lastVisited = roadSoFar.lastOrNull() ?: throw IllegalStateException("We should have last visited")
        visits[lastVisited] = visits.getOrDefault(lastVisited, 0) + 1

        val excluded = mutableSetOf<Cave>()

        val anySmallCaveVisitedTwice = visits.filter { (cave, visits) -> cave.isSmall && visits == 2 }.keys
        if (anySmallCaveVisitedTwice.isNotEmpty()) {
            excluded.addAll(anySmallCaveVisitedTwice)
            excluded.addAll(visits.filter { (cave, visits) -> cave.isSmall && visits == 1 }.keys)
        }

        if (visits[startCave] == 1) {
            excluded.add(startCave)
        }
        if (visits[endCave] == 1) {
            excluded.add(startCave)
        }

        if (lastVisited == endCave) {
            return listOf(roadSoFar)
        }

        return lastVisited.connected
            .filter { !excluded.contains(it) }
            .flatMap {
                pathsAdvanced(roadSoFar.plus(it), visits.toMutableMap())
            }
    }
}