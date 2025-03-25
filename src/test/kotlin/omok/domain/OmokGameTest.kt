package woowacourse.omok.domain

import omok.beforeDoubleFour
import omok.beforeDoubleThree
import omok.beforeFinished
import omok.beforeOverLine
import omok.domain.turn.PutStoneResult
import omok.toFinishedPosition
import omok.toViolationPosition
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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
        omokGame.putStone(position)
        // when
        val result = omokGame.putStone(position)
        // then
        assertThat(result).isEqualTo(PutStoneResult.Failure("이미 돌이 있습니다. 다시 입력해주세요."))
    }

    @Test
    fun `검은 돌이 삼삼 자리에 돌을 두면 예외를 던진다`() {
        // given
        beforeDoubleThree().forEach { position -> omokGame.putStone(position) }
        // when
        val result = omokGame.putStone(toViolationPosition())
        // then
        assertThat(result).isEqualTo(PutStoneResult.Failure("잘못된 위치입니다. 다시 입력해주세요."))
    }

    @Test
    fun `검은 돌이 사사 자리에 돌을 두면 예외를 던진다`() {
        // given
        beforeDoubleFour().forEach { position -> omokGame.putStone(position) }
        // when
        val result = omokGame.putStone(toViolationPosition())
        // then
        assertThat(result).isEqualTo(PutStoneResult.Failure("잘못된 위치입니다. 다시 입력해주세요."))
    }

    @Test
    fun `검은 돌이 장목 자리에 돌을 두면 예외를 던진다`() {
        // given
        beforeOverLine().forEach { position -> omokGame.putStone(position) }
        // when
        val result = omokGame.putStone(toViolationPosition())
        // then
        assertThat(result).isEqualTo(PutStoneResult.Failure("잘못된 위치입니다. 다시 입력해주세요."))
    }

    @Test
    fun `같은 돌 5개가 놓이면 Finished된다`() {
        // given
        beforeFinished().forEach { position -> omokGame.putStone(position) }
        // when
        val result = omokGame.putStone(toFinishedPosition())
        // then
        assertThat(result).isEqualTo(PutStoneResult.Finished(StoneState.BLACK))
    }
}
