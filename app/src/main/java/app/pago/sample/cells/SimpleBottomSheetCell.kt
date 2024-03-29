package app.pago.sample.cells

import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import app.pago.ravara.decorators.RavaraDecorator
import app.pago.ravara.models.RavaraBaseItem
import app.pago.ravara.models.RavaraCell
import app.pago.sample.databinding.ListItemSimpleBottomSheetBinding
import app.pago.sample.cells.data.SimpleBottomSheetModel

class SimpleBottomSheetCell : RavaraCell(
    ListItemSimpleBottomSheetBinding::inflate,
    SimpleBottomSheetModel::class.java
) {
    override fun onBindViewHolder(
        binding: ViewBinding, item: RavaraBaseItem,
        decorators: List<RavaraDecorator>
    ) {
        binding as ListItemSimpleBottomSheetBinding
        item as SimpleBottomSheetModel
        binding.apply {
            textView.text = item.text
            textView.setCompoundDrawablesWithIntrinsicBounds(
                item.iconResId,
                0,
                item.iconResIdRight ?: 0,
                0
            )

            if (item.iconTint != null) {
                textView.compoundDrawableTintList = ContextCompat.getColorStateList(
                    textView.context,
                    item.iconTint
                )
            }

            simpleParentView.setOnClickListener {
                item.listener.invoke()
            }

            item.backgroundResId?.let { resId ->
                textView.background = ContextCompat.getDrawable(textView.context, resId)
            }

            divider.visibility = if (item.hasBottomDivider) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

}