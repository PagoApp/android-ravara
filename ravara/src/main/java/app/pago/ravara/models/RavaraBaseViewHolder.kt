package app.pago.ravara.models

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import app.pago.ravara.decorators.RavaraDecorator

class RavaraBaseViewHolder(
    val binding: ViewBinding,
    val cell: RavaraCell,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindViewHolder(
        item: RavaraBaseItem,
        decorators: List<RavaraDecorator>
    ) {
        cell.onBindViewHolder(binding, item, decorators)
    }
}