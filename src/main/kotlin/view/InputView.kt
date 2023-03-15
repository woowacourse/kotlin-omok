package view

import domain.Stone
import domain.XCoordinate
import domain.YCoordinate

object InputView {

    fun readPoint(): Stone {
//        val input = readln()
//        if (input[0] in 'A'..'O')
//            return Stone(XCoordinate.of(input[0]), YCoordinate.of(input[1].toString().toInt()))
        return Stone(XCoordinate.of('A'), YCoordinate.of(1))
    }

}