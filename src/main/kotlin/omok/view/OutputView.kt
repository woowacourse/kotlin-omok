package omok.view

import omok.domain.board.OmokBoard
import omok.domain.board.OmokColumn.Companion.entriesWithoutWall
import omok.domain.board.StoneStatus
import omok.view.ext.toLabel

class OutputView {
    fun printErrorMessage(msg: String?) {
        println(msg)
    }

    fun printStartMessage() {
        println(MESSAGE_START_GAME)
    }

    fun printPrintWinner(stone: StoneStatus) {
        println(MESSAGE_WINNER.format(stone.toLabel()))
    }

    fun printBoard(board: OmokBoard) {
        val matrix = board.toMatrix()

        matrix.forEachIndexed { row, rowValue ->
            rowValue.forEachIndexed { column, _ ->
                val stone = matrix[ROW_MAX_LENGTH - row - 1][column].toChar()

                when (row) {
                    0 -> printTopRow(column, stone)
                    matrix.size - 1 -> printBottomRow(column, rowValue.size, stone)
                    else -> printMiddleRow(column, rowValue.size, row, stone)
                }
            }
            println()
        }
        printFormattedColumn()
        println()
    }

    private fun printTopRow(
        column: Int,
        stone: Char?,
    ) {
        printRow(column, stone)
    }

    private fun printBottomRow(
        column: Int,
        rowSize: Int,
        stone: Char?,
    ) {
        when (column) {
            0 -> {
                1.printFormattedRow()
                printStoneOrDefault(stone, BOTTOM_LEFT_CORNER)
                print(VERTICAL_SEPARATOR)
            }

            rowSize - 1 -> {
                print(VERTICAL_SEPARATOR)
                printStoneOrDefault(stone, BOTTOM_RIGHT_CORNER)
            }

            else -> {
                print(VERTICAL_SEPARATOR)
                printStoneOrDefault(stone, BOTTOM_HORIZONTAL_SEPARATOR)
                print(VERTICAL_SEPARATOR)
            }
        }
    }

    private fun printMiddleRow(
        column: Int,
        rowSize: Int,
        row: Int,
        stone: Char?,
    ) {
        when (column) {
            0 -> {
                (ROW_MAX_LENGTH - row).printFormattedRow()
                printStoneOrDefault(stone, LEFT_VERTICAL_SEPARATOR)
                print(VERTICAL_SEPARATOR)
            }

            rowSize - 1 -> {
                print(VERTICAL_SEPARATOR)
                printStoneOrDefault(stone, RIGHT_VERTICAL_SEPARATOR)
            }

            else -> {
                print(VERTICAL_SEPARATOR)
                printStoneOrDefault(stone, HORIZONTAL_SEPARATOR)
                print(VERTICAL_SEPARATOR)
            }
        }
    }

    private fun printRow(
        column: Int,
        stone: Char?,
    ) {
        when (column) {
            0 -> {
                ROW_MAX_LENGTH.printFormattedRow()
                printStoneOrDefault(stone, TOP_LEFT_CORNER)
                print(VERTICAL_SEPARATOR)
            }

            ROW_MAX_LENGTH - 1 -> {
                print(VERTICAL_SEPARATOR)
                printStoneOrDefault(stone, TOP_RIGHT_CORNER)
            }

            else -> {
                print(VERTICAL_SEPARATOR)
                printStoneOrDefault(stone, TOP_HORIZONTAL_SEPARATOR)
                print(VERTICAL_SEPARATOR)
            }
        }
    }

    private fun Int.printFormattedRow() {
        if (this >= 10) print("$this ") else print("$this  ")
    }

    private fun printFormattedColumn() {
        print(SPACE.repeat(2))
        entriesWithoutWall().forEach {
            print(SPACE)
            print(it.name)
            print(SPACE)
        }
    }

    private fun printStoneOrDefault(
        stone: Char?,
        default: Char,
    ) {
        print(stone ?: default)
    }

    private fun StoneStatus.toChar(): Char? {
        return when (this) {
            StoneStatus.BLACK -> '●'
            StoneStatus.WHITE -> '○'
            StoneStatus.PROTECTED -> 'x'
            else -> null
        }
    }

    companion object {
        private const val MESSAGE_START_GAME = "오목 게임을 시작합니다."
        private const val MESSAGE_WINNER = "%s이 승리하였습니다."
        private const val VERTICAL_SEPARATOR = '-'
        private const val HORIZONTAL_SEPARATOR = '┼'
        private const val TOP_LEFT_CORNER = '┌'
        private const val TOP_RIGHT_CORNER = '┐'
        private const val BOTTOM_LEFT_CORNER = '└'
        private const val BOTTOM_RIGHT_CORNER = '┘'
        private const val TOP_HORIZONTAL_SEPARATOR = '┬'
        private const val BOTTOM_HORIZONTAL_SEPARATOR = '┴'
        private const val LEFT_VERTICAL_SEPARATOR = '├'
        private const val RIGHT_VERTICAL_SEPARATOR = '┤'
        private const val SPACE = " "
        private const val ROW_MAX_LENGTH = 15
    }
}
