package woowacourse.omok

import android.widget.ImageView
import omok.domain.Board
import omok.domain.FiveRule
import omok.domain.Position
import omok.domain.Stone
import omok.domain.StoneType
import omok.domain.Turn
import woowacourse.omok.database.StoneDAO

class Game(
    private val omokBoard: Board,
    private val turn: Turn,
    private val stoneDao: StoneDAO,
) {
    private val fiveRule = FiveRule()

    fun putStone(
        view: ImageView,
        row: Int,
        column: Int,
        color: StoneType,
    ) {
        if (!omokBoard.isFull() && !omokBoard.isInvalidPosition(Position(row, column)) &&
            !omokBoard.isInvalidBlackPosition(
                Stone(Position(row, column), turn.color),
            )
        ) {
            omokBoard.put(Position(row, column), turn.color)
            val stoneColor = if (color == StoneType.WHITE) "white" else "black"
            stoneDao.insertStone(row, column, stoneColor)
            if (color == StoneType.WHITE) {
                view.setImageResource(R.drawable.white_stone)
            } else {
                view.setImageResource(R.drawable.black_stone)
            }
        }
    }

    fun checkOmok(): Boolean {
        val lastStone = omokBoard.stones.lastStone()
        if (lastStone != null) {
            return fiveRule.isOmok(lastStone, omokBoard.stones)
        }
        return false
    }
}
