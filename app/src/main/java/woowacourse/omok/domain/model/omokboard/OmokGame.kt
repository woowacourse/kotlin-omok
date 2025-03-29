package woowacourse.omok.domain.model.omokboard

import woowacourse.omok.domain.model.player.PlayerStone
import woowacourse.omok.domain.model.player.StoneColor
import woowacourse.omok.domain.model.rule.judge.JudgeResult
import woowacourse.omok.domain.model.rule.judge.JudgeRules
import woowacourse.omok.domain.model.rule.place.PlaceResult
import woowacourse.omok.domain.model.rule.place.PlaceRules

class OmokGame(
    val board: OmokBoard = OmokBoard.create(),
    private val placeRules: PlaceRules = PlaceRules(),
    private val judgeRules: JudgeRules = JudgeRules(),
    savedTurn: StoneColor = StoneColor.BLACK,
) {
    var currentTurn: StoneColor = savedTurn
        private set

    fun placeStone(position: Position): PlaceResult {
        val playerStone = PlayerStone(currentTurn, position)
        val result: PlaceResult = placeRules.perform(board, playerStone)

        if (result is PlaceResult.Success) {
            board.update(playerStone)
        }

        return result
    }

    fun judge(playerStone: PlayerStone): JudgeResult {
        val result: JudgeResult = judgeRules.perform(board, playerStone)

        if (result is JudgeResult.NotFinished) {
            reverseTurn()
        }

        return result
    }

    fun reverseTurn() {
        currentTurn = currentTurn.reversed()
    }

    fun restart() {
        currentTurn = StoneColor.BLACK
        board.clear()
    }
}
