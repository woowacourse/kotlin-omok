package view

import dto.ColorDTO
import dto.PointDTO
import dto.StoneDTO

class ConsoleRenderBoard : RenderBoard {
    override fun render(stones: List<StoneDTO>, size: PointDTO) {
        for (index in 0 until (size.x * size.y)) {
            makeNewLine(index, size)

            val x = index % size.x
            val y = index / size.x

            renderRowNumber(index, size, y)

            val stone = findStone(stones, x, y, size)
            if (stone != null) {
                renderStone(stone, size)
                continue
            }

            renderEmptySlot(x, y, size)
        }
        renderColumnChar(size)
    }

    private fun findStone(
        stones: List<StoneDTO>, x: Int, y: Int, size: PointDTO
    ): StoneDTO? {
        val stone = stones.find {
            it.coordinate.x == x && it.coordinate.y == size.y - y - 1
        }
        return stone
    }

    private fun renderColumnChar(size: PointDTO) {
        println()
        print("    ")
        for (i in 0 until size.y) {
            print("%c  ".format(('A'.code + i).toChar()))
        }
        println()
    }

    private fun renderRowNumber(index: Int, size: PointDTO, y: Int) {
        if (index % (size.x) == LEFT_START_INDEX) {
            print("%d\t".format(size.x - y))
        }
    }

    private fun makeNewLine(index: Int, size: PointDTO) {
        if (index > 0 && index % size.x == LEFT_START_INDEX) println()
    }

    private fun renderStone(stone: StoneDTO, size: PointDTO) {
        if (stone.coordinate.x == LEFT_START_INDEX && stone.color == ColorDTO.BLACK) print(BLACK_LEFT_STONE)
        else if (stone.coordinate.x == size.x - COORDINATE_FIXER && stone.color == ColorDTO.BLACK) print(
            BLACK_RIGHT_STONE
        )
        else if (stone.color == ColorDTO.BLACK) print(BLACK_INNER_STONE)

        if (stone.coordinate.x == LEFT_START_INDEX && stone.color == ColorDTO.WHITE) print(WHITE_LEFT_STONE)
        else if (stone.coordinate.x == size.x - 1 && stone.color == ColorDTO.WHITE) print(WHITE_RIGHT_STONE)
        else if (stone.color == ColorDTO.WHITE) print(WHITE_INNER_STONE)
    }

    private fun renderEmptySlot(x: Int, y: Int, size: PointDTO) {
        if (x == LEFT_START_INDEX && y == TOP_START_INDEX) print(EMPTY_LEFT_TOP_SLOT)
        else if (x == size.x - COORDINATE_FIXER && y == LEFT_START_INDEX) print(EMPTY_RIGHT_TOP_SLOT)
        else if (y == TOP_START_INDEX) print(EMPTY_TOP_SLOT)
        else if (x == LEFT_START_INDEX && y == size.y - COORDINATE_FIXER) print(EMPTY_LEFT_BOTTOM_SLOT)
        else if (x == size.x - COORDINATE_FIXER && y == size.y - COORDINATE_FIXER) print(EMPTY_RIGHT_BOTTOM_SLOT)
        else if (y == size.y - COORDINATE_FIXER) print(EMPTY_BOTTOM_SLOT)
        else if (x == LEFT_START_INDEX) print(EMPTY_LEFT_SLOT)
        else if (x == size.x - COORDINATE_FIXER) print(EMPTY_RIGHT_SLOT)
        else print(EMPTY_INNER_SLOT)
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
        private const val FIRST_LINE_INDEX = 0
    }
}
