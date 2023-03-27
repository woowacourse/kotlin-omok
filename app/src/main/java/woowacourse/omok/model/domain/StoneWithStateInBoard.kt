package woowacourse.omok.model.domain

import model.domain.OmokGame
import model.domain.state.Omok
import model.domain.tools.Board
import model.domain.tools.Location
import model.domain.tools.Stone
import woowacourse.omok.model.domain.StoneWithStateInBoard.PlaceState.ABLE
import woowacourse.omok.model.domain.StoneWithStateInBoard.PlaceState.DISABLE
import woowacourse.omok.model.domain.StoneWithStateInBoard.PlaceState.OMOK

class StoneWithStateInBoard {
    val omokGame: OmokGame by lazy { OmokGame(Board.create()) }

    private var _stoneColor: Stone = Stone.EMPTY
    val stoneColor: Stone get() = _stoneColor

    private var _placeState: PlaceState = ABLE
    val placeState: PlaceState get() = _placeState

    fun placeStone(number: Int) {
        val location = Location(number / SIZE, number % SIZE)

        omokGame.apply {
            _stoneColor = state.stoneColor
            val nextStoneColor = getNextStoneColor(location)

            canPlaceStone(nextStoneColor)
        }
    }

    private fun canPlaceStone(nextStoneColor: Stone) {
        if (_stoneColor == nextStoneColor) {
            checkWinnerIfOmok()
            return
        }
        _placeState = ABLE
    }

    private fun getNextStoneColor(location: Location): Stone {
        val x = location.coordinationX.value
        val y = location.coordinationY.value

        omokGame.play { return@play Pair(x, y) }

        return omokGame.state.stoneColor
    }

    private fun checkWinnerIfOmok() {
        if (omokGame.state is Omok) {
            _placeState = OMOK
            return
        }
        _placeState = DISABLE
    }

    sealed class PlaceState {
        object OMOK : PlaceState()
        object DISABLE : PlaceState()
        object ABLE : PlaceState()
    }

    companion object {
        private const val SIZE = 15
    }
}
