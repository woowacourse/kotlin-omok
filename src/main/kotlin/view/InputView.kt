package view

import domain.Stone

object InputView {
    fun readPosition(): Stone {
        val input = readln()
        return runCatching {
            Stone.create(input[0], input.substring(1).toInt())
        }.getOrElse { error ->
            println(error.message)
            readPosition()
        }
    }
}
