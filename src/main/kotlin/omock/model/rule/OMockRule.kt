package omock.model.rule

import omock.model.Column
import omock.model.ColumnStates
import omock.model.Direction
import omock.model.Node
import omock.model.Result
import omock.model.Row
import omock.model.state.Clear
import omock.model.state.Stone
import omock.model.state.StoneState

interface OMockRule {
    fun loadMap(
        stoneStates: List<ColumnStates>,
        stone: Stone,
    ): Map<Direction, Result> {
        val row = stone.row.getIndex()
        val column = stone.column.getIndex()
        val node = Node(x = column, y = row)
        val visited = mutableMapOf<Direction, Result>()
        val playerState = stoneStates[row].getStoneState(column)

        Direction.entries.forEach { direction ->
            visited[direction] = bfs(stoneStates, node, direction, playerState)
        }
        return visited
    }

    private fun bfs(
        stoneStates: List<ColumnStates>,
        node: Node,
        direction: Direction,
        playerState: StoneState,
    ): Result {
        val queue = ArrayDeque<Node>().apply { add(node) }
        var isClear = false
        var isLastClear = true
        var count = 0
        var flag = false

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            val (nx, ny) = current.x + direction.y to current.y + direction.x

            if (!(nx in Column.MIN_COLUMN_INDEX..Column.MAX_COLUMN_INDEX && ny in Row.MIN_ROW_INDEX..Row.MAX_ROW_INDEX)) continue

            val nextState = stoneStates[ny].getStoneState(nx)::class

            if (!flag) {
                flag = true
                when (nextState) {
                    playerState::class -> {
                        queue.addFirst(Node(nx, ny))
                        count++
                    }

                    Clear::class -> {
                        queue.addFirst(Node(nx, ny))
                        isClear = true
                    }
                }
                continue
            }

            when (nextState) {
                playerState::class -> {
                    count++
                    queue.addFirst(Node(nx, ny))
                }

                Clear::class -> isLastClear = true
                else -> isLastClear = false
            }
        }

        return Result(count, isClear, isLastClear)
    }

    fun judgementResult(visited: Map<Direction, Result>): Boolean

    companion object {
        const val INIT_COUNT = 0
        const val MIN_FOUR_TO_FOUR_COUNT = 4
        const val MIN_IS_CLEAR_FOUR_TO_FOUR_COUNT = 4
        const val MIN_THREE_TO_THREE_COUNT = 4
        const val MIN_REVERSE_COUNT = 0
        const val MIN_O_MOCK_COUNT = 4
        const val EDGE_THREE_TO_THREE_COUNT = 2
        const val EDGE_FOUR_TO_FOUR_COUNT = 3
    }
}
