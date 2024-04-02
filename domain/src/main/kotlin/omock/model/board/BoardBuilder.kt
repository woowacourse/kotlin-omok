package omock.model.board

import omock.model.Position
import omock.model.rule.OmokGameRule
import omock.model.rule.RenjuRule

@DslMarker
annotation class OmokBoardDsl

fun buildOmokBoard(block: OmokBoardBuilder.() -> Unit): OmokBoard {
    return OmokBoardBuilder().apply(block).build()
}

@OmokBoardDsl
class OmokBoardBuilder {
    private var boardSize: BoardSize = DEFAULT_BOARD_SIZE
    private var blocks: Blocks = Blocks()
    private var rule: OmokGameRule = DEFAULT_RULE

    fun size(width: Int) {
        boardSize = BoardSize(width)
    }

    fun size(size: BoardSize) {
        boardSize = size
    }

    fun blocks(blocks: Blocks) {
        this.blocks = blocks
    }

    fun blocks(blocks: List<Block>) {
        this.blocks = Blocks(blocks)
    }

    fun rule(rule: OmokGameRule) {
        this.rule = rule
    }

    fun build(): OmokBoard {
        validateBlocksInBoardSize()
        val emptyBlocks = boardSize.toBlocks()
        val board = if (blocks.isEmpty()) emptyBlocks else createBlocksFrom(emptyBlocks.toList())
        return OmokBoard(board, blocks, boardSize, rule)
    }

    private fun validateBlocksInBoardSize() {
        val blockPositions = blocks.blocks.map { it.position }
        val inValidBlockPositions = blockPositions.filterNot(boardSize::isInBounds)
        require(inValidBlockPositions.isEmpty()) {
            "블록의 위치가 보드 크기를 벗어났습니다. 보드 크기 : $boardSize, 위치 : $inValidBlockPositions"
        }
    }

    private fun BoardSize.toBlocks(): Blocks {
        return range().flatMap { y ->
            range().map { x ->
                Block(Position(x, y), BlockState.EMPTY)
            }
        }.let(::Blocks)
    }

    private fun createBlocksFrom(emptyBlocks: List<Block>): Blocks {
        val blockMap: Map<Position, Block> = blocks.toBlockMap()
        return emptyBlocks
            .map { block ->
                blockMap[block.position] ?: block
            }.let(::Blocks)
    }

    companion object {
        private val DEFAULT_BOARD_SIZE = BoardSize(15)
        private val DEFAULT_RULE = RenjuRule
    }
}
