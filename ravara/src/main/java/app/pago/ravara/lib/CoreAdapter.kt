package app.pago.ravara.lib

interface CoreAdapter<T> {
    val list: MutableList<T>

    /**
     * Add an item to the existing list. If the item is null, nothing will be added.
     */
    fun addItem(item: T?)

    /**
     * Clear the list of all the included items
     */
    fun clearList()

    /**
     * Add a subList to the existing item list.
     * @param list the list that will be added
     * @param shouldClear if true, the initial list will be cleared before adding the new subList
     */
    fun addList(list: List<T?>, shouldClear: Boolean = false)

    /**
     * Updated the existing item. Used in cases where a param changes, but the main object remains the same
     */
    fun updateItem(item: T)

    /**
     * Remove an item from the list
     */
    fun removeItem(item: T)

    /**
     * Remove an item from the list
     * @param itemId the id of the item that will be removed
     */
    fun removeItem(itemId: String)


}