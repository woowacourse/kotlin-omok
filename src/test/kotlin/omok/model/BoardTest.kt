package omok.model

import omok.model.fixture.createPlayingBoard
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `이미 돌이 있는 자리에 착수를 진행하면, 착수 실패임을 알린다`() {
        // given
        val status = createPlayingBoard()
        val board = Board(status)
        // when
        val actualResult = board.place(Position(HorizontalCoordinate.ONE, VerticalCoordinate.C))
        // then
        assertThat(actualResult.message).isEqualTo("중복된 곳에 착수할 수 없습니다.")
    }

    @Test
    fun `플레이어가 번갈아가며 착수하게 한다`() {
        // given
        val board = Board()
        // when
        board.place(Position(HorizontalCoordinate.TEN, VerticalCoordinate.B))
        board.place(Position(HorizontalCoordinate.ONE, VerticalCoordinate.A))
        // then
        assertThat(board.lastPlacement?.color).isEqualTo(Color.WHITE)
    }
}
