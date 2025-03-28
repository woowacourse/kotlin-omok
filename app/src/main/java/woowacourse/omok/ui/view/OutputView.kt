package woowacourse.omok.ui.view

import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.omokboard.PointState
import woowacourse.omok.domain.model.omokboard.Position
import woowacourse.omok.domain.model.player.StoneColor
import woowacourse.omok.domain.model.rule.judge.JudgeResult.Finished
import woowacourse.omok.domain.model.rule.place.PlaceResult.Failure

class OutputView {
    fun displayOmokGameStart() {
        println(START_OMOK_GAME_TITLE)
    }

    fun displayOmokBoard(omokBoard: OmokBoard) {
        println()
        (1..omokBoard.height)
            .reversed()
            .forEach { rowNumber ->
                displayRow(omokBoard, rowNumber)
            }
        displayColumnLabels(omokBoard.width)
    }

    private fun displayRow(
        omokBoard: OmokBoard,
        rowPosition: Int,
    ) {
        val rowPoints = omokBoard.snapshot.filter { it.key.row == rowPosition }
        print(" ${String.format("%02d", rowPosition)} ")
        println(rowPoints.keys.joinToString("──") { it.drawBoard(omokBoard) })
    }

    private fun Position.drawBoard(omokBoard: OmokBoard): String {
        val stoneColor = omokBoard.find(this) ?: PointState.EMPTY

        return when {
            stoneColor == PointState.OCCUPIED_BLACK -> "●"
            stoneColor == PointState.OCCUPIED_WHITE -> "○"
            this.row == omokBoard.height && this.column == 1 -> "┌"
            this.row == omokBoard.height && this.column == omokBoard.width -> "┐"
            this.row == 1 && this.column == 1 -> "└"
            this.row == 1 && this.column == omokBoard.width -> "┘"
            this.column == 1 -> "├"
            this.column == omokBoard.width -> "┤"
            this.row == 1 -> "┴"
            this.row == omokBoard.height -> "┬"
            else -> "┼"
        }
    }

    private fun displayColumnLabels(boardWidth: Int) {
        println(
            (1..boardWidth)
                .map { columnNumber ->
                    columnNumber
                        .toColumnLabel()
                }.joinToString(separator = "  ", prefix = "    "),
        )
    }

    private fun Int.toColumnLabel(): Char {
        val alphabets = ALPHABETS.toList()
        return alphabets[this - 1]
    }

    fun displayErrorMessage(error: Failure) {
        println()
        println(
            when (error) {
                Failure.AlreadyExistStone -> ALREADY_EXIST_MESSAGE
                Failure.InvalidPosition -> INVALID_POSITION_MESSAGE
                Failure.DoubleThreeViolation -> FORBIDDEN_DOUBLE_THREE
                Failure.DoubleFourViolation -> FORBIDDEN_DOUBLE_FOUR
                Failure.OverlineViolation -> FORBIDDEN_OVERLINE
            },
        )
    }

    fun displayGameResultMessage(result: Finished) {
        println()
        println(
            when (result) {
                is Finished.Win ->
                    when (result.stone) {
                        StoneColor.BLACK -> BLACK_WIN_RESULT_MESSAGE
                        StoneColor.WHITE -> WHITE_WIN_RESULT_MESSAGE
                    }

                is Finished.Draw -> DRAW_RESULT_MESSAGE
            },
        )
    }

    companion object {
        private const val START_OMOK_GAME_TITLE: String = "오목 게임을 시작합니다."
        private const val ALREADY_EXIST_MESSAGE: String = "이미 돌이 있는 자리에 둘 수 없습니다."
        private const val INVALID_POSITION_MESSAGE: String = "잘못된 위치 입니다."
        private const val FORBIDDEN_DOUBLE_THREE: String = "3 x 3은 금지입니다."
        private const val FORBIDDEN_DOUBLE_FOUR: String = "4 x 4는 금지입니다."
        private const val FORBIDDEN_OVERLINE: String = "6목은 금지입니다."
        private const val DRAW_RESULT_MESSAGE: String = "무승부 입니다."
        private const val BLACK_WIN_RESULT_MESSAGE: String = "흑의 우승을 축하드립니다!"
        private const val WHITE_WIN_RESULT_MESSAGE: String = "백의 우승을 축하드립니다!"
        private val ALPHABETS: CharRange = ('A'..'Z')
    }
}
