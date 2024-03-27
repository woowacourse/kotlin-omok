package omok.view

import omok.view.model.BoardUiModel
import omok.view.model.StoneUiModel

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
