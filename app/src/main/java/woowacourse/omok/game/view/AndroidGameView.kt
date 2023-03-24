package woowacourse.omok.game.view

import android.widget.ImageView
import android.widget.TextView
import domain.Board
import dto.ColorDTO
import dto.StoneDTO
import dto.VectorDTO
import view.GameView
import view.PlaceStoneObserver
import view.RenderBoard

class AndroidGameView(
    private val stoneButtons: List<ImageView>,
    private val message: TextView
) : GameView {
    override val placeStoneObserver = PlaceStoneObserver()
    override val renderBoard: RenderBoard = AndroidRenderBoard(stoneButtons)

    override fun renderStart(color: ColorDTO) {
        message.text = GAME_START
    }

    override fun setUpInput() {
        stoneButtons.forEachIndexed { index, imageButton ->
            imageButton.setOnClickListener {
                notifyPlaceStone(
                    VectorDTO(
                        index % Board.BOARD_SIZE.x,
                        Board.BOARD_SIZE.y - (index / Board.BOARD_SIZE.y) - FACTOR_ROW_FIXER
                    )
                )
            }
        }
    }

    override fun renderBoard(stones: Map<Int, StoneDTO>, size: VectorDTO) {
        renderBoard.render(stones, size)
    }

    override fun notifyPlaceStone(placedCoordinate: VectorDTO): Boolean {
        return placeStoneObserver.notify(placedCoordinate).all { it }
    }

    override fun renderTurn(color: ColorDTO, lastStone: VectorDTO?) {
        message.text = USER_TURN.format(colorToString(color))
    }

    override fun renderWinner(color: ColorDTO) {
        message.text = GAME_WINNER.format(colorToString(color))
    }

    override fun renderError(error: String) {
        message.text = error
    }

    private fun colorToString(color: ColorDTO): String {
        return when (color) {
            ColorDTO.BLACK -> "흑"
            ColorDTO.WHITE -> "백"
        }
    }

    companion object {
        private const val GAME_START = "오목 게임을 시작합니다."
        private const val USER_TURN = "%s의 차례입니다."
        private const val LAST_STONE_POSITION = " (마지막 돌의 위치 : %s) "
        private const val GAME_WINNER = "%s가 승리자입니다."

        private const val FACTOR_ROW_FIXER = 1
    }
}
