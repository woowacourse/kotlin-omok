package omok.view

import omok.view.model.BoardUiModel
import omok.view.model.PositionUiModel
import omok.view.model.StoneColorUiModel
import omok.view.model.StoneUiModel

class ConsoleOmokOutputView : OmokOutputView {
    override fun showStartMessage() {
        println("오목 게임을 시작합니다.")
    }

    override fun showCurrentGameState(
        board: BoardUiModel,
        stone: StoneUiModel?,
    ) {
        println()
        println(board)
        if (stone == null) {
            println("${StoneColorUiModel.BLACK.format()}의 차례입니다.")
        } else {
            val (x, y, color) = stone
            val nextColor = color.nextColor()
            println("${nextColor.format()}의 차례입니다. (마지막 돌의 위치: ${PositionUiModel(x, y).format()})")
        }
    }

    private fun StoneColorUiModel.nextColor(): StoneColorUiModel {
        return when (this) {
            StoneColorUiModel.BLACK -> StoneColorUiModel.WHITE
            StoneColorUiModel.WHITE -> StoneColorUiModel.BLACK
        }
    }

    override fun showGameResult(
        board: BoardUiModel,
        stone: StoneUiModel,
    ) {
        println(board)
        showWinner(stone.stoneColor)
    }

    private fun showWinner(color: StoneColorUiModel) {
        println("축하합니다! ${color.format()}가 이겼습니다.")
    }
}
