package omok.domain

class Positions {
    val positions = HorizontalAxis.values().flatMap { horizontalAxis ->
        (Position.MIN_VERTICAL_AXIS..Position.MAX_VERTICAL_AXIS).map { verticalAxis ->
            Position(horizontalAxis, verticalAxis)
        }
    }
}
