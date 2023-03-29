package console

import dto.ColorDTO
import dto.StoneDTO
import dto.VectorDTO
import dto.VectorSystem
import error.OmokResult
import error.StoneReadError
import view.GameView
import view.PlaceStoneObserver
import view.RenderBoard

class ConsoleGameView(
    override val renderBoard: RenderBoard = ConsoleRenderBoard()
) : GameView {
    override val placeStoneObserver: PlaceStoneObserver = PlaceStoneObserver()

    override fun renderStart(color: ColorDTO) {
        println(GAME_START)
        println(USER_TURN.format(colorToString(color)))
    }

    override fun setUpInput() {
        while (true) {
            val input = processStoneRead() ?: continue
            if (notifyPlaceStone(input)) break
        }
    }

    private fun readStone(): StoneReadError {
        val input = readln().trim()
        if (input.isEmpty()) {
            return StoneReadError.Empty
        }
        if (input[0] < 'A' || input[0] > 'Z') {
            return StoneReadError.ColumnNotAlpha
        }
        if (input.substring(1).toIntOrNull() == null) {
            return StoneReadError.RowNotNumeric
        }
        return OmokResult.Success(ConsoleVectorSystem(VectorDTO(input[0] - 'A', input.substring(1).toInt() - 1)))
    }

    private fun processStoneRead(): VectorSystem? {
        when (val input = readStone()) {
            is OmokResult.Success<*> -> {
                return input.value as VectorSystem
            }
            else -> {
                renderError(input.message)
            }
        }
        return null
    }

    override fun renderBoard(stones: Map<Int, StoneDTO>, size: VectorDTO) {
        println(renderBoard.render(stones, size))
    }

    override fun notifyPlaceStone(placedCoordinate: VectorSystem): Boolean {
        return placeStoneObserver.notify(placedCoordinate).all { it }
    }

    override fun renderTurn(color: ColorDTO, lastStone: VectorDTO?) {
        print(USER_TURN.format(colorToString(color)))
        println(
            lastStone?.let {
                LAST_STONE_POSITION.format(coordinateToString(lastStone))
            } ?: ""
        )
    }

    override fun renderWinner(color: ColorDTO) {
        println(GAME_WINNER.format(colorToString(color)))
    }

    override fun renderError(error: String) {
        println(error)
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
