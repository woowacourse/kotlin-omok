package woowacourse.omok.domain.rule

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import woowacourse.omok.domain.board.Board
import woowacourse.omok.domain.board.BoardSize
import woowacourse.omok.domain.board.CellState
import woowacourse.omok.domain.board.Point
import woowacourse.omok.domain.utils.generateCells
import woowacourse.omok.domain.utils.toPoint

class RuleValidatorTest {
    private lateinit var ruleValidator: RuleValidator

    @BeforeEach
    fun setUp() {
        ruleValidator = RuleValidator()
    }

    private fun createBoard(points: List<String>): Board = Board(BoardSize(15), generateCells(points.associateWith { CellState.BLACK }))

//            [3-3 금수 테스트를 위한 보드]
//   15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
//   14 ├──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//   13 ├──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//   12 ├──┼──●──A──●──┼──┼──┼──┼──┼──┼──┼──●──┼──┤
//   11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──C──┼──┼──┤
//   10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──●──┼──┤
//    9 ├──┼──┼──┼──┼──┼──┼──┼──┼──●──┼──┼──┼──●──┤
//    8 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//    7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//    6 ├──●──┼──┼──●──┼──┼──┼──┼──┼──●──┼──┼──┼──┤
//    5 ├──┼──●──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//    4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──D──┼──●──●──┤
//    3 ├──┼──┼──┼──B──┼──┼──┼──┼──┼──●──┼──┼──┼──┤
//    2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//    1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
//      A  B  C  D  E  F  G  H  I  J  K  L  M  N  O

    @ParameterizedTest
    @ValueSource(strings = ["D12", "E3", "L11", "K4"])
    fun `흑돌을 착수할 때 3-3 금수인 경우 True 반환`(rawPoint: String) {
        val board =
            createBoard(
                listOf(
                    "C12",
                    "E12",
                    "D14",
                    "D13",
                    "B6",
                    "C5",
                    "E5",
                    "E6",
                    "J9",
                    "M12",
                    "M10",
                    "N9",
                    "K6",
                    "K3",
                    "M4",
                    "N4",
                ),
            )
        assertTrue(ruleValidator.checkViolation(board, rawPoint.toPoint(), CellState.BLACK))
    }

//            [4-4 금수 테스트를 위한 보드]
//   15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
//   14 ├──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//   13 ├──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──●──┼──┼──┤
//   12 ├──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──●──┼──┼──┤
//   11 ├──●──●──●──A──┼──┼──┼──┼──┼──┼──●──┼──┼──┤
//   10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//    9 ├──┼──┼──┼──┼──┼──┼──┼──┼──●──●──C──●──┼──┤
//    8 ├──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┤
//    7 ├──┼──┼──┼──┼──┼──B──┼──┼──┼──┼──┼──┼──┼──┤
//    6 ├──┼──┼──┼──┼──●──┼──●──┼──┼──┼──┼──┼──┼──┤
//    5 ├──┼──┼──┼──●──┼──┼──┼──●──┼──┼──┼──┼──┼──┤
//    4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//    3 ├──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//    2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//    1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
//      A  B  C  D  E  F  G  H  I  J  K  L  M  N  O

    @ParameterizedTest
    @ValueSource(strings = ["E11", "G7", "L9"])
    fun `흑돌을 착수할 때 4-4 금수인 경우 True 반환`(rawPoint: String) {
        val board =
            createBoard(
                listOf(
                    "B14",
                    "B11",
                    "C13",
                    "C11",
                    "D12",
                    "D11",
                    "C3",
                    "E5",
                    "F6",
                    "F8",
                    "H6",
                    "I5",
                    "J9",
                    "K9",
                    "M9",
                    "L13",
                    "L12",
                    "L11",
                ),
            )
        assertTrue(ruleValidator.checkViolation(board, rawPoint.toPoint(), CellState.BLACK))
    }

//            [오목 테스트를 위한 보드]
//   15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
//   14 ├──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//   13 ├──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──●──┼──┤
//   12 ├──┼──┼──A──┼──┼──┼──┼──┼──┼──┼──●──┼──┼──┤
//   11 ├──┼──┼──┼──●──┼──┼──┼──┼──┼──●──┼──┼──┼──┤
//   10 ├──┼──┼──┼──┼──●──┼──┼──┼──●──┼──┼──┼──┼──┤
//    9 ├──┼──┼──┼──┼──┼──┼──┼──C──┼──┼──┼──┼──┼──┤
//    8 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//    7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──●──┼──┼──┤
//    6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──D──┼──┼──┤
//    5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──●──┼──┼──┤
//    4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──●──┼──┼──┤
//    3 ├──┼──B──●──●──●──●──┼──┼──┼──┼──●──┼──┼──┤
//    2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//    1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
//      A  B  C  D  E  F  G  H  I  J  K  L  M  N  O

    @ParameterizedTest
    @ValueSource(strings = ["D12", "C3", "I9", "L6"])
    fun `오목인 경우 True 반환`(rawPoint: String) {
        val board =
            createBoard(
                listOf(
                    "B14",
                    "C13",
                    "E11",
                    "F10",
                    "D3",
                    "E3",
                    "F3",
                    "G3",
                    "J10",
                    "K11",
                    "L12",
                    "M13",
                    "L3",
                    "L4",
                    "L5",
                    "L7",
                ),
            )
        assertTrue(ruleValidator.checkWinCondition(board, rawPoint.toPoint(), CellState.BLACK))
    }

    @Test
    fun `흑돌을 착수할 때 장목(Overline)인 경우 True 반환`() {
        val board = createBoard(listOf("A1", "B1", "C1", "D1", "F1"))
        assertTrue(ruleValidator.checkViolation(board, Point(5, 1), CellState.BLACK))
    }

    @Test
    fun `흑돌을 착수할 때 4-3인 경우 False 반환`() {
        val board = createBoard(listOf("E5", "F5", "G5", "H6", "H7"))
        assertFalse(ruleValidator.checkViolation(board, Point(8, 5), CellState.BLACK))
    }

    @Test
    fun `흑돌을 착수할 때 거짓 3-3 금수인 경우 False 반환`() {
        val board = createBoard(listOf("C3", "E3", "D2", "D4"))
        board.placeStone(Point(4, 5), CellState.WHITE, ruleValidator)
    }
}
