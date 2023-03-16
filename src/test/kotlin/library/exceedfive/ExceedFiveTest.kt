package library.exceedfive

import domain.CoordinateState
import domain.rule.ExceedFive
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class ExceedFiveTest {
    // 3
    // 4 X X X ? X X
    //   3 4 5 6 7 8
    @CsvSource(value = ["BLACK", "WHITE"])
    @ParameterizedTest
    fun `(4,3)(4,4)(4,5)(4,7)(4,8)에 같은 색의 돌이 놓여 있을 때 (4,6)에 같은 돌을 두면 장목이다`(coordinateState: CoordinateState) {
        val board = ExceedFiveDummy.getExceedFiveBoard(coordinateState)
        val targetCoordinate = ExceedFiveDummy.getExceedFiveCoordinate()

        assertThat(ExceedFive.isExceedFive(board, targetCoordinate, coordinateState)).isTrue
    }
}
