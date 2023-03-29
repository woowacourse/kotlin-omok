package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import view.OutputView
import java.util.stream.Stream

class RefereeTest {
    @Test
    fun `가로로 연속 5개 놓여있으면 승리다`() {
        // given
        val referee = Referee()
        val board =
            MutableList(OmokBoard.BOARD_SIZE) { MutableList(OmokBoard.BOARD_SIZE) { State.EMPTY } }
        board[1][1] = State.BLACK
        board[1][2] = State.BLACK
        board[1][3] = State.BLACK
        board[1][4] = State.BLACK
        board[1][5] = State.BLACK

        val omokBoard = OmokBoard(board)

        // when
        val result = referee.isWin(omokBoard, State.BLACK)

        // then
        assertThat(result).isTrue
    }

    @Test
    fun `세로로 연속 5개 놓여있으면 승리다`() {
        // given
        val referee = Referee()
        val board =
            MutableList(OmokBoard.BOARD_SIZE) { MutableList(OmokBoard.BOARD_SIZE) { State.EMPTY } }
        board[1][1] = State.BLACK
        board[2][1] = State.BLACK
        board[3][1] = State.BLACK
        board[4][1] = State.BLACK
        board[5][1] = State.BLACK

        val omokBoard = OmokBoard(board)

        // when
        val result = referee.isWin(omokBoard, State.BLACK)

        // then
        assertThat(result).isTrue
    }

    @Test
    fun `좌상단에서 우하단 대각선으로 5개 놓여있으면 승리다`() {
        // given
        val referee = Referee()
        val board =
            MutableList(OmokBoard.BOARD_SIZE) { MutableList(OmokBoard.BOARD_SIZE) { State.EMPTY } }
        board[1][1] = State.BLACK
        board[2][2] = State.BLACK
        board[3][3] = State.BLACK
        board[4][4] = State.BLACK
        board[5][5] = State.BLACK

        val omokBoard = OmokBoard(board)

        // when
        val result = referee.isWin(omokBoard, State.BLACK)

        // then
        assertThat(result).isTrue
    }

    @Test
    fun `우상단에서 좌하단 대각선으로 5개 놓여있으면 승리다`() {
        // given
        val referee = Referee()
        val board =
            MutableList(OmokBoard.BOARD_SIZE) { MutableList(OmokBoard.BOARD_SIZE) { State.EMPTY } }
        board[1][10] = State.BLACK
        board[2][9] = State.BLACK
        board[3][8] = State.BLACK
        board[4][7] = State.BLACK
        board[5][6] = State.BLACK

        val omokBoard = OmokBoard(board)

        // when
        val result = referee.isWin(omokBoard, State.BLACK)

        // then
        assertThat(result).isTrue
    }

    @Test
    fun `가로로 연속 4개 놓여있으면 승리가 아니다`() {
        // given
        val referee = Referee()
        val board =
            MutableList(OmokBoard.BOARD_SIZE) { MutableList(OmokBoard.BOARD_SIZE) { State.EMPTY } }
        board[1][2] = State.BLACK
        board[1][3] = State.BLACK
        board[1][4] = State.BLACK
        board[1][5] = State.BLACK

        val omokBoard = OmokBoard(board)
        // when
        val result = referee.isWin(omokBoard, State.BLACK)

        // then
        assertThat(result).isFalse
    }

