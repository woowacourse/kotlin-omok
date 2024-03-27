package omock

import omock.controller.OMokController
import omock.model.ruletype.FourToFourCount
import omock.model.ruletype.IsClearFourToFourCount
import omock.model.ruletype.IsReverseTwoAndThree
import omock.model.ruletype.ThreeToThreeCount

fun main() {
    OMokController(
        ruleTypes = listOf(
            ThreeToThreeCount,
            FourToFourCount,
            IsClearFourToFourCount,
            IsReverseTwoAndThree,
        )
    ).run()
}
