package omock.model.board

import omock.model.PlaceResult
import omock.model.Position
import omock.model.Stone
import omock.model.Vector
import omock.model.rule.OmokGameRule

class OmokBoard internal constructor(
    board: Blocks,
    val blockRecords: Blocks,
    val size: BoardSize,
    private val rule: OmokGameRule,
) {
    private val board: Map<Position, BlockState> = board.toBlockStateMap()

    fun placeStone(
        position: Position,
        stone: Stone,
    ): PlaceResult {
        return rule.placeStone(position, stone, this)
    }

    fun updateBoard(block: Block): OmokBoard {
        return buildOmokBoard {
            size(size)
            blocks(blockRecords + block)
            rule(rule)
        }
    }

    fun hasOmok(): Boolean = blockRecords.blocks.any { isInOmok(it.position) }

    fun isInOmok(position: Position): Boolean {
        val state = board.getOrDefault(position, BlockState.EMPTY)
        if (state == BlockState.EMPTY) return false
        val block = Block(position, state)
        return Vector.FOUR_DIRECTIONS.any { vector ->
            isInOmokAroundPosition(block, vector)
        }
    }

    fun lastBlockOrNull(): Block? = blockRecords.blocks.lastOrNull()

    private fun isInOmokAroundPosition(
        block: Block,
        vector: Vector,
    ): Boolean {
        return sumSameStone(block, vector) + sumSameStone(block, -vector) >= OMOK_THRESHOLD
    }

    private fun sumSameStone(
        block: Block,
        vector: Vector,
    ): Int {
        val (position, state) = block
        var now = position
        var count = INITIAL_COUNT
        for (i in OMOK_CANDIDATE_RANGE) {
            now += vector
            if (board[now] != state) break
            count++
        }
        return count
    }

    fun toDoubleList(): List<List<Block>> {
        return (size.range()).map { x ->
            (size.range()).map { y ->
                val state = board[Position(x, y)] ?: BlockState.EMPTY
                Block(Position(x, y), state)
            }
        }
    }

    companion object {
        private const val INITIAL_COUNT = 0
        private const val OMOK_THRESHOLD = 4
        private val OMOK_CANDIDATE_RANGE = 0..3
    }
}
