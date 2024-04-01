package woowacourse.omok.presenter

import omock.model.Failure
import omock.model.InvalidDuplicatedPlaced
import omock.model.InvalidFourFourRule
import omock.model.InvalidGameOver
import omock.model.InvalidOutOfBound
import omock.model.InvalidOverLineRule
import omock.model.InvalidThreeThreeRule
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

fun Failure.toConsoleErrorMessage(): String =
    when (this) {
        InvalidGameOver -> "게임이 이미 종료되었습니다."
        InvalidOverLineRule -> "장목 규칙을 위반하였습니다."
        InvalidDuplicatedPlaced -> "이미 돌이 놓여진 자리입니다."
        InvalidThreeThreeRule -> "33 규칙을 위반하였습니다."
        InvalidFourFourRule -> "44 규칙을 위반하였습니다."
        InvalidOutOfBound -> "바둑판을 벗어난 자리입니다."
    }
