package omok.model.state

import android.util.Log
import omok.model.Board
import omok.model.OmokStone
import omok.model.Position
import omok.model.StoneColor
import omok.model.rule.GeneralStonePlaceRule
import omok.model.rule.StonePlaceRule

class BlackTurn(stonePlaceRule: StonePlaceRule, board: Board) : Running(stonePlaceRule, board) {
    override fun put(position: Position): GameState {
        val newStone = OmokStone(position, StoneColor.BLACK)
        if (canPut(newStone)) {
            Log.d("흑돌 테스트", "${position}에 놓았어요!")
            val newBoard = board + newStone
            if (newBoard.isInOmok(position)) return Finish(newBoard)
            return WhiteTurn(GeneralStonePlaceRule, newBoard)
        }
        return this
    }
}
