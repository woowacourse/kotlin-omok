import controller.OmokController

fun main() {
    val t = Thread(OmokController())
    t.setUncaughtExceptionHandler { _, e ->
        println(e.message)
        println("예상치 못한 오류가 발생해서 프로그램을 종료합니다.")
    }
    t.start()
}
