package omock.model.board

import omock.model.Position

@JvmInline
value class Blocks(val blocks: Set<Block> = emptySet()) {
    constructor(blocks: List<Block>) : this(blocks.toSet()) {
        require(blocks.distinct().size == blocks.size) { "중복된 Block 이 존재합니다." }
    }

    val size: Int get() = blocks.size

    operator fun plus(other: Block): Blocks = Blocks(blocks + other)

    fun isEmpty(): Boolean = blocks.isEmpty()

    fun toList(): List<Block> = blocks.toList()

    fun toBlockStateMap(): Map<Position, BlockState> {
        return blocks.associate { it.position to it.state }
    }

    fun toBlockMap(): Map<Position, Block> {
        return blocks.associateBy { it.position }
    }
}
