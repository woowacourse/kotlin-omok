package domain.state

import domain.rule.LongMokRule
import domain.rule.RuleAdapter
import domain.stone.BlackStone
import domain.stone.Point
import domain.stone.Stones
import domain.stone.WhiteStone
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Test

class BlackTurnTest {

    @Test
    fun `둘 수 있는 곳에 흑돌을 뒀을 때 게임이 안끝나면 백돌을 둘 수 있는 상태를 반환한다`() {
        val state: State = BlackTurn(Stones(setOf()))
        val actual = state.put(Point.create('A', 1))
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
                    BlackStone(Point.create('B', 2)),
                    BlackStone(Point.create('C', 2)),
                    BlackStone(Point.create('D', 2)),
                    BlackStone(Point.create('E', 2)),
                    WhiteStone(Point.create('B', 12)),
                    WhiteStone(Point.create('J', 14)),
                    WhiteStone(Point.create('H', 3)),
                    WhiteStone(Point.create('H', 2))
                )
            )
        )
        val actual = state.put(Point.create('F', 2))
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
                    BlackStone(Point.create('B', 2)),
                    BlackStone(Point.create('B', 3)),
                    BlackStone(Point.create('B', 4)),
                    BlackStone(Point.create('B', 5)),
                    WhiteStone(Point.create('B', 12)),
                    WhiteStone(Point.create('J', 14)),
                    WhiteStone(Point.create('H', 3)),
                    WhiteStone(Point.create('H', 2))
                )
            )
        )

        val actual = state.put(Point.create('B', 6))

        assertThat(actual).isInstanceOf(BlackWin::class.java)
    }

    @Test
    fun `흑돌을 뒀을 때 왼쪽위에서 시작하는 대각선이 5목 이상이 되면 흑 승리 상태를 반환한다`() {
//        15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
//        14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//        13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//        12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//        11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         6 ├──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         5 ├──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         4 ├──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         3 ├──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         2 ├──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┤
//         1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┤
//            A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
        val state: State = BlackTurn(
            Stones(
                setOf(
                    BlackStone(Point.create('B', 6)),
                    BlackStone(Point.create('C', 5)),
                    BlackStone(Point.create('D', 4)),
                    BlackStone(Point.create('E', 3)),
                    WhiteStone(Point.create('B', 12)),
                    WhiteStone(Point.create('J', 14)),
                    WhiteStone(Point.create('H', 3)),
                    WhiteStone(Point.create('H', 2))
                )
            )
        )

        val actual = state.put(Point.create('F', 2))

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
                    BlackStone(Point.create('B', 2)),
                    BlackStone(Point.create('C', 3)),
                    BlackStone(Point.create('D', 4)),
                    BlackStone(Point.create('E', 5)),
                    WhiteStone(Point.create('B', 12)),
                    WhiteStone(Point.create('J', 14)),
                    WhiteStone(Point.create('H', 3)),
                    WhiteStone(Point.create('H', 2))
                )
            )
        )

        val actual = state.put(Point.create('F', 6))

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
                        BlackStone(Point.create('E', 5)),
                        BlackStone(Point.create('F', 5)),
                        BlackStone(Point.create('G', 5)),
                        BlackStone(Point.create('H', 5)),
                        BlackStone(Point.create('J', 5)),
                        BlackStone(Point.create('K', 5)),
                        WhiteStone(Point.create('I', 2)),
                        WhiteStone(Point.create('L', 4)),
                        WhiteStone(Point.create('I', 10)),
                        WhiteStone(Point.create('F', 2)),
                        WhiteStone(Point.create('A', 2)),
                        WhiteStone(Point.create('B', 2))
                    )
                )
            ).put(Point.create('I', 5), RuleAdapter(listOf(LongMokRule())))
        }.withMessage("흑돌은 장목이면 안됩니다.")
    }
}
