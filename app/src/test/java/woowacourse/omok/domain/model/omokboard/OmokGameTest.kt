package woowacourse.omok.domain.model.omokboard

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.POSITION_ONE_ONE
import woowacourse.omok.domain.model.game.OmokGame

class OmokGameTest {
    @Test
    fun `오목판 내에서 (1,1) 위치에 검은돌을 놓는다`() {
        // given
        val omokGame = OmokGame()

        // when
        omokGame.placeStone(POSITION_ONE_ONE)
        val board = omokGame.game.board.snapshot
        val actual = board.values.first()
        val expected = PointState.OCCUPIED_BLACK

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
