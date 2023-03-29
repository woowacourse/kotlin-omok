package console

import dto.ColorDTO
import dto.StoneDTO
import dto.VectorDTO
import view.RenderBoard

class ConsoleRenderBoard : RenderBoard {
    private val board = StringBuilder()
    override fun render(stones: Map<Int, StoneDTO>, size: VectorDTO): String {
        board.clear()
        for (index in 0 until (size.x * size.y)) {
            makeNewLine(index, size)

            val x = index % size.x
            val y = index / size.x

            renderRowNumber(index, size, y)

            val stone = stones[index]
            if (stone != null) {
                renderStone(stone, size)
                continue
            }

            renderEmptySlot(x, y, size)
        }
        renderColumnChar(size)
        return board.toString()
    }

    private fun renderColumnChar(size: VectorDTO) {
        board.append(NEW_LINE)
        board.append(COLUMN_PADDING)
        board.append(COLUMN_PADDING)
        for (i in 0 until size.y) {
            val column = ('A'.code + i).toChar()
            board.append(COLUMN_FORMAT.format(column, COLUMN_PADDING))
        }
        board.append(NEW_LINE)
    }

    private fun renderRowNumber(index: Int, size: VectorDTO, y: Int) {
        if (index % (size.x) == LEFT_START_INDEX) {
            val row = size.x - y
            board.append(ROW_FORMAT.format(row))
        }
    }

    private fun makeNewLine(index: Int, size: VectorDTO) {
        if (index > 0 && index % size.x == LEFT_START_INDEX) board.append("\n")
    }

    private fun renderStone(stone: StoneDTO, size: VectorDTO) {
        if (stone.coordinate.x == LEFT_START_INDEX && stone.color == ColorDTO.BLACK)
            board.append(BLACK_LEFT_STONE)
        else if (stone.coordinate.x == size.x - COORDINATE_FIXER && stone.color == ColorDTO.BLACK)
            board.append(BLACK_RIGHT_STONE)
        else if (stone.color == ColorDTO.BLACK)
            board.append(BLACK_INNER_STONE)

        if (stone.coordinate.x == LEFT_START_INDEX && stone.color == ColorDTO.WHITE)
            board.append(WHITE_LEFT_STONE)
        else if (stone.coordinate.x == size.x - 1 && stone.color == ColorDTO.WHITE)
            board.append(WHITE_RIGHT_STONE)
        else if (stone.color == ColorDTO.WHITE)
            board.append(WHITE_INNER_STONE)
    }

    private fun renderEmptySlot(x: Int, y: Int, size: VectorDTO) {
        board.append(
            when {
                x == LEFT_START_INDEX && y == TOP_START_INDEX -> EMPTY_LEFT_TOP_SLOT
                x == LEFT_START_INDEX && y == size.y - COORDINATE_FIXER -> EMPTY_LEFT_BOTTOM_SLOT
                x == size.x - COORDINATE_FIXER && y == LEFT_START_INDEX -> EMPTY_RIGHT_TOP_SLOT
                x == size.x - COORDINATE_FIXER && y == size.y - COORDINATE_FIXER -> EMPTY_RIGHT_BOTTOM_SLOT
                y == size.y - COORDINATE_FIXER -> EMPTY_BOTTOM_SLOT
                x == LEFT_START_INDEX -> EMPTY_LEFT_SLOT
                y == TOP_START_INDEX -> EMPTY_TOP_SLOT
                x == size.x - COORDINATE_FIXER -> EMPTY_RIGHT_SLOT
                else -> EMPTY_INNER_SLOT
            }
        )
    }

    companion object {
        private const val EMPTY_TOP_SLOT = "─┬─"
        private const val EMPTY_BOTTOM_SLOT = "─┴─"
        private const val EMPTY_LEFT_TOP_SLOT = "┌─"
        private const val EMPTY_RIGHT_TOP_SLOT = "─┐"
        private const val EMPTY_LEFT_BOTTOM_SLOT = "└─"
        private const val EMPTY_RIGHT_BOTTOM_SLOT = "─┘"

        private const val EMPTY_INNER_SLOT = "─┼─"
        private const val EMPTY_LEFT_SLOT = "├─"
        private const val EMPTY_RIGHT_SLOT = "─┤"

        private const val BLACK_INNER_STONE = "─●─"
        private const val BLACK_LEFT_STONE = "●─"
        private const val BLACK_RIGHT_STONE = "─●"

        private const val WHITE_INNER_STONE = "─○─"
        private const val WHITE_LEFT_STONE = "○─"
        private const val WHITE_RIGHT_STONE = "─○"

        private const val COORDINATE_FIXER = 1
        private const val LEFT_START_INDEX = 0
        private const val TOP_START_INDEX = 0

        private const val NEW_LINE = "\n"
        private const val COLUMN_PADDING = "  "
        private const val COLUMN_FORMAT = "%c%s"
        private const val ROW_FORMAT = "%d\t"
    }
}
