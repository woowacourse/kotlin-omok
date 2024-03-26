package omock

import omock.model.board.Board
import omock.model.player.BlackPlayer
import omock.model.position.Column
import omock.model.position.Row
import omock.model.rule.LoadMap
import omock.model.rule.OMockRule
import omock.model.stone.Stone
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OmockRuleTest {
    @Test
    fun `BlackPlayer가 돌을 놓을 때, 놓은 지점이 Board에서 3-3이라면, 예외를 발생시킨다 `() {
        val player = BlackPlayer()
        val board = Board.from()
        val loadMap = LoadMap(board.stoneStates)
        board.makeStones(
            player = player,
            coordinates = arrayOf("12C", "12E", "13D", "14D"),
        )

        val stone = Stone.from(Row("12"), Column("D"))
        board.setStoneState(player, stone)
        val oMockRule = OMockRule()
        val visited = loadMap.loadMap(stone)
        val visitedFirstClear = loadMap.firstClearLoadMap(stone)
        assertThrows<IllegalArgumentException> { oMockRule.checkRules(visited, visitedFirstClear) }
    }

    @Test
    fun `BlackPlayer가 돌을 놓을 때, 놓은 지점이 Board에서 장목이라면, 예외를 발생시킨다 `() {
        val player = BlackPlayer()
        val board = Board.from()
        val loadMap = LoadMap(board.stoneStates)
        board.makeStones(
            player = player,
            coordinates = arrayOf("15C", "14C", "12C", "11C", "10C"),
        )

        val stone = Stone.from(Row("13"), Column("C"))
        board.setStoneState(player, stone)
        val oMockRule = OMockRule()
        val visited = loadMap.loadMap(stone)
        val visitedFirstClear = loadMap.firstClearLoadMap(stone)
        assertThrows<IllegalArgumentException> { oMockRule.checkRules(visited, visitedFirstClear) }
    }

    @Test
    fun `BlackPlayer가 돌을 놓을 때, 놓은 지점이 Board에서 열린 4라면, 예외를 발생시킨다 `() {
        val player = BlackPlayer()
        val board = Board.from()
        val loadMap = LoadMap(board.stoneStates)
        board.makeStones(
            player = player,
            coordinates = arrayOf("6B", "5C", "6E", "5E"),
        )

        val stone = Stone.from(Row("3"), Column("E"))
        board.setStoneState(player, stone)
        val oMockRule = OMockRule()
        val visited = loadMap.loadMap(stone)
        val visitedFirstClear = loadMap.firstClearLoadMap(stone)
        assertThrows<IllegalArgumentException> { oMockRule.checkRules(visited, visitedFirstClear) }
    }
}
