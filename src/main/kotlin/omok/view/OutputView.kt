package omok.view

object OutputView {
    fun printGoBoard2(
        blackStones: Set<Pair<Char, Int>>,
        stone: List<String>,
    ) {
        val width = 15
        val height = 15
        val columns = "ABCDEFGHIJKLMNO"
        for (row in height downTo 1) {
            for (col in 1 until width + 1) {
                if (col == 1) print("%2d ".format(row))
                val stone: String =
                    when {
                        (columns[col - 1] to row) in blackStones -> "●"
                        (row == 15) -> {
                            when (col) {
                                1 -> "┌"
                                15 -> "┐"
                                else -> "┬"
                            }
                        }

                        (row == 1) -> {
                            when (col) {
                                1 -> "└"
                                15 -> "┘"
                                else -> "┴"
                            }
                        }

                        (col == 1) -> "├"
                        (col == 15) -> "┤"
                        else -> "┼"
                    }
                if (col != 15) {
                    print("$stone──")
                } else {
                    print(stone)
                }
            }
            println()
        }
        println("   A  B  C  D  E  F  G  H  I  J  K  L  M  N  O")
    }
}
