package woowacourse.omok.model.rule

import woowacourse.omok.model.board.ColumnStates
import woowacourse.omok.model.position.Column
import woowacourse.omok.model.position.Row
import woowacourse.omok.model.search.Direction
import woowacourse.omok.model.search.DirectionFirstClearResult
import woowacourse.omok.model.search.DirectionResult
import woowacourse.omok.model.search.Node
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stonestate.Black
import woowacourse.omok.model.stonestate.Clear
import woowacourse.omok.model.stonestate.StoneState
import woowacourse.omok.model.stonestate.White

class LoadMap(private val stoneStates: List<ColumnStates>) : LoadMapInterface {
    override fun loadMap(stone: Stone): Map<Direction, DirectionResult> {
        val node = getCurrentNode(stone)
        val visited = mutableMapOf<Direction, DirectionResult>()
        val playerState = stoneStates[node.y].getStoneState(node.x)

        Direction.entries.forEach { direction ->
            visited[direction] = getResultWithDirection(node, direction, playerState)
        }
        return visited
    }

    override fun firstClearLoadMap(stone: Stone): Map<Direction, DirectionFirstClearResult> {
        val node = getCurrentNode(stone)
        val visited = mutableMapOf<Direction, DirectionFirstClearResult>()
        Direction.entries.forEach { direction ->
            visited[direction] = getResultWithDirectionFirstClear(node, direction)
        }
        return visited
    }

    override fun getCurrentNode(stone: Stone): Node {
        val row = stone.getRowIndex()
        val column = stone.getColumnIndex()
        return Node(x = column, y = row)
    }

    private fun getResultWithDirectionFirstClear(
        node: Node,
        direction: Direction,
    ): DirectionFirstClearResult {
        val (nx, ny) = node.x + direction.y to node.y + direction.x
        if (!isBoardIndex(nx, ny)) return DirectionFirstClearResult(false)
        val nextState = stoneStates[ny].getStoneState(nx)
        return DirectionFirstClearResult(nextState is Clear)
    }

    private fun getResultWithDirection(
        node: Node,
        direction: Direction,
        playerState: StoneState,
    ): DirectionResult {
        val queue = ArrayDeque<Node>().apply { add(node) }
        var isLastClear = true
        var count = 0
        var flag = false

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            val (nx, ny) = current.x + direction.y to current.y + direction.x
            if (!isBoardIndex(nx, ny)) continue
            val nextState = stoneStates[ny].getStoneState(nx)

            fun addFindState() {
                queue.addFirst(Node(nx, ny))
                count++
            }

            fun queueNextNode() {
                queue.addFirst(Node(nx, ny))
            }

            fun updateIsLastClear(value: Boolean) {
                isLastClear = value
            }

            fun updateNextClearState() {
                if (flag) {
                    updateIsLastClear(true)
                } else {
                    queueNextNode()
                }
            }

            fun updateNextState() {
                if (playerState.isSameState(nextState)) {
                    addFindState()
                } else {
                    updateIsLastClear(!flag)
                }
            }

            when (nextState) {
                is Black, is White -> updateNextState()
                is Clear -> updateNextClearState()
            }
            flag = true
        }

        return DirectionResult(count, isLastClear)
    }

    private fun isBoardIndex(
        nx: Int,
        ny: Int,
    ): Boolean {
        return nx in Column.MIN_COLUMN_INDEX..Column.MAX_COLUMN_INDEX && ny in Row.MIN_ROW_INDEX..Row.MAX_ROW_INDEX
    }
}
