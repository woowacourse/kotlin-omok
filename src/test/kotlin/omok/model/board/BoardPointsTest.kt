package omok.model.board

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class BoardPointsTest {
    private lateinit var boardPoints: BoardPoints

    @BeforeEach
    fun setUp() {
        boardPoints = BoardPoints(BoardSize(15))
    }

    @Test
    fun `원하는 크기의 Point 목록을 생성할 수 있다`() {
        val size = BoardSize(15)
        val boardPoints = BoardPoints(size)

        assertThat(boardPoints.size).isEqualTo(size)
    }

    @ValueSource(ints = [14, 26])
    @ParameterizedTest
    fun `바둑판의 크기가 15~25사이가 아니라면 예외가 발생한다`(size: Int) {
        assertThrows<IllegalArgumentException> { BoardSize(size) }
    }

    @Test
    fun `초기 보드는 모든 점이 null이다`() {
        boardPoints.points.forEach { (_, state) ->
            assertEquals(null, state)
        }
    }

    @Test
    fun `특정 위치의 상태를 변경하면 해당 위치의 상태가 변경되어야 한다`() {
        val point = Point(2, 3)
        boardPoints.update(point, StoneColor.BLACK)
        assertEquals(StoneColor.BLACK, boardPoints.getState(point))
    }

    @Test
    fun `초기화 시 지정된 상태를 가진 보드가 정상적으로 설정되어야 한다`() {
        val initialPoints = mapOf(Point(1, 1) to StoneColor.BLACK, Point(2, 2) to StoneColor.WHITE)
        val customBoard = BoardPoints(BoardSize(15), initialPoints)

        assertEquals(StoneColor.BLACK, customBoard.getState(Point(1, 1)))
        assertEquals(StoneColor.WHITE, customBoard.getState(Point(2, 2)))
        assertEquals(null, customBoard.getState(Point(3, 3)))
    }
}
