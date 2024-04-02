package woowacourse.omok.view.output

interface OutputView {
    fun printTurn(
        stoneColor: String?,
        point: Pair<Int, Int>?,
    )

    fun printWinner(stoneColor: String?)

    fun printAlert(message: String)
}
