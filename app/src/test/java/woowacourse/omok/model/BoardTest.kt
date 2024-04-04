package woowacourse.omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.model.fixture.createPlayingBoard

class BoardTest {
    @Test
    fun `이미 돌이 있는 자리에 착수를 진행하면, 착수 실패임을 알린다`() {
        // given
        val placementInfo =
            PlacementInfo(
                createPlayingBoard(
                    Position(1, 3),
                    Position(9, 5),
                    Position(2, 3),
                    Position(10, 1),
                    Position(3, 3),
                    Position(10, 3),
                    Position(3, 4),
                    Position(12, 1),
                    Position(3, 5),
                    Position(12, 2),
                    Position(3, 6),
                    Position(11, 2),
                    Position(4, 3),
                    Position(11, 3),
                    Position(4, 4),
                    Position(11, 15),
                    Position(5, 3),
                    Position(11, 14),
                    Position(5, 5),
                ),
            )
        val board = Board(placementInfo)
        // when
        val actualResult = board.place(Position(1, 3))
        // then
        assertThat(actualResult.message).isEqualTo("중복된 곳에 착수할 수 없습니다.")
    }

    @Test
    fun `플레이어가 번갈아가며 착수하게 한다`() {
        // given
        val board = Board()
        // when
        board.place(Position(10, 2))
        board.place(Position(1, 1))
        // then
        assertThat(board.lastPlacement?.color).isEqualTo(Color.WHITE)
    }
}
