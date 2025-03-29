package woowacourse.omok.domain.model.omokboard

import woowacourse.omok.domain.model.player.PlayerStone
import woowacourse.omok.domain.model.player.StoneColor
import woowacourse.omok.domain.model.rule.judge.JudgeResult
import woowacourse.omok.domain.model.rule.judge.JudgeRule
import woowacourse.omok.domain.model.rule.place.PlaceResult
import woowacourse.omok.domain.model.rule.place.PlaceRule

class OmokGame(
    val board: OmokBoard = OmokBoard.create(),
    private val placeRules: List<PlaceRule> = emptyList(),
    private val judgeRules: List<JudgeRule> = emptyList(),
    savedTurn: StoneColor = StoneColor.BLACK,
) {
    var currentTurn: StoneColor = savedTurn
        private set

    fun placeStone(position: Position): PlaceResult {
        var result: PlaceResult = PlaceResult.Success
        val playerStone = PlayerStone(currentTurn, position)

        placeRules.forEach { rule ->
            result = rule.perform(board, playerStone) as PlaceResult
            if (result is PlaceResult.Failure) return result
        }

        if (result is PlaceResult.Success) {
            board.update(playerStone)
        }

        return result
    }

    fun judge(playerStone: PlayerStone): JudgeResult {
        var result: JudgeResult = JudgeResult.NotFinished

        judgeRules.forEach { rule ->
            result = rule.perform(board, playerStone) as JudgeResult
            if (result is JudgeResult.Finished) return result
        }

        currentTurn = currentTurn.reversed()

        return result
    }

    fun restart() {
        currentTurn = StoneColor.BLACK
        board.clear()
    }
}
