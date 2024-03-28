package woowacourse.omok.console.view

import woowacourse.omok.model.BoardUiModel
import woowacourse.omok.model.StoneUiModel

interface OmokOutputView {
    fun showStartMessage()

    fun showCurrentGameState(
        board: BoardUiModel,
        stone: StoneUiModel?,
    )

    fun showGameResult(
        board: BoardUiModel,
        stone: StoneUiModel,
    )
}
