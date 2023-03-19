package view

import domain.OmokBoard
import domain.State
import domain.Stone

interface OutputViewInterface {
    fun printStart()
    fun printDuplicate()
    fun printForbidden()
    fun printOmokState(omokBoard: OmokBoard, nextState: State, stone: Stone)
    fun printWinner(state: State)
}
