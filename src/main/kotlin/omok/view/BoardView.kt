package omok.view

import omok.domain.board.OmokBoard
import omok.domain.place.Empty
import omok.domain.place.Place
import omok.view.ext.format
import java.lang.StringBuilder

class BoardView(private val omokBoard: OmokBoard) {
    private val boardInfo = StringBuilder()
    private val formattedColumn = StringBuilder()

    init {
        initialize()
        formattedColumn()
    }

    private fun setStone(place: Place) {
        val magicNumber =
            COLUMN_MAGIC_NUMBER + ((place.x - 1) * COLUMN_MAGIC_NUMBER) +
                (OmokBoard.MAX_COLUMN_SIZE * COLUMN_MAGIC_NUMBER + SPACE_MAGIC_NUMBER) * (OmokBoard.MAX_ROW_SIZE - place.y)
        boardInfo.setCharAt(magicNumber, place.format())
    }

    private fun initialize() {
        (0 until OmokBoard.MAX_ROW_SIZE).forEachIndexed { row, _ ->
            when (row) {
                0 -> {
                    addRow(OmokBoard.MAX_ROW_SIZE, TOP_LEFT_CORNER, TOP_HORIZONTAL_SEPARATOR, TOP_RIGHT_CORNER)
                }
                OmokBoard.MAX_ROW_SIZE - 1 -> {
                    addRow(1, BOTTOM_LEFT_CORNER, BOTTOM_HORIZONTAL_SEPARATOR, BOTTOM_RIGHT_CORNER)
                }
                else -> {
                    addRow(OmokBoard.MAX_ROW_SIZE - row, LEFT_VERTICAL_SEPARATOR, HORIZONTAL_SEPARATOR, RIGHT_VERTICAL_SEPARATOR)
                }
            }
        }
    }

    private fun addRow(
        rowSize: Int,
        left: String,
        middle: String,
        right: String,
    ) {
        boardInfo.append(rowSize.formattedRow())
        boardInfo.append(left)
        repeat(OmokBoard.MAX_COLUMN_SIZE - 2) { boardInfo.append(middle) }
        boardInfo.append(right)
        boardInfo.append("\n")
    }

    private fun formattedColumn() {
        formattedColumn.append(SPACE.repeat(2))
        OmokBoard.COLUMN_POOL.subList(0, OmokBoard.MAX_COLUMN_SIZE).forEach {
            formattedColumn.append(SPACE + it + SPACE)
        }
    }

    private fun Int.formattedRow(): String {
        return if (this >= 10) "$this " else "$this  "
    }

    override fun toString(): String {
        omokBoard.omokStones.places
            .filter { it !is Empty }
            .forEach { setStone(it) }
        return boardInfo.append(formattedColumn).append("\n").toString()
    }

    companion object {
        private const val SPACE_MAGIC_NUMBER = 2
        private const val COLUMN_MAGIC_NUMBER = 3
        private const val SPACE = " "
        private const val HORIZONTAL_SEPARATOR = "-┼-"
        private const val TOP_LEFT_CORNER = "┌-"
        private const val TOP_RIGHT_CORNER = "-┐"
        private const val BOTTOM_LEFT_CORNER = "└-"
        private const val BOTTOM_RIGHT_CORNER = "-┘"
        private const val TOP_HORIZONTAL_SEPARATOR = "-┬-"
        private const val BOTTOM_HORIZONTAL_SEPARATOR = "-┴-"
        private const val LEFT_VERTICAL_SEPARATOR = "├-"
        private const val RIGHT_VERTICAL_SEPARATOR = "-┤"
    }
}
