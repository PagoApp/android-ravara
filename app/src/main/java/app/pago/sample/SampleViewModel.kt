package app.pago.sample

import androidx.lifecycle.ViewModel
import app.pago.ravara.models.RavaraBaseItem
import app.pago.sample.cells.data.SimpleBottomSheetModel
import app.pago.sample.cells.data.SpacerCellModel
import kotlinx.coroutines.flow.MutableStateFlow

class SampleViewModel : ViewModel() {
    val data = MutableStateFlow<List<RavaraBaseItem>>(listOf())

    fun addItem(title: String) {
        data.value = data.value + SimpleBottomSheetModel(
            id = title,
            text = title,
            iconResId = 0,
            listener = {}
        )
    }

    fun removeItem(title: String) {
        data.value = data.value.filter { it.id != title }
    }

    fun addSpace() {
        data.value = data.value + SpacerCellModel(
            id = "space${data.value.size}",
            height = 16,
            backgroundResId = R.color.pago_divider
        )
    }

    fun removeLastSpace() {
        val lastSpace = data.value.lastOrNull { it is SpacerCellModel }
        if (lastSpace != null) {
            data.value = data.value.filter { it.id != lastSpace.id }
        }
    }

    fun updateItem(title: String) {
        val item = data.value.firstOrNull { it.id == title }
        if (item != null) {
            data.value = data.value.map {
                if (it.id == title) {
                    it as SimpleBottomSheetModel
                    it.copy(text = "Updated $title")
                } else {
                    it
                }
            }
        }
    }
}