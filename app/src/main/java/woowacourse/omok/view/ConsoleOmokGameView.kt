package woowacourse.omok.view

import woowacourse.omok.model.BlockStateUiModel
import woowacourse.omok.model.BlockUiModel
import woowacourse.omok.model.BoardUiModel
import woowacourse.omok.model.PositionUiModel

class ConsoleOmokGameView {
    fun showGameStart(initialBoard: BoardUiModel) {
        println("오목 게임을 시작합니다.")
        println()
        println(initialBoard)
        println("${BlockStateUiModel.BLACK.format()}의 차례입니다.")
    }

    fun showCurrentGameState(
        board: BoardUiModel,
        stone: BlockUiModel,
    ) {
        println()
        println(board)
        val (x, y, color) = stone
        val nextColor = color.nextColor()
        println("${nextColor.format()}의 차례입니다. (마지막 돌의 위치: ${PositionUiModel(x, y).format()})")
    }

    private fun BlockStateUiModel.nextColor(): BlockStateUiModel {
        return when (this) {
            BlockStateUiModel.BLACK -> BlockStateUiModel.WHITE
            BlockStateUiModel.WHITE -> BlockStateUiModel.BLACK
            BlockStateUiModel.EMPTY -> throw IllegalArgumentException("EMPTY 는 다음 차례가 없습니다.")
        }
    }

    fun showGameResult(
        board: BoardUiModel,
        stone: BlockUiModel,
    ) {
        println(board)
        showWinner(stone.blockState)
    }

    fun showPlaceError(errorMessage: String) {
        println(errorMessage)
    }

    private fun showWinner(color: BlockStateUiModel) {
        println("축하합니다! ${color.format()}가 이겼습니다.")
    }
}
