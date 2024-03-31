package omock.model.rule

import omock.model.Position
import omock.model.board.BlockState
import omock.model.board.OmokBoard

fun OmokBoard.toArkOmokBoard(): List<List<Int>> {
    val arkBoard = MutableList(size.width) { MutableList(size.width) { 0 } }
    blockRecords.blocks
        .forEach { (position, blockState) ->
            val (x, y) = position
            arkBoard[y - 1][x - 1] = blockState.toInt()
        }
    return arkBoard
}

fun Position.toArkOmokPoint(): Pair<Int, Int> {
    return Pair(x - 1, y - 1)
}

private fun BlockState.toInt(): Int {
    return when (this) {
        BlockState.BLACK_STONE -> 1
        BlockState.WHITE_STONE -> 2
        else -> 0
    }
}
