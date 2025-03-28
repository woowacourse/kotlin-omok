package woowacourse.omok.domain.model.omokboard

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.POSITION_ONE_ONE

class OmokGameTest {
    @Test
    fun `오목판 내에서 (1,1) 위치에 검은돌을 놓는다`() {
        // given
        val omokGame = OmokGame()

        // when
        omokGame.placeStone(emptyList(), POSITION_ONE_ONE)
        val board = omokGame.board.snapshot
        val actual = board.values.first()
        val expected = IntersectionState.OCCUPIED_BLACK

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
