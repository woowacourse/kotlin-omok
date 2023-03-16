package domain

import domain.OmokGameDummy.getEmptyCoordinate
import domain.OmokGameDummy.getEmptyTestBoard
import domain.OmokGameDummy.getExactlyFiveBoard
import domain.OmokGameDummy.getExactlyFiveCoordinate
import domain.OmokGameDummy.getExceedFiveBoard
import domain.OmokGameDummy.getExceedFiveCoordinate
import domain.OmokGameDummy.getForbiddenFourBoard
import domain.OmokGameDummy.getForbiddenFourCoordinate
import domain.OmokGameDummy.getForbiddenThreeBoard
import domain.OmokGameDummy.getForbiddenThreeCoordinate
import domain.OmokGameDummy.getNotEmptyCoordinate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class OmokGameTest {
    @Test
    fun `putStone은 해당 Position이 비어있지 않으면 false를 반환한다`() {
        val inputPosition = getNotEmptyCoordinate()
        val board = getEmptyTestBoard()

        val omokGame = OmokGame(Board(board))
        val actual = omokGame.putStone(inputPosition)

        assertThat(actual).isFalse
    }

    @Test
    fun `putStone은 해당 Position이 비어있으면 true를 반환한다`() {
        val inputPosition = getEmptyCoordinate()
        val board = getEmptyTestBoard()

        val omokGame = OmokGame(Board(board))
        val actual = omokGame.putStone(inputPosition)

        assertThat(actual).isTrue
    }

    // 3   ? X   X
    // 4     X
    // 5
    // 6         X
    //     3 4 5 6
    @Test
    fun `putStone은 turn이 블랙이고 33금수인 경우 false를 반환한다`() {
        val inputPosition = getForbiddenThreeCoordinate()
        val board = getForbiddenThreeBoard()

        val omokGame = OmokGame(Board(board))
        val actual = omokGame.putStone(inputPosition)

        assertThat(actual).isFalse
    }

    // 3   ? X   X X
    // 4     X
    // 5
    // 6         X
    // 7           X
    //     3 4 5 6 7
    @Test
    fun `putStone은 turn이 블랙이고 44금수인 경우 fales를 반환한다`() {
        val inputPosition = getForbiddenFourCoordinate()
        val board = getForbiddenFourBoard()

        val omokGame = OmokGame(Board(board))
        val actual = omokGame.putStone(inputPosition)

        assertThat(actual).isFalse
    }

    // 3
    // 4 X X X ? X X
    //   3 4 5 6 7 8
    @CsvSource(value = ["BLACK,false", "WHITE,true"])
    @ParameterizedTest
    fun `putStone은 장목인 경우를 흑돌은 false, 백돌은 true를 반환한다`(coordinateState: CoordinateState, expected: Boolean) {
        val inputPosition = getExceedFiveCoordinate()
        val board = getExceedFiveBoard(coordinateState)

        val omokGame = OmokGame(Board(board))
        val actual = omokGame.putStone(inputPosition)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `changeTurn을 호출하면 turn이 바뀐다`() {
        val omokGame = OmokGame(board = Board(), initTurn = CoordinateState.BLACK)

        omokGame.changeTurn()

        assertThat(omokGame.turn).isEqualTo(CoordinateState.WHITE)
    }

    // 3
    // 4 X X X ? X
    //   3 4 5 6 7
    @CsvSource(value = ["BLACK", "WHITE"])
    @ParameterizedTest
    fun `checkWinner는 오목인 경우 무조건 true를 반환한다`(coordinateState: CoordinateState) {
        val inputPosition = getExactlyFiveCoordinate()
        val board = getExactlyFiveBoard(coordinateState)

        val omokGame = OmokGame(board = Board(board), initTurn = coordinateState)
        val actual = omokGame.checkWinner(inputPosition)

        assertThat(actual).isTrue
    }

    // 3
    // 4 X X X ? X X
    //   3 4 5 6 7 8
    @CsvSource(value = ["BLACK,false", "WHITE,true"])
    @ParameterizedTest
    fun `checkWinner는 장목인 경우를 흑돌은 false, 백돌은 true를 반환한다`(coordinateState: CoordinateState, expected: Boolean) {
        val inputPosition = getExceedFiveCoordinate()
        val board = getExceedFiveBoard(coordinateState)

        val omokGame = OmokGame(board = Board(board), initTurn = coordinateState)
        val actual = omokGame.checkWinner(inputPosition)

        assertThat(actual).isEqualTo(expected)
    }
}
