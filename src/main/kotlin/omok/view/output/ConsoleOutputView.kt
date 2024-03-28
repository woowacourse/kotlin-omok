package omok.view.output

import omok.model.Board
import omok.model.entity.Stone
import omok.model.entity.StoneColor

class ConsoleOutputView : OutputView {
    private val emptyOmokBoardString = buildEmptyOmokBoard()

    override fun printStartGuide() {
        println("오목 게임을 시작합니다")
    }

    override fun printError(message: String) {
        println(message)
    }

    override fun printTurn(
        board: Board,
        color: StoneColor,
    ) {
        val strMap = buildOmokBoard(board)
        println(strMap)
        printTurnGuide(color)
        printPreviousPoint(board.previousStone())
    }

    private fun printTurnGuide(color: StoneColor) {
        val colorString = getColorString(color)
        print("${colorString}의 차례입니다.  ")
    }

    private fun getColorString(color: StoneColor): String = if (color == StoneColor.BLACK) "흑" else "백"

    private fun printPreviousPoint(nullableStone: Stone?) {
        val stone = nullableStone ?: return println("")
        val (x, y) = stone.point
        val xAlphabet = intToAlphabet(x - 1)
        println("(마지막 돌의 위치: ${xAlphabet}$y)")
    }

    private fun intToAlphabet(num: Int): Char = (num + 'A'.code).toChar()

    private fun buildOmokBoard(board: Board): String {
        println()
        val sb = StringBuilder(emptyOmokBoardString)
        board.stones.forEach {
            val stoneChar = if (it.stoneColor == StoneColor.WHITE) '○' else '●'
            val x = (it.point.x) * 3
            val y = Board.MAX_SIZE - it.point.y
            val oneLineLength = 2 * 2 + (Board.MAX_SIZE - 2) * 3 + ROW_LABEL_PADDING_LENGTH + 1 // 4 + 39 + 3 + 1
            val idx = oneLineLength * y + x
            sb.setCharAt(idx, stoneChar)
        }
        return sb.toString()
    }

    private fun buildEmptyOmokBoard(): String {
        val stringBuilder = StringBuilder()

        stringBuilder.appendLine(buildEmptyTopLine(Board.MAX_SIZE.toString(), ROW_LABEL_PADDING_LENGTH))
        (Board.MAX_SIZE - 1 downTo Board.MIN_SIZE + 1).forEach {
            stringBuilder.appendLine(buildEmptyMidLine(it.toString(), ROW_LABEL_PADDING_LENGTH))
        }
        stringBuilder.appendLine(buildEmptyBottomLine(Board.MIN_SIZE.toString(), ROW_LABEL_PADDING_LENGTH))

        val columnLabel =
            " ".repeat(ROW_LABEL_PADDING_LENGTH) +
                Board.SIZE_RANGE.map { (it - 1 + 'A'.code).toChar() }
                    .joinToString("  ")
        stringBuilder.appendLine(columnLabel)
        return stringBuilder.toString()
    }

    private fun buildEmptyTopLine(
        label: String,
        labelPadLength: Int,
    ): String =
        buildEmptyLine(
            label,
            labelPadLength,
            TOP_LEFT_BOARD_STRING,
            TOP_MID_BOARD_STRING,
            TOP_RIGHT_BOARD_STRING,
        )

    private fun buildEmptyMidLine(
        label: String,
        labelPadLength: Int,
    ): String =
        buildEmptyLine(
            label,
            labelPadLength,
            MID_LEFT_BOARD_STRING,
            MID_MID_BOARD_STRING,
            MID_RIGHT_BOARD_STRING,
        )

    private fun buildEmptyBottomLine(
        label: String,
        labelPadLength: Int,
    ): String =
        buildEmptyLine(
            label,
            labelPadLength,
            BOTTOM_LEFT_BOARD_STRING,
            BOTTOM_MID_BOARD_STRING,
            BOTTOM_RIGHT_BOARD_STRING,
        )

    private fun buildEmptyLine(
        label: String,
        labelPadLength: Int,
        startString: String,
        midString: String,
        endString: String,
    ): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(label.padStart(labelPadLength))
        stringBuilder.append(startString)
        repeat(Board.SIZE_RANGE.count() - 2) { stringBuilder.append(midString) }
        stringBuilder.append(endString)
        return stringBuilder.toString()
    }

    override fun printWinner(
        board: Board,
        color: StoneColor,
    ) {
        val strMap = buildOmokBoard(board)
        println(strMap)
        val colorString = getColorString(color)
        print("${colorString}이 승리했습니다")
    }

    companion object {
        const val ROW_LABEL_PADDING_LENGTH = 3

        const val TOP_LEFT_BOARD_STRING = "┌─"
        const val TOP_MID_BOARD_STRING = "─┬─"
        const val TOP_RIGHT_BOARD_STRING = "─┐"

        const val MID_LEFT_BOARD_STRING = "├─"
        const val MID_MID_BOARD_STRING = "─┼─"
        const val MID_RIGHT_BOARD_STRING = "─┤"

        const val BOTTOM_LEFT_BOARD_STRING = "└─"
        const val BOTTOM_MID_BOARD_STRING = "─┴─"
        const val BOTTOM_RIGHT_BOARD_STRING = "─┘"
    }
}
