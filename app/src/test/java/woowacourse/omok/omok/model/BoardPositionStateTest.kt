package omok.model

import omok.model.entity.Stone
import omok.model.entity.board.BoardPositionState
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class BoardPositionStateTest {
    @Test
    fun `오목판 위치의 상태는 비어있음, 흑돌 있음, 백돌 있음 중 하나이다`() {
        assertAll(
            { assertThat(BoardPositionState.Empty).isInstanceOf(BoardPositionState::class.java) },
            { assertThat(BoardPositionState.Exist.Black).isInstanceOf(BoardPositionState::class.java) },
            { assertThat(BoardPositionState.Exist.White).isInstanceOf(BoardPositionState::class.java) },
        )
    }

    @Test
    fun `돌을 놓을 때, 해당 상태가 비어있지 않을 경우 예외를 발생시킨다`() {
        // given:
        // when:
        val state = BoardPositionState.Exist.White

        // then:
        assertThrows<IllegalStateException> { state.stateWithStone(Stone.BLACK) }
    }

    @Test
    fun `돌을 놓을 때, 해당 상태가 비어있는 경우 흑돌을 전달하면 흑돌이 있는 상태를 반환한다`() {
        // given:
        val state = BoardPositionState.Empty

        // when:
        val actual = state.stateWithStone(Stone.BLACK)

        // then:
        assertThat(actual).isEqualTo(BoardPositionState.Exist.Black)
    }

    @Test
    fun `돌을 놓을 때, 해당 상태가 비어있는 경우 백돌을 전달하면 백돌이 있는 상태를 반환한다`() {
        // given:
        val state = BoardPositionState.Empty

        // when:
        val actual = state.stateWithStone(Stone.WHITE)

        // then:
        assertThat(actual).isEqualTo(BoardPositionState.Exist.White)
    }
}
