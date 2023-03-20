package library.forbiddenthree

import domain.library.cldhfleks2.ForbiddenThree
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ForbiddenThreeTest {

    // 3   ? X X
    // 4   X
    // 5   X
    //     3 4 5
    @Test
    fun `(3,4)(3,5)(4,3)(5,3)가 흑돌인 조건일 때 (3,3)에 흑돌을 두면 33금수이다`() {
        val board = ThreeDummy.getForbiddenThreeBoard1()
        val targetCoordinate = ThreeDummy.getForbiddenThreeCoordinate1()

        assertThat(ForbiddenThree.isForbiddenThree(board, targetCoordinate)).isTrue
    }

    // 3   ? X X
    // 4     X
    // 5       X
    //     3 4 5
    @Test
    fun `(3,4)(3,5)(4,4)(5,5)가 흑돌인 조건일 때 (3,3)에 흑돌을 두면 33금수이다`() {
        val board = ThreeDummy.getForbiddenThreeBoard2()
        val targetCoordinate = ThreeDummy.getForbiddenThreeCoordinate2()

        assertThat(ForbiddenThree.isForbiddenThree(board, targetCoordinate)).isTrue
    }
}
