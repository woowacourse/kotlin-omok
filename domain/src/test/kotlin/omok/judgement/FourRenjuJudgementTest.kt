package omok.judgement

import omok.HorizontalAxis
import omok.Player
import omok.Position
import omok.Stone
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

class FourRenjuJudgementTest {

    @Test
    fun `4-4(가로, 가로)이면 True를 반환한다`() {
        // given
        val whitePlayer = Player()
        val position = Position(HorizontalAxis.F, 12)

        // when
        val blackPlayer = Player(
            listOf(
                Stone(Position(HorizontalAxis.C, 12)),
                Stone(Position(HorizontalAxis.D, 12)),
                Stone(Position(HorizontalAxis.G, 12)),
                Stone(Position(HorizontalAxis.I, 12)),
                Stone(Position(HorizontalAxis.J, 12))
            )
        )
        val fourJudgement = FourJudgement(blackPlayer, whitePlayer, position)

        // then
        assertThat(fourJudgement.check()).isTrue
    }

    @Test
    fun `4-4(가로, 주대각선)이면 True 반환한다`() {
        // given
        val whitePlayer = Player()
        val position = Position(HorizontalAxis.I, 8)

        // when
        val blackPlayer = Player(
            listOf(
                Stone(Position(HorizontalAxis.H, 8)),
                Stone(Position(HorizontalAxis.J, 8)),
                Stone(Position(HorizontalAxis.K, 8)),
                Stone(Position(HorizontalAxis.J, 9)),
                Stone(Position(HorizontalAxis.H, 7)),
                Stone(Position(HorizontalAxis.F, 5))
            )
        )
        val fourJudgement = FourJudgement(blackPlayer, whitePlayer, position)

        // then
        assertThat(fourJudgement.check()).isTrue
    }
}
