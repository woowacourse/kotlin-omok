package woowacourse.omok.domain.model.omokboard

import woowacourse.omok.domain.model.player.PlayerStone
import woowacourse.omok.domain.model.player.StoneColor
import woowacourse.omok.domain.model.rule.judge.DrawRule
import woowacourse.omok.domain.model.rule.judge.JudgeResult
import woowacourse.omok.domain.model.rule.judge.JudgeRule
import woowacourse.omok.domain.model.rule.judge.WinningRule
import woowacourse.omok.domain.model.rule.place.AlreadyExistStoneRule
import woowacourse.omok.domain.model.rule.place.ExternalRule
import woowacourse.omok.domain.model.rule.place.InvalidPositionRule
import woowacourse.omok.domain.model.rule.place.PlaceResult
import woowacourse.omok.domain.model.rule.place.PlaceRule

class OmokGame(
    val board: OmokBoard = OmokBoard.create(),
    firstTurn: StoneColor = StoneColor.BLACK,
) {
    var currentTurn = firstTurn
        private set

    fun placeStone(
        rules: List<PlaceRule> = listOf(InvalidPositionRule(), AlreadyExistStoneRule(), ExternalRule()),
        position: Position,
    ): PlaceResult {
        var result: PlaceResult = PlaceResult.Success
        val playerStone = PlayerStone(currentTurn, position)

        rules.forEach { rule ->
            result = rule.perform(board, playerStone) as PlaceResult
            if (result is PlaceResult.Failure) return result
        }

        if (result is PlaceResult.Success) {
            board.update(playerStone)
        }

        return result
    }

    fun judge(
        rules: List<JudgeRule> = listOf(WinningRule(), DrawRule()),
        playerStone: PlayerStone,
    ): JudgeResult {
        var result: JudgeResult = JudgeResult.NotFinished

        rules.forEach { rule ->
            result = rule.perform(board, playerStone) as JudgeResult
            if (result is JudgeResult.Finished) return result
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
