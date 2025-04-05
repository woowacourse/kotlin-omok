package woowacourse.omok.domain.model.game

import woowacourse.omok.domain.model.omokboard.Position
import woowacourse.omok.domain.model.player.PlayerStone
import woowacourse.omok.domain.model.rule.judge.JudgeResult
import woowacourse.omok.domain.model.rule.place.PlaceResult

class OmokGame(
    val game: OmokGameEntity = OmokGameEntity(),
) {
    val currentTurn get() = game.currentTurn

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
        game.reverseTurn()
    }

    fun restart() {
        game.board.clear()
    }
}
