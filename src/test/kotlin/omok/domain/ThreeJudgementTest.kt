package omok.domain

import omok.domain.judgement.ThreeJudgement
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

class ThreeJudgementTest {
    @Test
    fun `3-3(가로, 부대각선)이면 True를 반환한다`() {
        // given
        val blackPlayer = Player()
        val whitePlayer = Player()
        val position = Position(HorizontalAxis.E, 3)
        val threeJudgement = ThreeJudgement(blackPlayer, whitePlayer, position)

        // when
        blackPlayer.put(BlackStone(Position(HorizontalAxis.B, 6)))
        blackPlayer.put(BlackStone(Position(HorizontalAxis.C, 5)))
        whitePlayer.put(WhiteStone(Position(HorizontalAxis.K, 3)))
        blackPlayer.put(BlackStone(Position(HorizontalAxis.D, 3)))
        blackPlayer.put(BlackStone(Position(HorizontalAxis.F, 3)))

        // then
        assertThat(threeJudgement.check()).isTrue
    }

    @Test
    fun `3-3(세로, 주대각선)이면 True를 반환한다`() {
        // given
        val blackPlayer = Player()
        val whitePlayer = Player()
        val position = Position(HorizontalAxis.K, 4)
        val threeJudgement = ThreeJudgement(blackPlayer, whitePlayer, position)

        // when
        blackPlayer.put(BlackStone(Position(HorizontalAxis.K, 6)))
        blackPlayer.put(BlackStone(Position(HorizontalAxis.K, 3)))
        whitePlayer.put(WhiteStone(Position(HorizontalAxis.J, 4)))
        blackPlayer.put(BlackStone(Position(HorizontalAxis.L, 5)))
        blackPlayer.put(BlackStone(Position(HorizontalAxis.J, 3)))

        // then
        assertThat(threeJudgement.check()).isTrue
    }

    @Test
    fun `검은 돌이 있는 3-3은 조건을 충족하지 못해 false를 반환한다`() {
        // given
        val blackPlayer = Player()
        val whitePlayer = Player()
        val position = Position(HorizontalAxis.K, 4)
        val threeJudgement = ThreeJudgement(blackPlayer, whitePlayer, position)

        // when
        blackPlayer.put(BlackStone(Position(HorizontalAxis.K, 6)))
        blackPlayer.put(BlackStone(Position(HorizontalAxis.K, 3)))
        whitePlayer.put(WhiteStone(Position(HorizontalAxis.L, 4)))
        blackPlayer.put(BlackStone(Position(HorizontalAxis.M, 4)))
        blackPlayer.put(BlackStone(Position(HorizontalAxis.N, 4)))

        // then
        assertThat(threeJudgement.check()).isFalse
    }
}
