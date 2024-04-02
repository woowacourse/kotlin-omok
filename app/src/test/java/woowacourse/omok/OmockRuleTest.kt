package omock

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.omok.model.GameState
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.player.BlackPlayer
import woowacourse.omok.model.position.Column
import woowacourse.omok.model.position.Row
import woowacourse.omok.model.rule.LoadMap
import woowacourse.omok.model.rule.OMockRule
import woowacourse.omok.model.ruletype.FourToFourCount
import woowacourse.omok.model.ruletype.IsClearFourToFourCount
import woowacourse.omok.model.ruletype.IsReverseTwoAndThree
import woowacourse.omok.model.ruletype.ThreeToThreeCount
import woowacourse.omok.model.search.VisitedDirectionFirstClearResult
import woowacourse.omok.model.search.VisitedDirectionResult
import woowacourse.omok.model.stone.Stone

class OmockRuleTest {
    @Test
    fun `BlackPlayer가 돌을 놓을 때, 놓은 지점이 Board에서 3-3이라면, 예외를 발생시킨다 `() {
        val player = BlackPlayer()
        val board = Board.from()
        val loadMap = LoadMap(board.stoneStates)
        val oMockRule =
            OMockRule(
                ruleTypes = listOf(ThreeToThreeCount),
            )
        val stone = (Stone.from(Row("12"), Column("D")) as GameState.LoadStone.Success).stone

        board.makeStones(
            player = player,
            coordinates = arrayOf("12C", "12E", "13D", "14D"),
        )
        board.setStoneState(player, stone)

        assertThrows<IllegalArgumentException> {
            oMockRule.checkRules(
                VisitedDirectionResult(loadMap.loadMap(stone)),
                VisitedDirectionFirstClearResult(loadMap.firstClearLoadMap(stone)),
            )
        }
    }

    @Test
    fun `BlackPlayer가 돌을 놓을 때, 놓은 지점이 Board에서 4-4라면, 예외를 발생시킨다 `() {
        val player = BlackPlayer()
        val board = Board.from()
        val loadMap = LoadMap(board.stoneStates)
        val oMockRule =
            OMockRule(
                ruleTypes = listOf(FourToFourCount),
            )
        val stone = (Stone.from(Row("12"), Column("D")) as GameState.LoadStone.Success).stone

        board.makeStones(
            player = player,
            coordinates = arrayOf("12C", "12E", "13D", "14D", "12F", "11D"),
        )
        board.setStoneState(player, stone)

        assertThrows<IllegalArgumentException> {
            oMockRule.checkRules(
                VisitedDirectionResult(loadMap.loadMap(stone)),
                VisitedDirectionFirstClearResult(loadMap.firstClearLoadMap(stone)),
            )
        }
    }

    @Test
    fun `BlackPlayer가 돌을 놓을 때, 놓은 지점이 Board에서 장목이라면, 예외를 발생시킨다 `() {
        val player = BlackPlayer()
        val board = Board.from()
        val loadMap = LoadMap(board.stoneStates)
        val stone = (Stone.from(Row("13"), Column("C")) as GameState.LoadStone.Success).stone
        val oMockRule =
            OMockRule(
                ruleTypes = listOf(IsReverseTwoAndThree),
            )

        board.makeStones(
            player = player,
            coordinates = arrayOf("15C", "14C", "12C", "11C", "10C"),
        )
        board.setStoneState(player, stone)

        assertThrows<IllegalArgumentException> {
            oMockRule.checkRules(
                VisitedDirectionResult(loadMap.loadMap(stone)),
                VisitedDirectionFirstClearResult(loadMap.firstClearLoadMap(stone)),
            )
        }
    }

    @Test
    fun `BlackPlayer가 돌을 놓을 때, 놓은 지점이 Board에서 열린 4라면, 예외를 발생시킨다 `() {
        val player = BlackPlayer()
        val board = Board.from()
        val loadMap = LoadMap(board.stoneStates)
        val stone = (Stone.from(Row("3"), Column("E")) as GameState.LoadStone.Success).stone
        val oMockRule =
            OMockRule(
                ruleTypes = listOf(IsClearFourToFourCount),
            )

        board.makeStones(
            player = player,
            coordinates = arrayOf("6B", "5C", "6E", "5E", "2E", "2F"),
        )
        board.setStoneState(player, stone)

        assertThrows<IllegalArgumentException> {
            oMockRule.checkRules(
                VisitedDirectionResult(loadMap.loadMap(stone)),
                VisitedDirectionFirstClearResult(loadMap.firstClearLoadMap(stone)),
            )
        }
    }
}
