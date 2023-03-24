package woowacourse.omok.view

class RoomEventObserver {
    private val roomEvent: MutableList<RoomEventObservable> = mutableListOf()

    fun subscribe(event: RoomEventObservable) {
        roomEvent.add(event)
    }

    fun unsubscribe(event: RoomEventObservable) {
        roomEvent.remove(event)
    }

    fun unsubscribeAll() {
        roomEvent.clear()
    }

    fun notifyEvent() {
        roomEvent.forEach {
            it.event()
        }
    }
}
