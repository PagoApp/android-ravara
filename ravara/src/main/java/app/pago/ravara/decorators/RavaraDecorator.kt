package app.pago.ravara.decorators

import android.view.View

abstract class RavaraDecorator(
    protected val targetView: View,
    val applyStrategy: DecoratorStrategy
//    private val binding: ViewBinding,
//    private val item: PagoRecyclerAdapterBaseItem
) {
    enum class DecoratorStrategy {
        PRE_BIND,
        POST_BIND,
        MANUAL
    }

    abstract fun apply()
}