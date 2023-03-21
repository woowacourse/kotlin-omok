package domain.state

import domain.stone.BlackStone
import domain.stone.Point
import domain.stone.Stones
import domain.stone.WhiteStone
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Test

class BlackTurnTest {

    @Test
    fun `오목이 만들어진 흑돌로 흑돌을 둘 수 있는 상태를 생성하면 에러가 발생한다`() {

//        12 ├──○──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//        11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//        10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         3 ├──┼──┼──┼──┼──┼──┼──○──┼──┼──┼──┼──┼──┼──┤
//         2 ├──●──●──●──●──●──┼──┼──┼──┼──┼──┼──○──┼──┤
//         1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┤
//            A  B  C  D  E  F  G  H  I  J  K  L  M  N  O

        assertThatIllegalArgumentException().isThrownBy {
            BlackTurn(
                Stones(
                    setOf(
                        BlackStone('B', 2),
                        BlackStone('C', 2),
                        BlackStone('D', 2),
                        BlackStone('E', 2),
                        BlackStone('F', 2),
                        WhiteStone('B', 12),
                        WhiteStone('J', 14),
                        WhiteStone('H', 3),
                        WhiteStone('H', 2)
                    )
                )
            )
        }.withMessage("게임이 진행되는 상태에서는 오목이 완성될 수 없습니다.")
    }

    @Test
    fun `오목이 만들어진 백돌로 흑돌을 둘 수 있는 상태를 생성하면 에러가 발생한다`() {

//         6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         3 ├──┼──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┤
//         2 ├──○──○──○──○──○──┼──●──┼──┼──┼──┼──┼──┼──┤
//         1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┤
//            A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
        assertThatIllegalArgumentException().isThrownBy {
            BlackTurn(
                Stones(
                    setOf(
                        BlackStone('B', 2),
                        BlackStone('J', 14),
                        BlackStone('H', 3),
                        BlackStone('H', 2),
                        WhiteStone('B', 2),
                        WhiteStone('C', 2),
                        WhiteStone('D', 2),
                        WhiteStone('E', 2),
                        WhiteStone('F', 2)
                    )
                )
            )
        }.withMessage("게임이 진행되는 상태에서는 오목이 완성될 수 없습니다.")
    }

    @Test
    fun `둘 수 있는 곳에 흑돌을 뒀을 때 게임이 안끝나면 백돌을 둘 수 있는 상태를 반환한다`() {
        val state: State = BlackTurn(Stones(setOf()))
        val actual = state.put(Point('A', 1))
        assertThat(actual).isInstanceOf(WhiteTurn::class.java)
    }

    @Test
    fun `흑돌을 뒀을 때 가로로 5목 이상이 되면 흑 승리 상태를 반환한다`() {

//         5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         3 ├──┼──┼──┼──┼──┼──┼──○──┼──┼──┼──┼──┼──┼──┤
//         2 ├──●──●──●──●──●──┼──┼──┼──┼──┼──┼──○──┼──┤
//         1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┤
//            A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
        val state: State = BlackTurn(
            Stones(
                setOf(
                    BlackStone('B', 2),
                    BlackStone('C', 2),
                    BlackStone('D', 2),
                    BlackStone('E', 2),
                    WhiteStone('B', 12),
                    WhiteStone('J', 14),
                    WhiteStone('H', 3),
                    WhiteStone('H', 2)
                )
            )
        )
        val actual = state.put(Point('F', 2))
        assertThat(actual).isInstanceOf(BlackWin::class.java)
    }

    @Test
    fun `흑돌을 뒀을 때 세로로 5목 이상이 되면 흑 승리 상태를 반환한다`() {

//         7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         6 ├──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         5 ├──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         4 ├──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         3 ├──●──┼──┼──┼──┼──┼──○──┼──┼──┼──┼──┼──┼──┤
//         2 ├──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──○──┼──┤
//         1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┤
//            A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
        val state: State = BlackTurn(
            Stones(
                setOf(
                    BlackStone('B', 2),
                    BlackStone('B', 3),
                    BlackStone('B', 4),
                    BlackStone('B', 5),
                    WhiteStone('B', 12),
                    WhiteStone('J', 14),
                    WhiteStone('H', 3),
                    WhiteStone('H', 2)
                )
            )
        )

        val actual = state.put(Point('B', 6))

        assertThat(actual).isInstanceOf(BlackWin::class.java)
    }

    @Test
    fun `흑돌을 뒀을 때 왼쪽위에서 시작하는 대각선이 5목 이상이 되면 흑 승리 상태를 반환한다`() {
//        15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
//        14 ├──┼──┼──┼──┼──┼──┼──┼──┼──○──┼──┼──┼──┼──┤
//        13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//        12 ├──○──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//        11 ├──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//        10 ├──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         9 ├──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         8 ├──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┤
//       ... ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──○──┼──┤
//         1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┤
//            A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
        val state: State = BlackTurn(
            Stones(
                setOf(
                    BlackStone('C', 12),
                    BlackStone('D', 11),
                    BlackStone('E', 10),
                    BlackStone('F', 9),
                    WhiteStone('B', 12),
                    WhiteStone('J', 14),
                    WhiteStone('H', 3),
                    WhiteStone('H', 2)
                )
            )
        )

        val actual = state.put(Point('G', 8))

        assertThat(actual).isInstanceOf(BlackWin::class.java)
    }

    @Test
    fun `흑돌을 뒀을 때 왼쪽 아래에서 시작하는 대각선이 5목 이상이 되면 흑 승리 상태를 반환한다`() {

//         9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         8 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         6 ├──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         5 ├──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         4 ├──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         3 ├──┼──●──┼──┼──┼──┼──○──┼──┼──┼──┼──┼──┼──┤
//         2 ├──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──○──┼──┤
//         1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┤
//            A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
        val state: State = BlackTurn(
            Stones(
                setOf(
                    BlackStone('B', 2),
                    BlackStone('C', 3),
                    BlackStone('D', 4),
                    BlackStone('E', 5),
                    WhiteStone('B', 12),
                    WhiteStone('J', 14),
                    WhiteStone('H', 3),
                    WhiteStone('H', 2)
                )
            )
        )

        val actual = state.put(Point('F', 6))

        assertThat(actual).isInstanceOf(BlackWin::class.java)
    }

    @Test
    fun `흑돌이 금수를 뒀을 때 에러가 발생한다`() {

//         8 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         5 ├──┼──┼──┼──●──●──●──●──┼──●──●──┼──┼──┼──┤
//         4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┤
//            A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
        assertThatIllegalArgumentException().isThrownBy {

            BlackTurn(
                Stones(
                    setOf(
                        BlackStone('E', 5),
                        BlackStone('F', 5),
                        BlackStone('G', 5),
                        BlackStone('H', 5),
                        BlackStone('J', 5),
                        BlackStone('K', 5),
                        WhiteStone('I', 2),
                        WhiteStone('L', 4),
                        WhiteStone('I', 10),
                        WhiteStone('F', 2),
                        WhiteStone('A', 2),
                        WhiteStone('B', 2)
                    )
                )
            ).put(Point('I', 5))
        }.withMessage("흑돌은 장목이면 안됩니다.")
    }
}
