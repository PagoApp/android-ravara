package app.pago.recyclerview.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import app.pago.recyclerview.decorators.PagoRecyclerViewDecorator
import app.pago.recyclerview.lib.PagoRecyclerViewController

abstract class PagoRecyclerViewCell(
    private val inflate: (inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean) -> ViewBinding,
    val dataType: Class<*>,
    val dynamicDecorators: List<PagoRecyclerViewDecorator> = emptyList()
) {
    var controller: PagoRecyclerViewController? = null
    var viewType: Int? = null
    // This can be made to accept viewHolder instead of binding directly to allow for more customization
    abstract fun onBindViewHolder(
        binding: ViewBinding,
        item: PagoRecyclerAdapterBaseItem,
        decorators: List<PagoRecyclerViewDecorator>
    )

    fun createViewHolder(
        parent: ViewGroup,
    ): PagoRecyclerBaseViewHolder {
        val binding = inflate.invoke(LayoutInflater.from(parent.context), parent, false)
        return PagoRecyclerBaseViewHolder(binding, this)
    }

    open fun getDecorators(
        binding: ViewBinding,
        item: PagoRecyclerAdapterBaseItem
    ): List<PagoRecyclerViewDecorator> {
        return emptyList()
    }
}
