package woowacourse.omok.presenter

import omock.model.board.Block
import omock.model.board.BlockState
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