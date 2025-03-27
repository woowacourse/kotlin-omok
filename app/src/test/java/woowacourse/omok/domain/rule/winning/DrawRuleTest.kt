package woowacourse.omok.domain.rule.winning

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.omokboard.OmokBoard
import woowacourse.omok.domain.omokboard.PlayingBoard
import woowacourse.omok.domain.omokboard.Position
import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.player.StoneColor
import woowacourse.omok.domain.rule.judge.DrawRule
import woowacourse.omok.domain.rule.judge.JudgeResult

class DrawRuleTest {
    private lateinit var playingBoard: PlayingBoard

    @BeforeEach
    fun setup() {
        playingBoard = PlayingBoard(OmokBoard.create(5, 5))
    }

    @Test
    fun `모든 칸이 채워지면 무승부를 반환한다`() {
        // given
        for (row in 1..5) {
            for (column in 1..5) {
                if (row == 5 && column == 5) break

                val stoneColor = if ((row + column) % 2 == 0) StoneColor.BLACK else StoneColor.WHITE
                playingBoard.board.update(PlayerStone(stoneColor, Position(row, column)))
            }
        }

        // when
        val playerStone = PlayerStone(StoneColor.BLACK, Position(15, 15))
        val actual = DrawRule().perform(playingBoard.board, playerStone)
        val expected = JudgeResult.Finished.Draw

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
