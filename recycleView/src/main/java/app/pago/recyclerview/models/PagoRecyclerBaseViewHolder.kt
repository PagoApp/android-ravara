package app.pago.recyclerview.models

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import app.pago.recyclerview.decorators.PagoRecyclerViewDecorator

class PagoRecyclerBaseViewHolder(
    val binding: ViewBinding,
    val cell: PagoRecyclerViewCell,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindViewHolder(
        item: PagoRecyclerAdapterBaseItem,
        decorators: List<PagoRecyclerViewDecorator>
    ) {
        cell.onBindViewHolder(binding, item, decorators)
    }
}