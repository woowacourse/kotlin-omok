package woowacourse.omok.presentation.gameactivity

import android.widget.TextView
import domain.domain.Board
import domain.domain.OmokGame
import domain.domain.Position
import domain.view.AlphabetCoordinate
import kotlin.properties.Delegates

class LastPositionView(private val value: TextView, omokGame: OmokGame) {

    var lastPosition: Position? by Delegates.observable(omokGame.board.lastPosition) { _, _, new ->
        if (new == null) {
            value.text = ""
        } else {
            value.text = getPrintFormatPosition(new)
        }
    }

    init {
        lastPosition?.let {
            value.text =
                getPrintFormatPosition(it)
        }
    }

    private fun getPrintFormatPosition(position: Position): String =
        AlphabetCoordinate.convertAlphabet(position.coordinateX).name + ((Board.BOARD_SIZE - position.coordinateY).toString())
}
