package omock.view

import omock.model.Stone

object OutputView {
    val boardTable: MutableList<MutableList<Char>> =
        mutableListOf(
            mutableListOf('┌', '┬', '┬', '┬', '┬', '┬', '┬', '┬', '┬', '┬', '┬', '┬', '┬', '┬', '┐'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('└', '┴', '┴', '┴', '┴', '┴', '┴', '┴', '┴', '┴', '┴', '┴', '┴', '┴', '┘'),
        )
    val boardForm =
        listOf(
            " 15 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            " 14 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            " 13 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            " 12 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            " 11 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            " 10 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            "  9 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            "  8 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            "  7 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            "  6 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            "  5 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            "  4 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            "  3 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            "  2 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            "  1 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            "    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O",
        )

    fun outputBoardForm(){
        boardForm.forEachIndexed { index, s ->
            if (index == boardForm.size - 1) {
                println(s)
            } else {
                println(s.format(*boardTable[index].toTypedArray()))
            }
        }
    }
    fun outputUserLocation() {
        print("위치를 입력하세요:")
    }

    fun outputSuccessOMock() {
        println("오목~!~!~!~!~!~")
    }

    fun outputUserTurn(name: String) {
        print("${name}의 차례입니다.")
    }

    private fun outputPrintLine() {
        println()
    }

    fun outputLastStone(lastStone: Stone?) {
        lastStone?.let { stone ->
            println(stone.toString())
        } ?: outputPrintLine()
    }

    fun outputGameStart() {
        println("오목 게임을 시작합니다.")
    }

    fun outputFailureMessage(throwable: Throwable){
        println(throwable.message)
    }
}
