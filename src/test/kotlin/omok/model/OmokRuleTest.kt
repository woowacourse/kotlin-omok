package omok.model

import omok.library.BlackStoneOmokRule
import omok.library.WhiteStoneOmokRule
import omok.view.BoardOutputView
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OmokRuleTest {
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board()
    }

    @Test
    fun `checkThreeThree를 확인한다 - 첫번째 경우`() {
        listOf(C12, E12, D13, D14).map {
            board.placeStone(Coordinate(it.x, it.y), PositionType.BLACK_STONE)
        }
        BoardOutputView.printBoard(board)
        assertThat(BlackStoneOmokRule.isThreeThree(3, 11, board.getBoardLayout())).isEqualTo(true)
    }

    @Test
    fun `checkThreeThree를 확인한다 - 두번째 경우`() {
        listOf(M12, M10, N9, J9).map {
            board.placeStone(Coordinate(it.x, it.y), PositionType.BLACK_STONE)
        }
        assertThat(BlackStoneOmokRule.isThreeThree(11, 10, board.getBoardLayout())).isEqualTo(true)
    }

    @Test
    fun `checkThreeThree를 확인한다 - 세번째 경우`() {
        listOf(K3, K6, M4, N4).map {
            board.placeStone(Coordinate(it.x, it.y), PositionType.BLACK_STONE)
        }
        assertThat(BlackStoneOmokRule.isThreeThree(10, 3, board.getBoardLayout())).isEqualTo(true)
    }

    @Test
    fun `checkThreeThree를 확인한다 - 네번째 경우`() {
        listOf(B6, C5, E5, E6).map {
            board.placeStone(Coordinate(it.x, it.y), PositionType.BLACK_STONE)
        }
        assertThat(BlackStoneOmokRule.isThreeThree(4, 2, board.getBoardLayout())).isEqualTo(true)
    }

    @Test
    fun `countFourFour를 확인한다 - 첫번째 경우`() {
        listOf(C12, D12, G12, I12, J12).map {
            board.placeStone(Coordinate(it.x, it.y), PositionType.BLACK_STONE)
        }
        assertThat(BlackStoneOmokRule.isFourFour(5, 11, board.getBoardLayout())).isEqualTo(true)
    }

    @Test
    fun `countFourFour를 확인한다 - 두번째 경우`() {
        listOf(C10, C11, C12, E6, F5, G4).map {
            board.placeStone(Coordinate(it.x, it.y), PositionType.BLACK_STONE)
        }
        assertThat(BlackStoneOmokRule.isFourFour(2, 7, board.getBoardLayout())).isEqualTo(true)
    }

    @Test
    fun `countFourFour를 확인한다 - 세번째 경우`() {
        listOf(J6, J8, J9, J12).map {
            board.placeStone(Coordinate(it.x, it.y), PositionType.BLACK_STONE)
        }
        assertThat(BlackStoneOmokRule.isFourFour(9, 9, board.getBoardLayout())).isEqualTo(true)
    }

    @Test
    fun `checkMoreThanFive를 확인한다`() {
        listOf(A1, A2, A3, A4, A5).map {
            board.placeStone(Coordinate(it.x, it.y), PositionType.BLACK_STONE)
        }
        assertThat(BlackStoneOmokRule.isMoreThanFive(0, 5, board.getBoardLayout())).isEqualTo(true)
    }

    @Test
    fun `백돌이 오목인지 확인한다`() {
        listOf(A1, A2, A3, A4, A5).map {
            board.placeStone(Coordinate(it.x, it.y), PositionType.WHITE_STONE)
        }
        BoardOutputView.printBoard(board)
        assertThat(
            WhiteStoneOmokRule.isOmok(0, 4, board.getBoardLayout()),
        ).isEqualTo(true)
    }

    @Test
    fun `흑돌이 오목인지 확인한다`() {
        listOf(A1, A2, A3, A4, A5).map {
            board.placeStone(Coordinate(it.x, it.y), PositionType.BLACK_STONE)
        }
        assertThat(BlackStoneOmokRule.isOmok(0, 4, board.getBoardLayout())).isEqualTo(true)
    }
}
