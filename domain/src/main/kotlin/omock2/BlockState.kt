package omock2

enum class BlockState {
    BLACK_STONE,
    WHITE_STONE,
    OMOK_STONE,
    EMPTY,
    FORBIDDEN,
}

// data class Block(val position: Position, val state: BlockState)
//
// // ## Blocks
// //- blocks : Map<Position, boxState>
// //- fun toList(): List<List<boxState>>
// //- fun hasOmok(): 오목이 있는지 확인한다.
//
// @JvmInline
// value class Blocks(val blocks: Map<Position, BlockState>) {
//
//    val positions: Set<Position> get() = blocks.keys
//
//    constructor(size: BoardSize, blocks: List<Block> = emptyList()) : this(
//        createBoxes(
//            size.width,
//            blocks
//        )
//    )
//
//    fun placeStone(
//        position: Position,
//        color: StoneColor,
//    ): Board {
//        if (isInOmok(position))
//            return Board(size, boxes + (position to stone))
//    }
//
//    fun isInOmok(position: Position): Boolean {
//        val stone = blocks[position] ?: return false
//        return Vector.FOUR_DIRECTIONS.any { vector ->
//            isInOmok(Block(position, stone), vector)
//        }
//    }
//
//    fun hasOmok(): Boolean = positions.any(::isInOmok)
//
//    private fun isInOmok(
//        block: Block,
//        vector: Vector,
//    ): Boolean {
//        return sumSameStone(block, vector) + sumSameStone(block, -vector) >= OMOK_THRESHOLD
//    }
//
//    private fun sumSameStone(
//        block: Block,
//        vector: Vector,
//    ): Int {
//        val (position, state) = block
//        var now = position
//        var count = INITIAL_COUNT
//        for (i in OMOK_CANDIDATE_RANGE) {
//            now += vector
//            if (blocks[now] != state) break
//            count++
//        }
//        return count
//    }
//
//    companion object {
//        private const val MIN = 1
//        private const val INITIAL_COUNT = 0
//        private const val OMOK_THRESHOLD = 4
//        private val OMOK_CANDIDATE_RANGE = 0..3
//
//        private fun createInitialBlocks(size: Int): Map<Position, BlockState> {
//            return List(size) { x ->
//                List(size) { y ->
//                    Position.of(x + 1, y + 1)
//                }
//            }.flatten().associateWith { BlockState.BLACK_STONE }
//        }
//
//        private fun createBoxes(size: Int, blocks: List<Block>): Map<Position, BlockState> {
//            if (blocks.isEmpty()) return createInitialBlocks(size)
//            return blocks.associate { it.position to it.state }
//        }
//    }
// }
