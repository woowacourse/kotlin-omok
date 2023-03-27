package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BlackStoneTest {

    @Test
    fun `3-3 이면 흑돌은 해당 위치에 두어질 수 없다고 판단한다`() {
        // given
        val blackPlayer = Player()
        val whitePlayer = Player()
        val position = Position(HorizontalAxis.D, 12)

        // when
        blackPlayer.put(BlackStone(Position(HorizontalAxis.C, 12)))
        blackPlayer.put(BlackStone(Position(HorizontalAxis.E, 12)))
        blackPlayer.put(BlackStone(Position(HorizontalAxis.D, 13)))
        blackPlayer.put(BlackStone(Position(HorizontalAxis.D, 14)))

        val expect = BlackStone(position).judgePossibility(blackPlayer, whitePlayer)

        // then
        assertThat(expect).isTrue
    }

    @Test
    fun `4-4 면 흑돌은 해당 위치에 두어질 수 없다고 판단한다`() {
        // given
        val blackPlayer = Player()
        val whitePlayer = Player()
        val position = Position(HorizontalAxis.H, 5)

        // when
        blackPlayer.put(BlackStone(Position(HorizontalAxis.E, 5)))
        blackPlayer.put(BlackStone(Position(HorizontalAxis.F, 5)))
        blackPlayer.put(BlackStone(Position(HorizontalAxis.G, 5)))
        blackPlayer.put(BlackStone(Position(HorizontalAxis.H, 6)))
        blackPlayer.put(BlackStone(Position(HorizontalAxis.H, 7)))
        blackPlayer.put(BlackStone(Position(HorizontalAxis.H, 8)))

        val expect = BlackStone(position).judgePossibility(blackPlayer, whitePlayer)

        // then
        assertThat(expect).isTrue
    }

    @Test
    fun `3-3, 4-4 그 어느 것에도 속하지 않으면 흑돌은 해당 위치에 두어질 수 있다고 판단한다`() {
        // given
        val blackPlayer = Player()
        val whitePlayer = Player()
        val position = Position(HorizontalAxis.I, 5)

        // when
        blackPlayer.put(BlackStone(Position(HorizontalAxis.I, 6)))
        blackPlayer.put(BlackStone(Position(HorizontalAxis.I, 6)))

        val expect = BlackStone(position).judgePossibility(blackPlayer, whitePlayer)

        // then
        assertThat(expect).isFalse
    }
}
