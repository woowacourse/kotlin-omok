package woowacourse.omok.view

import omock.model.board.OmokBoard
import woowacourse.omok.model.android.BlockAndroidModel

interface OmokGameAndroidView {
    fun showGameStart(blocks: List<BlockAndroidModel>)

    fun showCurrentGameState(block: BlockAndroidModel)

    fun showGameResult(
        board: OmokBoard,
        block: BlockAndroidModel,
    )

    fun showPlaceError(errorMessage: String)

    fun resetView()
}
