package woowacourse.omok.domain.omokboard

import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.player.StoneColor
import woowacourse.omok.domain.rule.judge.JudgeResult
import woowacourse.omok.domain.rule.judge.JudgeRule
import woowacourse.omok.domain.rule.place.PlaceResult
import woowacourse.omok.domain.rule.place.PlaceRule

class PlayingBoard(
    private val board: OmokBoard = OmokBoard.create(),
) {
    var stoneColor = StoneColor.BLACK
        private set

    fun placeStone(
        rules: List<PlaceRule>,
        position: Position,
    ): PlaceResult {
        var result: PlaceResult = PlaceResult.Success

        rules.forEach { rule ->
            val playerStone = PlayerStone(stoneColor, position)
            result = rule.perform(board, playerStone) as PlaceResult
            if (result is PlaceResult.Failure) return result
        }

        if (result is PlaceResult.Success) {
            board
                .find(position)
                ?.updateState(stoneColor)
        }

        return result
    }

    fun judge(
        rules: List<JudgeRule>,
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
        stoneColor = stoneColor.reversed()
    }
}
