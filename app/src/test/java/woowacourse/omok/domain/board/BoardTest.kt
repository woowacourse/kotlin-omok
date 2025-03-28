package woowacourse.omok.domain.board

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import woowacourse.omok.domain.board.result.Finished
import woowacourse.omok.domain.board.result.OnGoing
import woowacourse.omok.domain.rule.RuleValidator
import woowacourse.omok.domain.utils.generateCells
import woowacourse.omok.domain.utils.toPoint

class BoardTest {
    private fun createBoard(cells: List<String> = emptyList()): Board =
        Board(BoardSize(15), generateCells(cells.associateWith { CellState.BLACK }), RuleValidator())

    @Test
    fun `원하는 크기의 바둑판을 생성할 수 있다`() {
        val size = BoardSize(15)
        val board = Board(size, judge = RuleValidator())

        assertThat(board.size).isEqualTo(15)
    }

    @Test
    fun `초기 보드는 모든 점이 NONE이다`() {
        createBoard().cells.forEach { (_, state) ->
            assertThat(state).isEqualTo(CellState.EMPTY)
        }
    }

    @Test
    fun `초기화 시 지정된 상태를 가진 보드가 정상적으로 설정되어야 한다`() {
        val initialPoints = mapOf(Point(1, 1) to CellState.BLACK, Point(2, 2) to CellState.WHITE)
        val customBoard = Board(BoardSize(15), initialPoints, validator = RuleValidator())

        assertThat(customBoard.findStoneColor(Point(1, 1))).isEqualTo(CellState.BLACK)
        assertThat(customBoard.findStoneColor(Point(2, 2))).isEqualTo(CellState.WHITE)
        assertThat(customBoard.findStoneColor(Point(3, 3))).isEqualTo(CellState.EMPTY)
    }

    @Test
    fun `해당 좌표에 아무 돌도 없다면 돌을 둘 수 있다`() {
        val position = Point(1, 1)
        val board = createBoard()
        board.placeStone(position, CellState.WHITE)

        val actual = board.findStoneColor(position)

        assertThat(actual).isEqualTo(CellState.WHITE)
    }

    @Test
    fun `Point에 이미 돌이 있다면 돌을 둘 수 없다`() {
        val position = Point(1, 1)
        val board = createBoard()
        board.placeStone(position, CellState.WHITE)

        val actual = board.placeStone(position, CellState.BLACK)
        val expected = OnGoing.AlreadyPlaced

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `보드에서 원하는 좌표의 돌의 유무를 찾을 수 있다`() {
        val position = Point(1, 1)
        val board = createBoard()

        val actual = board.findStoneColor(position)
        val expected = CellState.EMPTY

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흰돌은 착수 시 금수를 판단하지 않는다`() {
        val cells =
            generateCells(
                listOf("C3", "D4", "F4", "G3").associateWith { CellState.WHITE },
            )
        val board = Board(BoardSize(15), cells, RuleValidator())
        val result = board.placeStone("E5".toPoint(), CellState.WHITE)
        val actual = result is OnGoing.StonePlaced

        assertTrue(actual)
    }

    @Test
    fun `금수로 판단되면 RuleViolation를 반환한다`() {
        val board = createBoard(listOf("C3", "D4", "F4", "G3"))
        val result = board.placeStone("E5".toPoint(), CellState.BLACK)
        val actual = result is OnGoing.RuleViolation
        assertTrue(actual)
    }

    @Test
    fun `오목이면 GameFinished를 반환한다`() {
        val board = createBoard(listOf("C3", "C4", "C5", "C6"))
        val result = board.placeStone("C7".toPoint(), CellState.BLACK)
        val actual = result is Finished.GameFinished

        assertTrue(actual)
    }

    @ParameterizedTest
    @CsvSource("-1, 5", "5, -1", "26, 1", "1, 26")
    fun `바둑판 크기를 벗어난 위치에 두려고 하면 InvalidMove를 반환한다`(
        x: Int,
        y: Int,
    ) {
        val board = createBoard()
        val result = board.placeStone(Point(x, y), CellState.BLACK)
        val actual = result is OnGoing.InvalidMove

        assertTrue(actual)
    }

    @Test
    fun `바둑판에 더 이상 둘 공간이 없다면 BoardFull를 반환한다`() {
        val cells = (1..15).flatMap { x -> (1..15).map { y -> Point(x, y) to CellState.BLACK } }.toMap()
        val board = Board(BoardSize(15), cells.filter { it.key != Point(15, 15) }, RuleValidator())
        val result = board.placeStone(Point(15, 15), CellState.WHITE)
        val actual = result is Finished.BoardFull

        assertTrue(actual)
    }
}
