package woowacourse.omok.domain.model.omokboard

import woowacourse.omok.domain.model.player.PlayerStone
import woowacourse.omok.domain.model.player.StoneColor
import woowacourse.omok.domain.model.rule.judge.DrawRule
import woowacourse.omok.domain.model.rule.judge.JudgeResult
import woowacourse.omok.domain.model.rule.judge.JudgeRules
import woowacourse.omok.domain.model.rule.judge.WinningRule
import woowacourse.omok.domain.model.rule.place.AlreadyExistStoneRule
import woowacourse.omok.domain.model.rule.place.ExternalRule
import woowacourse.omok.domain.model.rule.place.InvalidPositionRule
import woowacourse.omok.domain.model.rule.place.PlaceResult
import woowacourse.omok.domain.model.rule.place.PlaceRules

class OmokGame(
    val board: OmokBoard = OmokBoard.create(),
    private val placeRules: PlaceRules = PlaceRules(listOf(InvalidPositionRule(), AlreadyExistStoneRule(), ExternalRule())),
    private val judgeRules: JudgeRules = JudgeRules(listOf(WinningRule(), DrawRule())),
    firstStone: StoneColor = INITIAL_STONE_COLOR,
) {
    var currentTurn: StoneColor = firstStone
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
        currentTurn = INITIAL_STONE_COLOR
        board.clear()
    }

    companion object {
        private val INITIAL_STONE_COLOR = StoneColor.BLACK
    }
}
