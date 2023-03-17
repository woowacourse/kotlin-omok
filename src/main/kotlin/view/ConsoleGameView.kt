package view

import dto.ColorDTO
import dto.StoneDTO
import dto.VectorDTO

class ConsoleGameView(override val renderBoard: RenderBoard = ConsoleRenderBoard()) : GameView {
    override fun startGame() {
        println(GAME_START)
    }

    override fun renderBoard(stones: List<StoneDTO>, size: VectorDTO) {
        println(renderBoard.render(stones, size))
    }

    override fun readStone(color: ColorDTO, lastStone: VectorDTO?): VectorDTO? {
        print(USER_TURN.format(colorToString(color)))
        println(
            lastStone?.let {
                LAST_STONE_POSITION.format(coordinateToString(lastStone))
            } ?: ""
        )
        val input = readln().trim()
        if (input[0] < 'A' || input[0] > 'Z') {
            return null
        }
        if (input.substring(1).toIntOrNull() == null) {
            return null
        }
        return VectorDTO(input[0] - 'A', input.substring(1).toInt() - 1)
    }

    override fun renderWinner(color: ColorDTO) {
        println(GAME_WINNER.format(colorToString(color)))
    }

    private fun colorToString(color: ColorDTO): String {
        return when (color) {
            ColorDTO.BLACK -> "흑"
            ColorDTO.WHITE -> "백"
        }
    }

    private fun coordinateToString(coordinate: VectorDTO): String {
        return ("%c%d".format(('A'.code + coordinate.x).toChar(), coordinate.y + 1))
    }

    companion object {
        private const val GAME_START = "오목 게임을 시작합니다."
        private const val USER_TURN = "%s의 차례입니다."
        private const val LAST_STONE_POSITION = " (마지막 돌의 위치 : %s) "
        private const val GAME_WINNER = "%s가 승리자입니다."
    }
}
