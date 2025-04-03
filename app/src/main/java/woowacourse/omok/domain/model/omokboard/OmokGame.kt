package woowacourse.omok.domain.model.omokboard

import woowacourse.omok.domain.model.player.PlayerStone
import woowacourse.omok.domain.model.player.StoneColor
import woowacourse.omok.domain.model.rule.judge.JudgeResult
import woowacourse.omok.domain.model.rule.place.PlaceResult

class OmokGame(
    val game: OmokGameEntity = OmokGameEntity(),
) {
    var currentTurn: StoneColor = game.lastTurn
        private set

    fun placeStone(position: Position): PlaceResult {
        val playerStone = PlayerStone(currentTurn, position)
        val result: PlaceResult = game.placeRules.perform(game.board, playerStone)

        if (result is PlaceResult.Success) {
            game.board.update(playerStone)
        }

        return result
    }

    fun judge(playerStone: PlayerStone): JudgeResult {
        val result: JudgeResult = game.judgeRules.perform(game.board, playerStone)

        if (result is JudgeResult.NotFinished) {
            reverseTurn()
        }

        return result
    }

    fun reverseTurn() {
        currentTurn = currentTurn.reversed()
    }

    fun restart() {
        currentTurn = INITIAL_STONE_COLOR
        game.board.clear()
    }

    companion object {
        private val INITIAL_STONE_COLOR = StoneColor.BLACK
    }
}
