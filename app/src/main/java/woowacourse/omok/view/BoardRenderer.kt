package woowacourse.omok.view

import woowacourse.omok.domain.Board
import woowacourse.omok.domain.Position
import woowacourse.omok.domain.StoneType

object BoardRenderer {
    fun render(board: Board): String {
        val size = 15
        return buildString {
            for (y in size downTo 1) {
                append("${y.toString().padStart(2)} ") // 행 번호
                for (x in 1..size) {
                    val stone = board.stones.firstOrNull { it.position == Position(x, y) }
                    append(
                        when (stone?.color) {
                            StoneType.BLACK -> "●"
                            StoneType.WHITE -> "○"
                            else -> getCellSymbol(x, y, size)
                        },
                    )
                    append(if (x == size) "\n" else "──")
                }
            }
            // 하단 알파벳 추가
            append("   ")
            append(('A' until 'A' + size).joinToString("  "))
        }
    }

    private fun getCellSymbol(
        x: Int,
        y: Int,
        size: Int,
    ): String {
        return when {
            x == 1 && y == size -> "┌"
            x == size && y == size -> "┐"
            x == 1 && y == 1 -> "└"
            x == size && y == 1 -> "┘"
            x == 1 -> "├"
            x == size -> "┤"
            y == size -> "┬"
            y == 1 -> "┴"
            else -> "┼"
        }
    }
}
