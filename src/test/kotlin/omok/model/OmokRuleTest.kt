package omok.model

import omok.view.BoardView
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
            board.layout[it.coordinate.x][it.coordinate.y] = PositionType.BLACK_STONE
        }
        assertThat(OmokRule(board.layout).checkThreeThree(3, 11)).isEqualTo(true)
    }

    @Test
    fun `checkThreeThree를 확인한다 - 두번째 경우`() {
        listOf(M12, M10, N9, J9).map {
            board.layout[it.coordinate.x][it.coordinate.y] = PositionType.BLACK_STONE
        }
        assertThat(OmokRule(board.layout).checkThreeThree(11, 10)).isEqualTo(true)
    }

    @Test
    fun `checkThreeThree를 확인한다 - 세번째 경우`() {
        listOf(K3, K6, M4, N4).map {
            board.layout[it.coordinate.x][it.coordinate.y] = PositionType.BLACK_STONE
        }
        assertThat(OmokRule(board.layout).checkThreeThree(10, 3)).isEqualTo(true)
    }

    @Test
    fun `checkThreeThree를 확인한다 - 네번째 경우`() {
        listOf(B6, C5, E5, E6).map {
            board.layout[it.coordinate.x][it.coordinate.y] = PositionType.BLACK_STONE
        }
        assertThat(OmokRule(board.layout).checkThreeThree(4, 2)).isEqualTo(true)
    }

    @Test
    fun `countFourFour를 확인한다 - 첫번째 경우`() {
        listOf(C12, D12, G12, I12, J12).map {
            board.layout[it.coordinate.x][it.coordinate.y] = PositionType.BLACK_STONE
        }
        assertThat(OmokRule(board.layout).checkFourFour(5, 11)).isEqualTo(true)
    }

    @Test
    fun `countFourFour를 확인한다 - 두번째 경우`() {
        listOf(C10, C11, C12, E6, F5, G4).map {
            board.layout[it.coordinate.x][it.coordinate.y] = PositionType.BLACK_STONE
        }
        assertThat(OmokRule(board.layout).checkFourFour(2, 7)).isEqualTo(true)
    }

    @Test
    fun `countFourFour를 확인한다 - 세번째 경우`() {
        listOf(J6, J8, J9, J12).map {
            board.layout[it.coordinate.x][it.coordinate.y] = PositionType.BLACK_STONE
        }
        assertThat(OmokRule(board.layout).checkFourFour(9, 9)).isEqualTo(true)
    }

    @Test
    fun `checkMoreThanFive를 확인한다`() {
        listOf(A1, A2, A3, A4, A5).map {
            board.layout[it.coordinate.x][it.coordinate.y] = PositionType.BLACK_STONE
        }
        assertThat(OmokRule(board.layout).checkMoreThanFive(0, 5)).isEqualTo(true)
    }

    @Test
    fun `백돌이 오목인지 확인한다`() {
        listOf(A1, A2, A3, A4, A5).map {
            board.layout[it.coordinate.x][it.coordinate.y] = PositionType.WHITE_STONE
        }
        BoardView.printBoard(board)
        assertThat(
            OmokRule(board.layout, currentStone = PositionType.WHITE_STONE, otherStone = PositionType.BLACK_STONE).checkOmok(0, 4),
        ).isEqualTo(true)
    }

    @Test
    fun `흑돌이 오목인지 확인한다`() {
        listOf(A1, A2, A3, A4, A5).map {
            board.layout[it.coordinate.x][it.coordinate.y] = PositionType.BLACK_STONE
        }
        assertThat(OmokRule(board.layout).checkOmok(0, 4)).isEqualTo(true)
    }
}
