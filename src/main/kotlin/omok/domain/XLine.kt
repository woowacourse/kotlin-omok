package omok.domain

class XLine(val value: Map<XCoordinate, StoneState>) {
    val keys = value.keys
    val values = value.values

    constructor () : this(XCoordinate.all().associateWith { StoneState.EMPTY })
}
