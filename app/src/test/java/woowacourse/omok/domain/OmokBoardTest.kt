package woowacourse.omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OmokBoardTest {
    private lateinit var omokBoard: OmokBoard

    @BeforeEach
    fun setUp() {
        omokBoard = OmokBoard(rule = OmokAdapter())
    }

    @Test
    fun `좌표와 돌 상태를 받으면 해당 위치에 돌을 놓는다`() {
        // given
        val position = Position(1, 2)
        val stone = Stone(position, StoneState.BLACK)
        // when
        omokBoard.putStone(stone)
        // then
        assertThat(omokBoard.getStoneState(position)).isEqualTo(StoneState.BLACK)
    }
}
