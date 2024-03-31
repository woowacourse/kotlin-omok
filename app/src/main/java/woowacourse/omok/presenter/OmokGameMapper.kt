package woowacourse.omok.presenter

import omock.model.Position
import omock.model.board.Block
import omock.model.board.BlockState
import omock.model.board.OmokBoard
import woowacourse.omok.model.BlockStateUiModel
import woowacourse.omok.model.BlockUiModel
import woowacourse.omok.model.BoardUiModel
import woowacourse.omok.model.PositionUiModel

fun PositionUiModel.toPosition(): Position {
    return Position(x, y)
}

fun BlockState.toUiModel(): BlockStateUiModel {
    return when (this) {
        BlockState.BLACK_STONE -> BlockStateUiModel.BLACK
        BlockState.WHITE_STONE -> BlockStateUiModel.WHITE
        BlockState.EMPTY -> BlockStateUiModel.EMPTY
    }
}

fun Block.toUiModel(): BlockUiModel {
    val (position, blockState) = this
    return BlockUiModel(position.x - 1, position.y - 1, blockState.toUiModel())
}

fun OmokBoard.toUiModel(): BoardUiModel {
    return toDoubleList()
        .map { it.toUiModel() }
        .let(::BoardUiModel)
}

private fun List<Block>.toUiModel(): Set<BlockUiModel> {
    return mapNotNull {
        it.toUiModel()
    }.toSet()
}
