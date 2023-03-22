package woowacourse.omok.view

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import dto.ColorDTO
import dto.StoneDTO
import dto.VectorDTO
import view.GameView
import view.PlaceStoneObserver
import view.RenderBoard

class AndroidGameView(
    private val context: Context,
    override val renderBoard: RenderBoard,
    private val stoneButtons: List<ImageView>,
) : GameView {
    override val placeStoneObserver = PlaceStoneObserver()

    override fun renderStart(color: ColorDTO) {
        Toast.makeText(context, GAME_START, Toast.LENGTH_SHORT).show()
    }

    override fun setUpInput() {
        stoneButtons.forEachIndexed { index, imageButton ->
            imageButton.setOnClickListener {
                notifyPlaceStone(VectorDTO(index % 15, 15 - (index / 15) - 1))
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
        Toast.makeText(context, USER_TURN.format(colorToString(color)), Toast.LENGTH_SHORT).show()
    }

    override fun renderWinner(color: ColorDTO) {
        Toast.makeText(context, GAME_WINNER.format(colorToString(color)), Toast.LENGTH_SHORT).show()
    }

    override fun renderError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
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
    }
}
