package omok.domain.game

import omok.domain.board.OmokBoard
import omok.domain.rule.OmokRules
import omok.domain.stone.Black
import omok.domain.stone.Stone
import omok.event.GameEventListner
import omok.global.retryWhenException

class OmokGame(
    private val omokBoard: OmokBoard,
    private val rules: OmokRules,
) {
    fun startGame(event: GameEventListner) {
        event.onGameStart()
        var stone: Stone = retryWhenFailedToAddStone(event)
        while (omokBoard.isNotFull() && !isFinished(stone, event)) {
            stone =
                retryWhenFailedToAddStone(event) {
                    stone.toggle(getInputPoint(event))
                }
        }
    }

    private fun isFinished(
        stone: Stone,
        event: GameEventListner,
    ): Boolean {
        if (rules.isOmok(stone, omokBoard)) {
            event.onFinished(stone)
            return true
        }
        return false
    }

    private fun retryWhenFailedToAddStone(
        event: GameEventListner,
        action: () -> Stone = { Black(getInputPoint(event)) },
    ): Stone {
        return retryWhenException {
            val stone = action()
            omokBoard.addStone(stone)
            stone
        }
    }

    private fun getInputPoint(event: GameEventListner): String {
        event.onBoardView(omokBoard)
        return event.onInputRequest(omokBoard.latestStone)
    }
}
