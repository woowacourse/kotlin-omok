package woowacourse.omok.domain.service

import woowacourse.omok.domain.omokboard.PlayingBoard
import woowacourse.omok.domain.omokboard.Position
import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.player.StoneColor
import woowacourse.omok.domain.rule.judge.DrawRule
import woowacourse.omok.domain.rule.judge.JudgeResult
import woowacourse.omok.domain.rule.judge.JudgeRule
import woowacourse.omok.domain.rule.judge.WinningRule
import woowacourse.omok.domain.rule.place.AlreadyExistStoneRule
import woowacourse.omok.domain.rule.place.ExternalRule
import woowacourse.omok.domain.rule.place.InvalidPositionRule
import woowacourse.omok.domain.rule.place.PlaceResult
import woowacourse.omok.domain.rule.place.PlaceRule

class OmokGame(
    private val playingBoard: PlayingBoard,
    private val placeRules: List<PlaceRule> = listOf(InvalidPositionRule(), AlreadyExistStoneRule(), ExternalRule()),
    private val judgeRules: List<JudgeRule> = listOf(WinningRule(), DrawRule()),
) {
    fun start(
        getNewPosition: (StoneColor, Position?) -> Position,
        onPlaceTried: (PlaceResult) -> Unit,
    ): JudgeResult.Finished {
        var stoneColor = StoneColor.BLACK
        var position: Position? = null

        while (true) {
            val newPosition: Position = getNewPosition(stoneColor, position)
            val playerStone: PlayerStone = PlayerStone(stoneColor, newPosition)
            val placeResult: PlaceResult = playingBoard.placeStone(placeRules, playerStone)

            onPlaceTried(placeResult)
            if (placeResult is PlaceResult.Failure) continue

            stoneColor = stoneColor.reversed()
            position = playerStone.position
            return when (val gameResult = playingBoard.judge(judgeRules, playerStone)) {
                is JudgeResult.Finished.Win -> gameResult
                is JudgeResult.Finished.Draw -> gameResult
                JudgeResult.NotFinished -> continue
            }
        }
    }
}
