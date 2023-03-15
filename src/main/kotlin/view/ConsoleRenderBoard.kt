package view

import dto.ColorDTO
import dto.PointDTO
import dto.StoneDTO

class ConsoleRenderBoard : RenderBoard {
    override fun render(stones: List<StoneDTO>, size: PointDTO) {

        for (i in 0 until (size.x * size.y)) {
            if (i > 0 && i % size.x == 0)
                println()

            val x = i % size.x
            val y = i / size.x
            if (i % (size.x) == 0)
                print("%d\t".format(size.x - y))

            val stone = stones.find {
                it.coordinate.x == x && it.coordinate.y == size.y - y - 1
            }
            if (stone != null) {
                if (stone.coordinate.x == 0 && stone.color == ColorDTO.BLACK)
                    print(BLACK_LEFT_STONE)
                else if (stone.coordinate.x == size.x - 1 && stone.color == ColorDTO.BLACK)
                    print(BLACK_RIGHT_STONE)
                else if (stone.color == ColorDTO.BLACK)
                    print(BLACK_INNER_STONE)

                if (stone.coordinate.x == 0 && stone.color == ColorDTO.WHITE)
                    print(WHITE_LEFT_STONE)
                else if (stone.coordinate.x == size.x - 1 && stone.color == ColorDTO.WHITE)
                    print(WHITE_RIGHT_STONE)
                else if (stone.color == ColorDTO.WHITE)
                    print(WHITE_INNER_STONE)
                continue
            }

            if (x == 0 && y == 0) print(EMPTY_LEFT_TOP_SLOT)
            else if (x == 14 && y == 0) print(EMPTY_RIGHT_TOP_SLOT)
            else if (y == 0) print(EMPTY_TOP_SLOT)
            else if (x == 0 && y == 14) print(EMPTY_LEFT_BOTTOM_SLOT)
            else if (x == 14 && y == 14) print(EMPTY_RIGHT_BOTTOM_SLOT)
            else if (y == 14) print(EMPTY_BOTTOM_SLOT)
            else if (x == 0) print(EMPTY_LEFT_SLOT)
            else if (x == 14) print(EMPTY_RIGHT_SLOT)
            else print(EMPTY_INNER_SLOT)
        }
        println()
        print("    ")
        for (i in 0 until size.y) {
            print("%c  ".format(('A'.code + i).toChar()))
        }

        println()
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
    }
}
