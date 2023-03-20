package omok.domain.judgement

import omok.domain.HorizontalAxis
import omok.domain.Player
import omok.domain.Position
import omok.domain.Stone
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

class FourJudgementTest {

    @Test
    fun `4-4(가로, 가로)이면 True를 반환한다`() {
        // given
        val blackPlayer = Player()
        val whitePlayer = Player()
        val position = Position(HorizontalAxis.F, 12)
        val fourJudgement = FourJudgement(blackPlayer, whitePlayer, position)

        // when
        blackPlayer.put(Stone(Position(HorizontalAxis.C, 12)))
        blackPlayer.put(Stone(Position(HorizontalAxis.D, 12)))
        blackPlayer.put(Stone(Position(HorizontalAxis.G, 12)))
        blackPlayer.put(Stone(Position(HorizontalAxis.I, 12)))
        blackPlayer.put(Stone(Position(HorizontalAxis.J, 12)))

        // then
        assertThat(fourJudgement.check()).isTrue
    }

    @Test
    fun `4-4(가로, 주대각선)이면 True 반환한다`() {
        // given
        val blackPlayer = Player()
        val whitePlayer = Player()
        val position = Position(HorizontalAxis.I, 8)
        val fourJudgement = FourJudgement(blackPlayer, whitePlayer, position)

        // when
        blackPlayer.put(Stone(Position(HorizontalAxis.H, 8)))
        blackPlayer.put(Stone(Position(HorizontalAxis.J, 8)))
        blackPlayer.put(Stone(Position(HorizontalAxis.K, 8)))
        blackPlayer.put(Stone(Position(HorizontalAxis.J, 9)))
        blackPlayer.put(Stone(Position(HorizontalAxis.H, 7)))
        blackPlayer.put(Stone(Position(HorizontalAxis.F, 5)))

        // then
        assertThat(fourJudgement.check()).isTrue
    }
}
