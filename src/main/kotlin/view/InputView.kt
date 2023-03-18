package view

import domain.Stone

interface InputView {
    fun readPosition(): Stone
}
