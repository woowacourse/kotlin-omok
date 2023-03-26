package omok.judgement

import omok.HorizontalAxis
import omok.Player
import omok.Position
import omok.Stone
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

class ThreeJudgementTest {
    @Test
    fun `3-3(가로, 부대각선)이면 True를 반환한다`() {
        val blackPlayer = Player(
            listOf(
                Stone(Position(HorizontalAxis.B, 6)),
                Stone(Position(HorizontalAxis.C, 5)),
                Stone(Position(HorizontalAxis.D, 3)),
                Stone(Position(HorizontalAxis.F, 3))
            )
        )
        val whitePlayer = Player(listOf(Stone(Position(HorizontalAxis.K, 3))))
        val position = Position(HorizontalAxis.E, 3)
        val threeJudgement = ThreeJudgement(blackPlayer, whitePlayer, position)

        assertThat(threeJudgement.check()).isTrue
    }

    @Test
    fun `3-3(세로, 주대각선)이면 True를 반환한다`() {
        val blackPlayer = Player(
            listOf(
                Stone(Position(HorizontalAxis.K, 6)),
                Stone(Position(HorizontalAxis.K, 3)),
                Stone(Position(HorizontalAxis.L, 5)),
                Stone(Position(HorizontalAxis.J, 3))
            )
        )
        val whitePlayer = Player(listOf(Stone(Position(HorizontalAxis.J, 4))))
        val position = Position(HorizontalAxis.K, 4)
        val threeJudgement = ThreeJudgement(blackPlayer, whitePlayer, position)

        // then
        assertThat(threeJudgement.check()).isTrue
    }

    @Test
    fun `검은 돌이 있는 3-3은 조건을 충족하지 못해 false를 반환한다`() {
        // given
        val blackPlayer = Player(
            listOf(
                Stone(Position(HorizontalAxis.K, 6)),
                Stone(Position(HorizontalAxis.K, 3)),
                Stone(Position(HorizontalAxis.M, 4)),
                Stone(Position(HorizontalAxis.N, 4))
            )
        )
        val whitePlayer = Player(listOf(Stone(Position(HorizontalAxis.L, 4))))
        val position = Position(HorizontalAxis.K, 4)
        val threeJudgement = ThreeJudgement(blackPlayer, whitePlayer, position)

        // then
        assertThat(threeJudgement.check()).isFalse
    }
}
