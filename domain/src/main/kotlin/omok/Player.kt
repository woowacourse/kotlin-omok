package omok

class Player(val hand: Hand = Hand()) {

    fun put(stone: Stone) {
        hand.add(stone)
    }
}
