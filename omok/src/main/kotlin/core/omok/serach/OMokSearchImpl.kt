package core.omok.serach

import core.omok.Direction
import core.omok.OMokResult

object OMokSearchImpl : OMokSearch() {
    const val CLEAR_NUMBER = 0

    override fun loadMap(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Map<Direction, OMokResult> {
        val node = Node(x = column, y = row)
        val visited = mutableMapOf<Direction, OMokResult>()
        val playerState = stoneStates[row][column]

        Direction.entries.forEach { direction ->
            visited[direction] = bfs(stoneStates, node, direction, playerState)
        }

        return visited
    }

    private fun bfs(
        stoneStates: List<List<Int>>,
        node: Node,
        direction: Direction,
        currentStone: Int,
    ): OMokResult {
        val minColumn = 0
        val maxColumn = stoneStates.size - 1
        val minRow = 0
        val maxRow = stoneStates[0].size - 1

        val queue = ArrayDeque<Node>().apply { add(node) }
        var isClear = false
        var isLastClear = true
        var count = 0
        var flag = false

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            val (nx, ny) = current.x + direction.y to current.y + direction.x

            if (!(nx in minColumn..maxColumn && ny in minRow..maxRow)) continue

            val nextState = stoneStates[ny][nx]

            if (!flag) {
                flag = true
                when (nextState) {
                    currentStone -> {
                        queue.addFirst(Node(nx, ny))
                        count++
                    }

                    CLEAR_NUMBER -> {
                        queue.addFirst(Node(nx, ny))
                        isClear = true
                    }
                }
                continue
            }

            when (nextState) {
                currentStone -> {
                    count++
                    queue.addFirst(Node(nx, ny))
                }

                CLEAR_NUMBER -> isLastClear = true
                else -> isLastClear = false
            }
        }

        return OMokResult(count, isClear, isLastClear)
    }
}
