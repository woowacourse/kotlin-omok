package woowacourse.omokApp.omok.view

import woowacourse.omokApp.omok.model.board.ColumnNumber
import woowacourse.omokApp.omok.model.board.CoordsNumber
import woowacourse.omokApp.omok.model.board.Stone

object InputView {
    fun readPlayerMove(
        currentStone: Stone,
        string: String,
    ): Pair<CoordsNumber, Char> {
        println("${currentStone}의 차례입니다. ${if (string.isEmpty()) "" else "(마지막 돌의 위치: $string)"} 위치를 입력하세요: ")

        while (true) {
            val input = readln().trim().uppercase()
            if (input.length < 2 || input[0] !in 'A'..'O') {
                println("잘못된 입력입니다. A와 O 사이의 문자와 1에서 15 사이의 숫자를 입력해주세요.")
                continue
            }

            val columnLetter = input[0]
            val columnNumber = ColumnNumber.fromLetter(columnLetter)
            if (columnNumber == null) {
                println("잘못된 열 문자입니다. A와 O 사이의 문자를 입력해주세요.")
                continue
            }

            val rowNumberInput = input.substring(1).toIntOrNull()
            if (rowNumberInput == null || rowNumberInput !in 1..15) {
                println("잘못된 행 번호입니다. 1에서 15 사이의 숫자를 입력해주세요.")
                continue
            }

            val rowNumber = CoordsNumber(rowNumberInput - 1)
            return rowNumber to columnLetter
        }
    }
}
