package woowacourse.omok.domain.model.rule.winning

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.omokboard.OmokGame
import woowacourse.omok.domain.model.omokboard.Position
import woowacourse.omok.domain.model.player.PlayerStone
import woowacourse.omok.domain.model.player.StoneColor
import woowacourse.omok.domain.model.rule.judge.DrawRule
import woowacourse.omok.domain.model.rule.judge.JudgeResult

class DrawRuleTest {
    private lateinit var omokGame: OmokGame

    @BeforeEach
    fun setup() {
        omokGame = OmokGame(OmokBoard.create(5, 5))
    }

    @Test
    fun `모든 칸이 채워지면 무승부를 반환한다`() {
        // given
        for (row in 1..5) {
            for (column in 1..5) {
                if (row == 5 && column == 5) break

                val stoneColor = if ((row + column) % 2 == 0) StoneColor.BLACK else StoneColor.WHITE
                omokGame.board.update(PlayerStone(stoneColor, Position(row, column)))
            }
        }

        // when
        val playerStone = PlayerStone(StoneColor.BLACK, Position(15, 15))
        val actual = DrawRule().perform(omokGame.board, playerStone)
        val expected = JudgeResult.Finished.Draw

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
