package view

class GameView(val renderBoard: RenderBoard = RenderBoard()) {
    fun startGame() {
        println(GAME_START)
    }

    fun renderBoard(stones: List<Pair<Int, Pair<Int, Int>>>, size: Pair<Int, Int>) {
        renderBoard.render(stones, size)
    }

    fun readStone(color: Int, lastStone: Pair<Int, Int>?): Pair<Char, Int>? {
        print(USER_TURN.format(colorToString(color)))
        println(
            lastStone?.let {
                LAST_STONE_POSITION.format(coordinateToString(lastStone))
            } ?: ""
        )
        val input = readln().trim()
        if (input.length != 2) {
            return null
        }
        if (input[0] < 'A' || input[0] > 'Z') {
            return null
        }
        if (!input[1].isDigit()) {
            return null
        }
        return input[0] to input[1].digitToInt()
    }

    private fun colorToString(color: Int): String? {
        return when (color) {
            0 -> "흑"
            1 -> "백"
            else -> null
        }
    }

    private fun coordinateToString(coordinate: Pair<Int, Int>): String {
        return ("%c%d".format(('A'.code + coordinate.first).toChar(), coordinate.second))
    }

    companion object {
        private const val GAME_START = "오목 게임을 시작합니다."
        private const val USER_TURN = "%s의 차례입니다."
        private const val LAST_STONE_POSITION = " (마지막 돌의 위치 : %s) "
    }
}
