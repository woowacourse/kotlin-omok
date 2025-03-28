package woowacourse.omok.ui

import android.widget.TableLayout
import android.widget.Toast
import omok.domain.board.OmokBoard
import omok.domain.place.Black
import omok.domain.place.Place
import omok.domain.place.Protected
import omok.domain.place.White
import omok.domain.rule.OmokRules
import omok.view.ext.toLabel
import woowacourse.omok.R
import woowacourse.omok.ui.ext.getPointAt
import woowacourse.omok.ui.ext.removeAllEvent
import woowacourse.omok.ui.ext.setView

class OmokUi(
    val omokBoard: OmokBoard,
    val omokRules: OmokRules,
    val layout: TableLayout,
) {
    init {
        setBoard(omokBoard.latestPlace.opponent())
    }

    private fun setBoard(target: Place) {
        layout.setView { x, y, view ->
            view.setOnClickListener {
                val stone = if (target is Black) Black(x, y) else White(x, y)
                runCatching {
                    omokBoard.addStone(stone)
                }.onFailure {
                    Toast.makeText(view.context, it.message, Toast.LENGTH_SHORT).show()
                }.onSuccess {
                    if (printBoard(omokBoard, x, y)) setBoard(target.opponent())
                }
            }
        }
    }

    private fun printBoard(
        omokBoard: OmokBoard,
        currentX: Int,
        currentY: Int,
    ): Boolean {
        var flag = true
        layout.setView { x, y, view ->
            val stone = omokBoard.getPointAt(x, y)
            if (stone is Protected) {
                view.setImageResource(R.drawable.protected_stone)
            }

            if (currentX == x && currentY == y) {
                if (stone is Black) {
                    view.setImageResource(R.drawable.black_stone)
                }
                if (stone is White) {
                    view.setImageResource(R.drawable.white_stone)
                }

                if (omokRules.isOmok(stone, omokBoard)) {
                    Toast.makeText(view.context, "${stone.toLabel()}이 승리하였습니다", Toast.LENGTH_SHORT).show()
                    layout.removeAllEvent()
                    flag = false
                }
            }
        }
        return flag
    }
}
