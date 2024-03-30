package omock

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.player.BlackPlayer
import woowacourse.omok.model.player.WhitePlayer
import woowacourse.omok.model.position.Column
import woowacourse.omok.model.position.Row
import woowacourse.omok.model.rule.LoadMap
import woowacourse.omok.model.search.VisitedDirectionResult
import woowacourse.omok.model.stone.Stone

class WhitePlayerTest {
    @Test
    fun `플레이어가 돌을 놓을 때, Board는 오목인 경우 true를 반환한다`() {
        val player = BlackPlayer()
        val board = Board.from()
        val loadMap = LoadMap(board.stoneStates)
        val stone = Stone.from(Row("1"), Column("A"))

        board.makeStones(
            player = player,
            coordinates = arrayOf("2B", "1B", "2A", "3A", "4A", "5A"),
        )
        board.setStoneState(player, stone)

        Assertions.assertThat(player.judgementResult(VisitedDirectionResult(loadMap.loadMap(stone)))).isTrue()
    }

    @Test
    fun `플레이어가 돌을 놓을 때, Board는 오목이 아닌 경우 false 반환한다`() {
        val player = WhitePlayer()
        val board = Board.from()
        val loadMap = LoadMap(board.stoneStates)
        val stone =
            Stone.from(Row("1"), Column("A"))

        board.makeStones(
            player = player,
            coordinates = arrayOf("2B", "1B", "2A", "3A", "5A"),
        )

        Assertions.assertThat(player.judgementResult(VisitedDirectionResult(loadMap.loadMap(stone)))).isFalse()
    }
}
