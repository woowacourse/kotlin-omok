package omok.model.state

import omok.model.Board
import omok.model.OmokStone
import omok.model.StoneColor
import omok.model.event.PutEvent
import omok.model.rule.GeneralStonePlaceRule
import omok.model.rule.StonePlaceRule

class BlackTurn(stonePlaceRule: StonePlaceRule, board: Board) : Running(stonePlaceRule, board) {
    override fun put(putEvent: PutEvent): GameState {
        val position = putEvent.onPutBlack.onPlace()
        val newStone = OmokStone(position, StoneColor.BLACK)
        if (canPut(newStone)) {
            val newBoard = board + newStone
            if (newBoard.isInOmok(position)) return Finish(newBoard)
            return WhiteTurn(GeneralStonePlaceRule, newBoard)
        }
        return put(putEvent)
    }
}
