package woowacourse.omok.controller

import android.widget.TableLayout
import android.widget.Toast
import omok.domain.board.OmokBoard
import omok.domain.place.Black
import omok.domain.place.Place
import omok.domain.place.Protected
import omok.domain.place.White
import omok.domain.rule.OmokRules
import omok.event.GameEventListner
import omok.view.ext.toLabel
import woowacourse.omok.R
import woowacourse.omok.event.getPointAt
import woowacourse.omok.event.removeAllEvent
import woowacourse.omok.event.setEvent

class AndroidOmokController(
    val omokBoard: OmokBoard,
    val omokRules: OmokRules,
    val layout: TableLayout,
    val event: GameEventListner,
) {
    init {
        setBoard(omokBoard.latestPlace.opponent())
    }

    private fun setBoard(target: Place) {
        layout.setEvent { x, y, view ->
            if (omokBoard.getPointAt(x, y) is Protected && omokBoard.getPointAt(x, y) is Black) {
                Toast.makeText(view.context, "금수 자리입니다", Toast.LENGTH_SHORT).show()
            }
            val stone = if (target is Black) Black(x, y) else White(x, y)
            omokBoard.addStone(stone)
            printBoard(omokBoard, x, y)
        }
    }

    private fun printBoard(
        omokBoard: OmokBoard,
        currentX: Int,
        currentY: Int,
    ) {
        layout.setEvent { x, y, view ->
            if (currentX == x && currentY == y) {
                val stone = omokBoard.getPointAt(x, y)
                if (stone is Black) {
                    view.setImageResource(R.drawable.black_stone)
                    setBoard(stone.opponent())
                }
                if (stone is White) {
                    view.setImageResource(R.drawable.white_stone)
                    setBoard(stone.opponent())
                }

                if (stone is Protected) {
                    view.setImageResource(R.drawable.protected_stone)
                }

                if (omokRules.isOmok(stone, omokBoard)) {
                    Toast.makeText(view.context, "${stone.toLabel()}이 승리하였습니다", Toast.LENGTH_SHORT).show()
                    layout.removeAllEvent()
                }
            }
        }
    }
}
