class OutputView {

    fun printOmokBoardState(board: Board) {
        // 15부터 시작해서 반복문 돌기
        // Y좌표가 I와 같은 좌표를 찾고 좌표의 X좌표값이 1이면 3으로 , 2이면 6으로,
        // 3,6,9,...3 단위로 출력

        for (i in 0..14) {
            val frontNumber = BOARD[i].substring(0, 3)
            val line = BOARD[i].substring(3)
            val putBlackplacedStones =
                board.getStones().filter { it.point.y == 15 - i && it.color == Color.BLACK }
                    .map { stone -> (stone.point.x - 1) * 3 }
            val putWhiteplacedStones =
                board.getStones().filter { it.point.y == 15 - i && it.color == Color.WHITE }
                    .map { stone -> (stone.point.x - 1) * 3 }
            val builder = StringBuilder()
            line.forEachIndexed { index, c ->
                if (putWhiteplacedStones.contains(index)) {
                    builder.append('◎')
                } else if (putBlackplacedStones.contains(index)) {
                    builder.append('●')
                } else {
                    builder.append(c)
                }
            }
            print(frontNumber + builder.toString())
        }
        println(BOARD.last())
    }

    companion object {
        private const val BLACK_STONE = '●'
        private const val WHITE_STONE = '◎'

        private val BOARD = listOf(
            "15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐\n",
            "14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            "13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            "12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            "11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            "10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            " 9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            " 8 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            " 7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            " 6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            " 5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            " 4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            " 3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            " 2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            " 1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘\n",
            "   A  B  C  D  E  F  G  H  I  J  K  L  M  N  O"
        )
    }
}
