package view

import domain.OmokBoard
import domain.State
import domain.Stone

interface OutputView {

    fun printStart()
    fun printDuplicate()
    fun printForbidden()
    fun printOmokState(omokBoard: OmokBoard, state: State, stone: Stone)
    fun printWinner(state: State)
}
