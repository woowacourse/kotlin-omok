package woowacourse.omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class OmokResultTest {
    @Test
    @DisplayName("마지막 플레이어가 black이면 black이 승리한다")
    fun winBlackStone() {
        // given
        val nowTurn = StoneColor.BLACK

        // when
        val actual = OmokResult.getWinner(nowTurn)
        val expected = OmokResult.BLACK_WIN

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    @DisplayName("마지막 플레이어가 white면 white가 승리한다")
    fun winWhiteStone() {
        // given
        val nowTurn = StoneColor.WHITE

        // when
        val actual = OmokResult.getWinner(nowTurn)
        val expected = OmokResult.WHITE_WIN

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
