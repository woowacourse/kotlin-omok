package omok.domain.game

import kotlinx.coroutines.runBlocking
import omok.domain.board.OmokBoard
import omok.domain.place.Black
import omok.domain.place.Place
import omok.domain.rule.OmokRules
import omok.event.GameEventListner
import omok.global.retryWhenException

class OmokGame(
    private val omokBoard: OmokBoard,
    private val rules: OmokRules,
) {
    fun startGame(event: GameEventListner) {
        event.onGameStart()
        var place: Place = retryWhenFailedToAddStone(event)
        while (omokBoard.isNotFull() && !isFinished(place, event)) {
            place =
                retryWhenFailedToAddStone(event) {
                    place.toggle(getInputPoint(event))
                }
        }
    }

    private fun isFinished(
        place: Place,
        event: GameEventListner,
    ): Boolean {
        if (rules.isOmok(place, omokBoard)) {
            event.onBoardView(omokBoard)
            event.onFinished(place)
            return true
        }
        return false
    }

    private fun retryWhenFailedToAddStone(
        event: GameEventListner,
        action: () -> Place = { Black(getInputPoint(event)) },
    ): Place {
        return retryWhenException {
            val stone = action()
            omokBoard.addStone(stone)
            stone
        }
    }

    private fun getInputPoint(event: GameEventListner): String {
        event.onBoardView(omokBoard)
        return runBlocking { event.onInputRequest(omokBoard.latestPlace) }
    }
}
