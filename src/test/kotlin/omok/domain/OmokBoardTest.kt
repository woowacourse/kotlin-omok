package omok.domain

import omok.domain.turn.BlackTurn
import omok.domain.turn.PutStoneResult
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OmokBoardTest {
    private lateinit var omokBoard: OmokBoard

    @BeforeEach
    fun setUp() {
        omokBoard = OmokBoard()
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

    @Test
    fun `좌표에 이미 돌이 있으면 예외를 던진다`() {
        // given
        val position = Position(1, 2)
        val stone = Stone(position, StoneState.BLACK)
        // when
        omokBoard.putStone(stone)
        // then
        val result = BlackTurn().putStone(position, omokBoard)
        assertThat(result).isEqualTo(PutStoneResult.Failure("이미 돌이 있습니다. 다시 입력해주세요."))
    }
}
