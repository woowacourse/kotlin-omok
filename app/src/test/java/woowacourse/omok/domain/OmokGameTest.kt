package woowacourse.omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.beforeDoubleFour
import woowacourse.omok.beforeDoubleThree
import woowacourse.omok.beforeFinished
import woowacourse.omok.beforeOverLine
import woowacourse.omok.toFinishedPosition
import woowacourse.omok.toViolationPosition

class OmokGameTest {
    private lateinit var omokBoard: OmokBoard
    private lateinit var omokGame: OmokGame

    @BeforeEach
    fun setUp() {
        omokBoard = OmokBoard(rule = OmokAdapter())
        omokGame = OmokGame(omokBoard)
    }

    @Test
    fun `좌표에 이미 돌이 있으면 예외를 던진다`() {
        // given
        val position = Position(1, 2)
        val stone = Stone(position, StoneState.BLACK)
        omokGame.putStone(stone)
        // when
        val result = omokGame.putStone(stone)
        // then
        assertThat(result).isEqualTo(PutStoneResult.AlreadyPlaced)
    }

    @Test
    fun `검은 돌이 삼삼 자리에 돌을 두면 Violation을 반환한다`() {
        // given
        val turn = StoneState.BLACK
        beforeDoubleThree().forEach { position ->
            omokGame.putStone(Stone(position, turn))
            omokGame.changeTurn()
        }
        // when
        val result = omokGame.putStone(Stone(toViolationPosition(), StoneState.BLACK))
        // then
        assertThat(result).isEqualTo(PutStoneResult.Violation)
    }

    @Test
    fun `검은 돌이 사사 자리에 돌을 두면 Violation을 반환한다`() {
        // given
        val turn = StoneState.BLACK
        beforeDoubleFour().forEach { position ->
            omokGame.putStone(Stone(position, turn))
            omokGame.changeTurn()
        }
        // when
        val result = omokGame.putStone(Stone(toViolationPosition(), StoneState.BLACK))
        // then
        assertThat(result).isEqualTo(PutStoneResult.Violation)
    }

    @Test
    fun `검은 돌이 장목 자리에 돌을 두면 Violation을 반환한다`() {
        // given
        val turn = StoneState.BLACK
        beforeOverLine().forEach { position ->
            omokGame.putStone(Stone(position, turn))
            omokGame.changeTurn()
        }
        // when
        val result = omokGame.putStone(Stone(toViolationPosition(), StoneState.BLACK))
        // then
        assertThat(result).isEqualTo(PutStoneResult.Violation)
    }

    @Test
    fun `같은 돌 5개가 놓이면 Finished를 반환한다`() {
        // given
        val turn = StoneState.BLACK
        beforeFinished().forEach { position ->
            omokGame.putStone(Stone(position, turn))
            omokGame.changeTurn()
        }
        // when
        val result = omokGame.putStone(Stone(toFinishedPosition(), StoneState.BLACK))
        // then
        assertThat(result).isEqualTo(PutStoneResult.Finished)
    }

    @Test
    fun `보드 범위를 벗어나는 위치에 돌을 놓으면 InvalidPosition을 반환한다`() {
        // given
        val position = Position(0, 20)
        // when
        val result = omokGame.putStone(Stone(position, StoneState.BLACK))
        // then
        assertThat(result).isEqualTo(PutStoneResult.InvalidPosition)
    }

    @Test
    fun `정상적으로 돌을 놓으면 Success를 반환한다`() {
        // given
        val position = Position(1, 1)
        // when
        val result = omokGame.putStone(Stone(position, StoneState.BLACK))
        // then
        assertThat(result).isEqualTo(PutStoneResult.Success)
    }
}
