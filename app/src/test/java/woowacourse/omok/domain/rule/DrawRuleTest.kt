package woowacourse.omok.domain.rule

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.omokboard.ColumnPosition
import woowacourse.omok.domain.omokboard.PlayingBoard
import woowacourse.omok.domain.omokboard.Position
import woowacourse.omok.domain.omokboard.RowPosition
import woowacourse.omok.domain.placeresult.GameFinish
import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.player.StoneColor

class DrawRuleTest {
    private lateinit var playingBoard: PlayingBoard

    @BeforeEach
    fun setup() {
        playingBoard = PlayingBoard(ruleNavigation = RuleNavigation(OmokRule.whiteRules, OmokRule.blackRules))
    }

    @Test
    fun `모든 칸이 채워지면 무승부를 반환한다`() {
        // given
        var board = playingBoard.board
        for (row in 1..15) {
            for (column in 1..15) {
                if (row == 15 && column == 15) break

                val stoneColor = if ((row + column) % 2 == 0) StoneColor.BLACK else StoneColor.WHITE
                board = board.updateBoard(PlayerStone(stoneColor, Position(RowPosition(row), ColumnPosition(column))))
            }
        }

        // when
        val playerStone = PlayerStone(StoneColor.BLACK, Position(RowPosition(15), ColumnPosition(15)))
        val actual = DrawRule().place(board, playerStone)
        val expected = GameFinish(GameResult.DRAW)

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
