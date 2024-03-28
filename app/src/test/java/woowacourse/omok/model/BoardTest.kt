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
                    Position(HorizontalCoordinate.ONE, VerticalCoordinate.C),
                    Position(HorizontalCoordinate.NINE, VerticalCoordinate.E),
                    Position(HorizontalCoordinate.TWO, VerticalCoordinate.C),
                    Position(HorizontalCoordinate.TEN, VerticalCoordinate.A),
                    Position(HorizontalCoordinate.THREE, VerticalCoordinate.C),
                    Position(HorizontalCoordinate.TEN, VerticalCoordinate.C),
                    Position(HorizontalCoordinate.THREE, VerticalCoordinate.D),
                    Position(HorizontalCoordinate.TWELVE, VerticalCoordinate.A),
                    Position(HorizontalCoordinate.THREE, VerticalCoordinate.E),
                    Position(HorizontalCoordinate.TWELVE, VerticalCoordinate.B),
                    Position(HorizontalCoordinate.THREE, VerticalCoordinate.F),
                    Position(HorizontalCoordinate.ELEVEN, VerticalCoordinate.B),
                    Position(HorizontalCoordinate.FOUR, VerticalCoordinate.C),
                    Position(HorizontalCoordinate.ELEVEN, VerticalCoordinate.C),
                    Position(HorizontalCoordinate.FOUR, VerticalCoordinate.D),
                    Position(HorizontalCoordinate.ELEVEN, VerticalCoordinate.O),
                    Position(HorizontalCoordinate.FIVE, VerticalCoordinate.C),
                    Position(HorizontalCoordinate.ELEVEN, VerticalCoordinate.N),
                    Position(HorizontalCoordinate.FIVE, VerticalCoordinate.E),
                ),
            )
        val board = Board(placementInfo)
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
