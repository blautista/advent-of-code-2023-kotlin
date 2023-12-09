package day8

import lcm
import readFile

typealias NodeNetwork = Map<String, Node>

data class Node(val key: String, val left: String, val right: String)
data class NavigationMap(val directions: String, val nodes: NodeNetwork)

fun parseInput(input: String): NavigationMap {
    val (directions, nodes) = input.split("${System.lineSeparator()}${System.lineSeparator()}")

    val nodeNetwork = mutableMapOf<String, Node>()

    for (node in nodes.lines()) {
        val (key, values) = node.split(" = ")
        val left = values.substring(1, 4)
        val right = values.substring(6, 9)
        nodeNetwork[key] = Node(key, left, right)
    }

    return NavigationMap(directions, nodeNetwork)
}

class DirectionGenerator(private val directions: String, startingIndex: Int = 0) {
    private var directionIndex = startingIndex

    fun getNextDirection(): Char {
        val current = directionIndex
        directionIndex = if (directionIndex < directions.length - 1) directionIndex + 1 else 0
        return directions[current]
    }
}

fun findNodeStepsToTarget(startingNode: Node, map: NavigationMap, getIsTargetNode: (node: Node) -> Boolean): Int {
    val (directions, nodes) = map
    var currentNode = startingNode
    var steps = 0
    val directionGenerator = DirectionGenerator(directions)

    while (!getIsTargetNode(currentNode)) {
        val direction = directionGenerator.getNextDirection()

        currentNode = when (direction) {
            'L' -> nodes[currentNode.left]!!
            'R' -> nodes[currentNode.right]!!
            else -> throw Exception("invalid direction")
        }

        steps++
    }

    return steps
}

fun solve1(input: NavigationMap): Int {
    return findNodeStepsToTarget(input.nodes["AAA"]!!, input, getIsTargetNode = { it.key == "ZZZ" })
}

fun solve2(input: NavigationMap): Long {
    val startingNodes = input.nodes.filter { it.key.endsWith('A') }.values

    val stepLengths = startingNodes.map {
        findNodeStepsToTarget(
            it,
            input,
            getIsTargetNode = { node -> node.key.endsWith('Z') }).toLong()
    }.toLongArray()

    return lcm(*stepLengths)
}

fun main() {
    val inputString = readFile("src\\main\\kotlin\\day8\\input.txt")
    val input = parseInput(inputString)

    println(solve1(input))
    println(solve2(input))
}