package library.exactlyfive

import domain.CoordinateState
import domain.library.self.ExactlyFive
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class ExactlyFiveTest {
    // 3
    // 4 X X X ? X
    //   3 4 5 6 7
    @CsvSource(value = ["BLACK", "WHITE"])
    @ParameterizedTest
    fun `(4,3)(4,4)(4,5)(4,7)에 같은 색의 돌이 놓여 있을 때 (4,6)에 같은 돌을 두면 오목이다`(coordinateState: CoordinateState) {
        val board = ExactlyFiveDummy.getExactlyFiveBoard(coordinateState)
        val targetCoordinate = ExactlyFiveDummy.getExactlyFiveCoordinate()

        assertThat(ExactlyFive.isExactlyFive(board, targetCoordinate, coordinateState)).isTrue
    }
}
