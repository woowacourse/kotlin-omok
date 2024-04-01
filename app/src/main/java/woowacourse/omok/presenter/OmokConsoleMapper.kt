package woowacourse.omok.presenter

import omock.model.board.Block
import omock.model.board.BlockState
import omock.model.board.OmokBoard
import woowacourse.omok.model.console.BlockConsoleModel
import woowacourse.omok.model.console.BlockStateConsoleModel
import woowacourse.omok.model.console.BoardConsoleModel

fun BlockState.toConsole(): BlockStateConsoleModel {
    return when (this) {
        BlockState.BLACK_STONE -> BlockStateConsoleModel.BLACK
        BlockState.WHITE_STONE -> BlockStateConsoleModel.WHITE
        BlockState.EMPTY -> BlockStateConsoleModel.EMPTY
    }
}

fun Block.toConsole(): BlockConsoleModel {
    val (position, blockState) = this
    return BlockConsoleModel(position.x - 1, position.y - 1, blockState.toConsole())
}

fun OmokBoard.toConsole(): BoardConsoleModel {
    return toDoubleList()
        .map { it.toConsole() }
        .let(::BoardConsoleModel)
}

private fun List<Block>.toConsole(): Set<BlockConsoleModel> {
    return mapNotNull {
        it.toConsole()
    }.toSet()
}
