package omok.model

import omok.model.rule.ExceedFiveChecker
import omok.model.rule.FourFourChecker
import omok.model.rule.ThreeThreeChecker
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokRuleTest {
    @Test
    fun `3-3 케이스 1`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position(2, 11)) // C12 (2, 11)
        blackStone.putStone(Position(4, 11)) // E12 (4, 11)
        blackStone.putStone(Position(3, 12)) // D13 (3, 12)
        blackStone.putStone(Position(3, 13)) // D14 (3, 13)

        val actual = ThreeThreeChecker.checkThreeThree(3, 11) // D12 (3, 11)
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `3-3 케이스 2`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position(1, 5)) // B6 (1, 5)
        blackStone.putStone(Position(2, 4)) // C5 (2, 4)
        blackStone.putStone(Position(4, 4)) // E5 (4, 4)
        blackStone.putStone(Position(4, 5)) // E6 (4, 5)

        val actual = ThreeThreeChecker.checkThreeThree(4, 2) // E3 (4, 2)
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `3-3 케이스 3`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position(9, 8)) // J9 (9, 8)
        blackStone.putStone(Position(13, 8)) // N9 (13, 8)
        blackStone.putStone(Position(12, 9)) // M10 (12, 9)
        blackStone.putStone(Position(12, 11)) // M12 (12, 11)

        val actual = ThreeThreeChecker.checkThreeThree(11, 10) // L11 (11, 10)
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `3-3 케이스 4`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position(10, 2)) // K3 (10, 2)
        blackStone.putStone(Position(10, 5)) // K6 (10, 5)
        blackStone.putStone(Position(12, 3)) // M4 (12, 3)
        blackStone.putStone(Position(13, 3)) // N4 (13, 3)

        val actual = ThreeThreeChecker.checkThreeThree(10, 3) // K4 (10, 3)
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `4-4 케이스`() {
        val blackStone = BlackStone()
        val whiteStone = WhiteStone()
        blackStone.putStone(Position(2, 9))
        blackStone.putStone(Position(2, 10))
        blackStone.putStone(Position(2, 11))
        blackStone.putStone(Position(2, 13))
        blackStone.putStone(Position(2, 14))
        blackStone.putStone(Position(3, 11))
        blackStone.putStone(Position(6, 11))
        blackStone.putStone(Position(8, 11))
        blackStone.putStone(Position(9, 11))
        blackStone.putStone(Position(4, 4))
        blackStone.putStone(Position(4, 5))
        blackStone.putStone(Position(5, 4))
        blackStone.putStone(Position(6, 4))
        blackStone.putStone(Position(6, 3))
        blackStone.putStone(Position(7, 5))
        blackStone.putStone(Position(7, 6))
        blackStone.putStone(Position(7, 7))
        blackStone.putStone(Position(9, 7))
        blackStone.putStone(Position(10, 7))
        blackStone.putStone(Position(9, 8))
        blackStone.putStone(Position(9, 5))

        whiteStone.putStone(Position(3, 4))
        whiteStone.putStone(Position(7, 8))

        val actual1 = FourFourChecker.checkFourFour(2, 7)
        assertThat(actual1).isEqualTo(true)

        val actual2 = ExceedFiveChecker.checkMoreThanFive(2, 12)
        assertThat(actual2).isEqualTo(true)

        val actual3 = FourFourChecker.checkFourFour(5, 11)
        assertThat(actual3).isEqualTo(true)

        val actual4 = FourFourChecker.checkFourFour(7, 4)
        assertThat(actual4).isEqualTo(true)

        val actual5 = FourFourChecker.checkFourFour(8, 7)
        assertThat(actual5).isEqualTo(true)

        val actual6 = FourFourChecker.checkFourFour(9, 9)
        assertThat(actual6).isEqualTo(true)
    }
}
