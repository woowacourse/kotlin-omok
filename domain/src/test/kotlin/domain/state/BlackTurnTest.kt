package domain.state

import domain.rule.LongMokRule
import domain.rule.Referee
import domain.stone.BlackStone
import domain.stone.Stones
import domain.stone.WhiteStone
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Test

class BlackTurnTest {

    @Test
    fun `둘 수 있는 곳에 흑돌을 뒀을 때 게임이 안끝나면 백돌을 둘 수 있는 상태를 반환한다`() {
        //given
        val state: State = BlackTurn(Stones(setOf()))

        //when
        val actual = state.put(PointAdapter.create('A', 1))

        //then
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

        //given
        val stones = Stones(
            setOf(
                BlackStone(PointAdapter.create('B', 2)),
                BlackStone(PointAdapter.create('C', 2)),
                BlackStone(PointAdapter.create('D', 2)),
                BlackStone(PointAdapter.create('E', 2)),
                WhiteStone(PointAdapter.create('B', 12)),
                WhiteStone(PointAdapter.create('J', 14)),
                WhiteStone(PointAdapter.create('H', 3)),
                WhiteStone(PointAdapter.create('H', 2))
            )
        )
        val state: State = BlackTurn(stones)

        //when
        val actual = state.put(PointAdapter.create('F', 2))

        //then
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

        //given
        val stones = Stones(
            setOf(
                BlackStone(PointAdapter.create('B', 2)),
                BlackStone(PointAdapter.create('B', 3)),
                BlackStone(PointAdapter.create('B', 4)),
                BlackStone(PointAdapter.create('B', 5)),
                WhiteStone(PointAdapter.create('B', 12)),
                WhiteStone(PointAdapter.create('J', 14)),
                WhiteStone(PointAdapter.create('H', 3)),
                WhiteStone(PointAdapter.create('H', 2))
            )
        )
        val state: State = BlackTurn(stones)

        //when
        val actual = state.put(PointAdapter.create('B', 6))

        //then
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

        //given
        val stones = Stones(
            setOf(
                BlackStone(PointAdapter.create('B', 6)),
                BlackStone(PointAdapter.create('C', 5)),
                BlackStone(PointAdapter.create('D', 4)),
                BlackStone(PointAdapter.create('E', 3)),
                WhiteStone(PointAdapter.create('B', 12)),
                WhiteStone(PointAdapter.create('J', 14)),
                WhiteStone(PointAdapter.create('H', 3)),
                WhiteStone(PointAdapter.create('H', 2))
            )
        )
        val state: State = BlackTurn(stones)

        //when
        val actual = state.put(PointAdapter.create('F', 2))

        //then
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

        //given
        val stones = Stones(
            setOf(
                BlackStone(PointAdapter.create('B', 2)),
                BlackStone(PointAdapter.create('C', 3)),
                BlackStone(PointAdapter.create('D', 4)),
                BlackStone(PointAdapter.create('E', 5)),
                WhiteStone(PointAdapter.create('B', 12)),
                WhiteStone(PointAdapter.create('J', 14)),
                WhiteStone(PointAdapter.create('H', 3)),
                WhiteStone(PointAdapter.create('H', 2))
            )
        )
        val state: State = BlackTurn(stones)

        //when
        val actual = state.put(PointAdapter.create('F', 6))

        //then
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

        //given
        val stones = Stones(
            setOf(
                BlackStone(PointAdapter.create('E', 5)),
                BlackStone(PointAdapter.create('F', 5)),
                BlackStone(PointAdapter.create('G', 5)),
                BlackStone(PointAdapter.create('H', 5)),
                BlackStone(PointAdapter.create('J', 5)),
                BlackStone(PointAdapter.create('K', 5)),
                WhiteStone(PointAdapter.create('I', 2)),
                WhiteStone(PointAdapter.create('L', 4)),
                WhiteStone(PointAdapter.create('I', 10)),
                WhiteStone(PointAdapter.create('F', 2)),
                WhiteStone(PointAdapter.create('A', 2)),
                WhiteStone(PointAdapter.create('B', 2))
            )
        )
        val point = PointAdapter.create('I', 5)
        val referee = Referee(listOf(LongMokRule()))

        //then
        assertThatIllegalArgumentException().isThrownBy {
            BlackTurn(stones).put(point, referee)
        }.withMessage("흑돌은 장목이면 안됩니다.")
    }
}
