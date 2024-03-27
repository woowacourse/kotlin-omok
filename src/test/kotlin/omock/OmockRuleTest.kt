package omock

import omock.model.board.Board
import omock.model.player.BlackPlayer
import omock.model.position.Column
import omock.model.position.Row
import omock.model.rule.LoadMap
import omock.model.rule.OMockRule
import omock.model.search.VisitedDirectionFirstClearResult
import omock.model.search.VisitedDirectionResult
import omock.model.stone.Stone
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OmockRuleTest {
    @Test
    fun `BlackPlayer가 돌을 놓을 때, 놓은 지점이 Board에서 3-3이라면, 예외를 발생시킨다 `() {
        val player = BlackPlayer()
        val board = Board.from()
        val loadMap = LoadMap(board.stoneStates)
        val oMockRule = OMockRule()
        val stone = Stone.from(Row("12"), Column("D"))

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
    fun `BlackPlayer가 돌을 놓을 때, 놓은 지점이 Board에서 장목이라면, 예외를 발생시킨다 `() {
        val player = BlackPlayer()
        val board = Board.from()
        val loadMap = LoadMap(board.stoneStates)
        val stone = Stone.from(Row("13"), Column("C"))
        val oMockRule = OMockRule()

        board.makeStones(
            player = player,
            coordinates = arrayOf("15C", "14C", "12C", "11C", "10C"),
        )
        board.setStoneState(player, stone)

        assertThrows<IllegalArgumentException> {
            oMockRule.checkRules(
                VisitedDirectionResult(loadMap.loadMap(stone)),
                VisitedDirectionFirstClearResult(loadMap.firstClearLoadMap(stone))
            )
        }
    }

    @Test
    fun `BlackPlayer가 돌을 놓을 때, 놓은 지점이 Board에서 열린 4라면, 예외를 발생시킨다 `() {
        val player = BlackPlayer()
        val board = Board.from()
        val loadMap = LoadMap(board.stoneStates)
        val stone = Stone.from(Row("3"), Column("E"))
        val oMockRule = OMockRule()

        board.makeStones(
            player = player,
            coordinates = arrayOf("6B", "5C", "6E", "5E"),
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
