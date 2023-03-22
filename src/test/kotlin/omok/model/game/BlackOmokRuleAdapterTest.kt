package omok.model.game

import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class BlackOmokRuleAdapterTest {
    @ParameterizedTest
    @CsvSource("H8, H9, H11, H12, H10", "F10, G10, I10, J10, H10", "F12, G11, I9, J8, H10")
    fun `5개의 같은 색의 돌이 어느 방향이던 연이어 놓이면 승리이다`(mark1: String, mark2: String, mark3: String, mark4: String, mark5: String) {
        val board = Board().apply {
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark(mark1)))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark(mark2)))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark(mark3)))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark(mark4)))
        }
        val goStone = GoStone(GoStoneColor.BLACK, Coordinate.createWithMark(mark5))
        val rule = BlackOmokRuleAdapter(board)

        Assertions.assertThat(rule.checkWin(goStone.coordinate)).isEqualTo(PlacementState.WIN)
    }

    @Test
    fun `연달아 놓여져 있는 5개의 돌들의 색이 서로 다르면 승리가 아니다`() {
        val board = Board().apply {
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H8")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H9")))
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.createWithMark("H11")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H12")))
        }
        val goStone = GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H10"))
        val rule = BlackOmokRuleAdapter(board)

        Assertions.assertThat(rule.checkWin(goStone.coordinate)).isEqualTo(PlacementState.STAY)
    }

    @Test
    fun `놓여져 있는 돌들이 5개보다 적으면 승리가 아니다`() {
        val board = Board().apply {
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H8")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H9")))
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.createWithMark("H11")))
        }
        val goStone = GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H10"))
        val rule = BlackOmokRuleAdapter(board)

        Assertions.assertThat(rule.checkWin(goStone.coordinate)).isEqualTo(PlacementState.STAY)
    }

    @Test
    fun `6개의 같은 색의 돌이 가로로 연이어 놓이면 승리가 아니다`() {
        val board = Board().apply {
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H8")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H9")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H11")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H12")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H13")))
        }
        val goStone = GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H10"))
        val rule = BlackOmokRuleAdapter(board)

        Assertions.assertThat(rule.checkWin(goStone.coordinate)).isEqualTo(PlacementState.STAY)
    }

    @Test
    fun `흑돌이 열린 33이면 그에맞는 상수가 반환된다`() {
        val board = Board().apply {
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("C12")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("E12")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("D13")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("D14")))
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.createWithMark("A1")))
        }
        val rule = BlackOmokRuleAdapter(board)

        Assertions.assertThat(rule.checkViolation(Coordinate.createWithMark("D12")))
            .isEqualTo(PlacementState.OPEN_DOUBLE_THREE)
    }

    @Test
    fun `흑돌이 44이면 그에 맞는 상수가 반환된다`() {
        val board = Board().apply {
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("E5")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("F5")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("G5")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H6")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H7")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H8")))
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.createWithMark("D5")))
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.createWithMark("H9")))
        }
        val goStone = GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H5"))
        val rule = BlackOmokRuleAdapter(board)

        Assertions.assertThat(rule.checkViolation(goStone.coordinate)).isEqualTo(PlacementState.OPEN_DOUBLE_FOUR)
    }

    @Test
    fun `흑돌이 장목이면 그에 맞는 상수가 반환된다`() {
        val board = Board().apply {
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("C10")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("C11")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("C12")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("C14")))
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("C15")))
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.createWithMark("H8")))
        }
        val goStone = GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("C13"))
        val rule = BlackOmokRuleAdapter(board)

        Assertions.assertThat(rule.checkViolation(goStone.coordinate)).isEqualTo(PlacementState.LONG_LINE)
    }
}
