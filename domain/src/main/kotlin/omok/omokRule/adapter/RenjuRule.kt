package omok.omokRule.adapter

import omok.OmokBoard
import omok.OmokPoint

interface RenjuRule {
    fun isForbidden(omokBoard: OmokBoard, omokPoint: OmokPoint): Boolean
}
