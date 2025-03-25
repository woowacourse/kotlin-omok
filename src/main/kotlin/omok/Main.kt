package omok

import omok.di.DependencyInjector

fun main() {
    val controller = DependencyInjector().injectController()
    controller.run()
}
