package app.pago.ravara.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import app.pago.ravara.decorators.RavaraDecorator
import app.pago.ravara.lib.RavaraController

abstract class RavaraCell(
    private val inflate: (inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean) -> ViewBinding,
    val dataType: Class<*>,
) {
    var controller: RavaraController? = null
    var viewType: Int? = null
    // This can be made to accept viewHolder instead of binding directly to allow for more customization
    abstract fun onBindViewHolder(
        binding: ViewBinding,
        item: RavaraBaseItem,
        decorators: List<RavaraDecorator>
    )

    fun createViewHolder(
        parent: ViewGroup,
    ): RavaraBaseViewHolder {
        val binding = inflate.invoke(LayoutInflater.from(parent.context), parent, false)
        return RavaraBaseViewHolder(binding, this)
    }

    open fun getDecorators(
        binding: ViewBinding,
        item: RavaraBaseItem
    ): List<RavaraDecorator> {
        return emptyList()
    }
}
