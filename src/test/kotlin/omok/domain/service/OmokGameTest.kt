package omok.domain.service

import omok.domain.omokboard.OmokBoard
import omok.domain.omokboard.PlayingBoard
import omok.domain.omokboard.Position
import omok.domain.player.StoneColor
import omok.domain.rule.OmokRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokGameTest {
    @Test
    fun `플레이어는 게임을 마칠 때까지 한 번씩 번갈아가며 돌을 둘 수 있다`() {
        // given
        val blackTurns =
            mutableListOf(
                Position(1, 'A'),
                Position(1, 'B'),
                Position(1, 'C'),
                Position(1, 'D'),
                Position(1, 'E'),
            )
        val whiteTurns =
            mutableListOf(
                Position(7, 'A'),
                Position(7, 'B'),
                Position(7, 'C'),
                Position(7, 'D'),
            )

        // when & then
        val omokGame = OmokGame(PlayingBoard(OmokBoard.create(), OmokRule.rules))
        var expected = StoneColor.WHITE

        omokGame.start({ stoneColor, _ ->
            expected = expected.reversed()
            assertThat(stoneColor).isEqualTo(expected)
            when (stoneColor) {
                StoneColor.BLACK -> blackTurns.removeFirst()
                StoneColor.WHITE -> whiteTurns.removeFirst()
            }
        }, {
        })
    }
}
