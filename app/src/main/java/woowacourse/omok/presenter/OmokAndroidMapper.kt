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
import woowacourse.omok.model.android.BlockAndroidModel
import woowacourse.omok.model.android.BlockStateAndroidModel

fun BlockState.toAndroid(): BlockStateAndroidModel {
    return when (this) {
        BlockState.BLACK_STONE -> BlockStateAndroidModel.BLACK_STONE
        BlockState.WHITE_STONE -> BlockStateAndroidModel.WHITE_STONE
        BlockState.EMPTY -> BlockStateAndroidModel.EMPTY
    }
}

fun Block.toAndroid(): BlockAndroidModel {
    val (position, blockState) = this
    return BlockAndroidModel(position.x - 1, position.y - 1, blockState.toAndroid())
}

fun OmokBoard.toAndroid(): List<BlockAndroidModel> {
    return blockRecords.blocks.map(Block::toAndroid)
}

fun Failure.toAndroidErrorMessage(): String =
    when (this) {
        InvalidGameOver -> "게임이 이미 종료되었습니다."
        InvalidOverLineRule -> "장목 규칙을 위반하였습니다."
        InvalidDuplicatedPlaced -> "이미 돌이 놓여진 자리입니다."
        InvalidThreeThreeRule -> "33 규칙을 위반하였습니다."
        InvalidFourFourRule -> "44 규칙을 위반하였습니다."
        InvalidOutOfBound -> "바둑판을 벗어난 자리입니다."
    }
