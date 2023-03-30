package app.pago.ravara.decorators

import android.view.View
import app.pago.ravara.models.RavaraBaseItem

abstract class RavaraDecorator(
    protected open val targetView: View,
    val applyStrategy: DecoratorStrategy
//    private val binding: ViewBinding,
//    private val item: PagoRecyclerAdapterBaseItem
) {
    enum class DecoratorStrategy {
        PRE_BIND,
        POST_BIND,
        MANUAL
    }

    abstract fun apply(item: RavaraBaseItem)
}