    @Test
    fun `4*4 test1`() {
        // given
        val referee = Referee()
        val board =
            MutableList(OmokBoard.BOARD_SIZE) { MutableList(OmokBoard.BOARD_SIZE) { State.EMPTY } }
        board[3][2] = State.BLACK
        board[3][3] = State.BLACK
        board[3][6] = State.BLACK
        board[3][8] = State.BLACK
        board[3][9] = State.BLACK

        val myBoard = OmokBoard(board)

        // when
        val stone = Stone.create('F', 12)
        val actual = referee.isMovable(myBoard, stone, OmokRuleAdapter())

        OutputView().printOmokState(myBoard, State.BLACK, stone)

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `4*4 test2`() {
        // given
        val referee = Referee()
        val board =
            MutableList(OmokBoard.BOARD_SIZE) { MutableList(OmokBoard.BOARD_SIZE) { State.EMPTY } }
        board[3][9] = State.BLACK
        board[6][9] = State.BLACK
        board[7][9] = State.BLACK
        board[9][9] = State.BLACK

        val myBoard = OmokBoard(board)

        // when
        val stone = Stone.create('J', 10)
        val actual = referee.isMovable(myBoard, stone, OmokRuleAdapter())

        OutputView().printOmokState(myBoard, State.BLACK, stone)

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `4*4 test3`() {
        // given
        val referee = Referee()
        val board =
            MutableList(OmokBoard.BOARD_SIZE) { MutableList(OmokBoard.BOARD_SIZE) { State.EMPTY } }
        board[10][4] = State.BLACK
        board[10][5] = State.BLACK
        board[10][6] = State.BLACK
        board[9][7] = State.BLACK
        board[8][7] = State.BLACK
        board[7][7] = State.BLACK
        board[10][3] = State.WHITE
        board[6][7] = State.WHITE
        val myBoard = OmokBoard(board)

        // when
        val stone = Stone.create('H', 5)
        val actual = referee.isMovable(myBoard, stone, OmokRuleAdapter())

        OutputView().printOmokState(myBoard, State.BLACK, stone)

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `4*4 test4`() {
        // given
        val referee = Referee()
        val board =
            MutableList(OmokBoard.BOARD_SIZE) { MutableList(OmokBoard.BOARD_SIZE) { State.EMPTY } }
        board[7][7] = State.BLACK
        board[7][9] = State.BLACK
        board[7][10] = State.BLACK
        board[6][9] = State.BLACK
        board[8][7] = State.BLACK
        board[10][5] = State.BLACK
        val myBoard = OmokBoard(board)

        // when
        val stone = Stone.create('I', 8)
        val actual = referee.isMovable(myBoard, stone, OmokRuleAdapter())

        OutputView().printOmokState(myBoard, State.BLACK, stone)

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `jangmok test`() {
        // given
        val referee = Referee()
        val board =
            MutableList(OmokBoard.BOARD_SIZE) { MutableList(OmokBoard.BOARD_SIZE) { State.EMPTY } }
        board[0][2] = State.BLACK
        board[1][2] = State.BLACK
        board[3][2] = State.BLACK
        board[4][2] = State.BLACK
        board[5][2] = State.BLACK
        val myBoard = OmokBoard(board)

        // when
        val stone = Stone.create('C', 13)
        val actual = referee.isMovable(myBoard, stone, OmokRuleAdapter())

        OutputView().printOmokState(myBoard, State.BLACK, stone)

        // then
        assertThat(actual).isFalse
    }

    @ParameterizedTest
    @MethodSource("giveThreeAndThree")
    fun `3*3 묶음 테스트`(stones: MutableList<Pair<Int, Int>>, stone: Stone) {
        // given
        val referee = Referee()
        val board =
            MutableList(OmokBoard.BOARD_SIZE) { MutableList(OmokBoard.BOARD_SIZE) { State.EMPTY } }

        stones.forEach {
            board[it.first][it.second] = State.BLACK
        }
        val myBoard = OmokBoard(board)

        // when
        val actual = referee.isMovable(myBoard, stone, OmokRuleAdapter())

        OutputView().printOmokState(myBoard, State.BLACK, stone)

        // then
        assertThat(actual).isFalse
    }

    companion object {
        @JvmStatic
        fun giveThreeAndThree(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    mutableListOf(
                        1 to 3,
                        2 to 3,
                        3 to 2,
                        3 to 4,
                    ),
                    Stone.create('D', 12),
                ),
                Arguments.of(
                    mutableListOf(
                        3 to 12,
                        5 to 12,
                        6 to 9,
                        6 to 13
                    ),
                    Stone.create('L', 11),
                ),
                Arguments.of(
                    mutableListOf(
                        9 to 1,
                        10 to 2,
                        9 to 4,
                        10 to 4
                    ),
                    Stone.create('E', 3),
                ),
                Arguments.of(
                    mutableListOf(
                        12 to 10,
                        9 to 10,
                        11 to 12,
                        11 to 13
                    ),
                    Stone.create('K', 4),
                ),
            )
        }
    }
}
