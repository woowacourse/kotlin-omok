package library.forbiddenfour

import domain.library.ForbiddenFour
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ForbiddenFourTest {
    @Test
    fun `(3,4)(3,5)(3,6)(4,3)(5,3)(6,3)가 흑돌인 조건일 때 (3,3)에 흑돌을 두면 44금수이다`() {
        val board = FourDummy.getForbiddenFourBoard1()
        val targetCoordinate = FourDummy.forbiddenFourCoordinate1

        assertThat(ForbiddenFour.isForbiddenFour(board, targetCoordinate)).isTrue
    }

    @Test
    fun `(3,4)(3,5)(3,6)(4,4)(5,5)(6,6)가 흑돌인 조건일 때 (3,3)에 흑돌을 두면 44금수이다`() {
        val board = FourDummy.getForbiddenFourBoard2()
        val targetCoordinate = FourDummy.forbiddenFourCoordinate2

        assertThat(ForbiddenFour.isForbiddenFour(board, targetCoordinate)).isTrue
    }
}
