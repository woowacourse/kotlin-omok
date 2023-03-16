package view

import view.util.CoordinationWithIndex

class GuideView {

    fun requestCoordination(): Pair<Int, Int> {
        val coordination = readLine()

        return runCatching {
            val dot = checkValidation(coordination)
            CoordinationWithIndex.convertCoordination(dot)
        }
            .onFailure { println("ERROR") }
            .getOrElse { requestCoordination() }
    }

    private fun checkValidation(coordination: String?): String {
        requireNotNull(coordination)
        require(coordination.length in 2..3)

        return coordination
    }
}
