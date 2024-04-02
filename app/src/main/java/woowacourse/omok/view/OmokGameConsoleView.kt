package woowacourse.omok.view

import woowacourse.omok.model.console.BlockConsoleModel
import woowacourse.omok.model.console.BlockStateConsoleModel
import woowacourse.omok.model.console.BoardConsoleModel
import woowacourse.omok.model.console.PositionConsoleModel

class OmokGameConsoleView {
    fun showGameStart(initialBoard: BoardConsoleModel) {
        println("오목 게임을 시작합니다.")
        println()
        println(initialBoard)
        println("${BlockStateConsoleModel.BLACK.format()}의 차례입니다.")
    }

    fun showCurrentGameState(
        board: BoardConsoleModel,
        stone: BlockConsoleModel,
    ) {
        println()
        println(board)
        val (x, y, color) = stone
        val nextColor = color.nextColor()
        println("${nextColor.format()}의 차례입니다. (마지막 돌의 위치: ${PositionConsoleModel(x, y).format()})")
    }

    private fun BlockStateConsoleModel.nextColor(): BlockStateConsoleModel {
        return when (this) {
            BlockStateConsoleModel.BLACK -> BlockStateConsoleModel.WHITE
            BlockStateConsoleModel.WHITE -> BlockStateConsoleModel.BLACK
            BlockStateConsoleModel.EMPTY -> throw IllegalArgumentException("EMPTY 는 다음 차례가 없습니다.")
        }
    }

    fun showGameResult(
        board: BoardConsoleModel,
        stone: BlockConsoleModel,
    ) {
        println(board)
        showWinner(stone.blockState)
    }

    fun showPlaceError(errorMessage: String) {
        println(errorMessage)
    }

    private fun showWinner(color: BlockStateConsoleModel) {
        println("축하합니다! ${color.format()}가 이겼습니다.")
    }
}
