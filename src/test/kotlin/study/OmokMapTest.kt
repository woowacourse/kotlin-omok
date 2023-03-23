package study

import domain.stone.Board
import domain.stone.Stone
import domain.stone.StoneType

class OmokMapTest

class OmokMap {

    val MAX_INDEX = 15
    val MIN_INDEX = 1

    fun makeMap(board: Board): List<String> {

        // 왼쪽 숫자 표현
        val omokMap: MutableList<StringBuilder> = MutableList(Board.BOARD_SIZE + 1) { StringBuilder() }
        omokMap.indices.forEach {
            if (it != Board.BOARD_SIZE)
                omokMap[it].append(" %2d ".format(Board.BOARD_SIZE - it))
        }

        // 각 열들의 포멧 메시지 표현  %c──%c──%c── ...
        val intersectionTextFormat = getIntersectionTextFormat()
        omokMap.indices.forEach {
            omokMap[it].append(intersectionTextFormat)
        }

        // 각 열들의

        return omokMap.map { it.toString() }
    }

    private fun getIntersectionTextFormat(): String {
        val text: StringBuilder = StringBuilder()

        repeat(Board.BOARD_SIZE) {
            if (it == 0) text.append("%c")
            else {
                text.append("──%c")
            }
        }

        return text.toString()
    }

    private fun getIntersectionText(stone: Stone): Char {
        val type: StoneType = stone.type
        val x: Int = stone.position.x
        val y: Int = stone.position.y

        return when {
            type == StoneType.WHITE -> '○'
            type == StoneType.BLACK -> '●'
            x == MAX_INDEX && y == MIN_INDEX -> '┌'
            x == MAX_INDEX && y == MAX_INDEX -> '┐'
            x == MIN_INDEX && y == MIN_INDEX -> '└'
            x == MIN_INDEX && y == MAX_INDEX -> '┘'
            x == MAX_INDEX -> '┬'
            x == MIN_INDEX -> '┴'
            y == MIN_INDEX -> '├'
            y == MAX_INDEX -> '┤'
            else -> '┼'
        }
    }
}
