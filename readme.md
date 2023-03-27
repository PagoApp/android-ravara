# Råvara

Råvara is a lightweight library to augment the Android Recycler View default component for building dynamic ui lists. 

It draws inspiration from Epoxy by abstracting the list adapter letting the user focus on defining the data model and how it binds to UI elements

Råvara is Plug and play by not defining it's own UI widget. It relies directly on the default `RecyclerView` to host all its capabilities by defining a `RecyclerView.Adapter` that you can bind to your list. 

# Contents
[Components](#Components)

[Cell](#Cell)

[Controller](#Controller)

[Decorator](#Decorator)

[Putting it all together](#Putting-it-all-together)

[Sample App](#Sample-App)


# Components

Råvara defines a couple of components to build on top of:

- Cell: A cell is where you link the data model to the UI. Using androidX view binding you can define how the data model is bound to the UI elements.
A cell is tied to a specific data model class and a layout view binding.
- Controller: The controller is actually what links the data to the cells. 
It is used to:
  - populate the list with data
  - map the cells to view type ids and their respective data items
  - Resolve conflicts when two cells are bound to the same data model class
- Decorator: These can be used to define and apply common UI customizations to your cells UI
- A list adapter: This is used only to bind the Recycler view adapter to the controller

# Cell

First make sure AndroidX View Binding is enabled in your app's gradle config

Before defining a Cell we need to define a layout XML for it and a data model.
A data model will be the class that holds all the data necesarry to build the UI.

For this guide we'll create a basic cell that has:
- Some text
- A background
- Left and right optional icons
- A bottom divider
- A click event handler

So for this we'll use this layout XML named `basic_cell.xml`

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/simpleParentView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <TextView
        android:id="@+id/textView"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:drawablePadding="16dp"
        android:layout_marginRight="24dp"
        android:paddingTop="24dp"
        android:layout_marginLeft="24dp"
        android:paddingBottom="24dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
     />

    <View
        android:id="@+id/divider"
        android:layout_width="0dp"
        android:layout_height="1dp"
        android:layout_marginLeft="16dp"
        android:layout_marginRight="16dp"
        android:background="#acacac"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toBottomOf="@id/textView" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
Next we need to define a class that will hold all the properties we said we want to be able to customize

```
@Parcelize
data class SimpleCelltModel(
    override val id: String,
    var text: String,
    var iconResId: Int? = null,
    var iconResIdRight: Int? = null,
    var backgroundResId: Int? = null,
    val listener: () -> Unit,
    val hasBottomDivider: Boolean = true
) : RavaraBaseItem(id)
```
As you can see, our data model class inherits from `RavaraBaseItem`. The class is only hold an `id` property used internally. `RavaraBaseItem` also implements `Parcelable` so you can pass this objects around between fragments. **It uses @Parcelize for the Parcelable implementation**

Now that we have both our prerequisites we can define our cell.

``` 
class SimpleCell : RavaraCell(
    BasicCellBinding::inflate,
    SimpleCelltModel::class.java
) {
    override fun onBindViewHolder(
        binding: ViewBinding, item: RavaraBaseItem,
        decorators: List<RavaraDecorator>
    ) {
        binding as BasicCellBinding
        item as SimpleCelltModel
        binding.apply {
            textView.text = item.text
            textView.setCompoundDrawablesWithIntrinsicBounds(
                item.iconResId ?: 0,
                0,
                item.iconResIdRight ?: 0,
                0
            )

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

```

Let's go through it.

First, we define a class that inherits from `RavaraCell`. All cells should inherit from this class. 
The constructor for `RavaraCell` takes 2 parameters. First, the `inflate` method from the UI binding class of our layout. The second parameter is the data class that we'll bind to our cell.

`RavaraCell` defines the abstract method `onBindViewHolder`. This is where you will define how will your data bind to the UI. First thing in this method you can cast the first 2 params to their actuall types, then you ca start using the `binging` parameter to set the values from the data model in the UI. We'll be discussing the `decorators` parameter later in the guide.

Your exising view binding code for the `RecyclerView.ViewHolder` classes you have already defined should work almost immediatelly if you drop it in a `RavaraCell`.

## Note

`RavaraCell` is not the equivalent for `RecyclerView.ViewHolder`. A cell represents how you want the ViewHolders to be constructed. From a single `RavaraCell` object all the ViewHolder objects necesarry for your data models (of a particular type) will be constructed.

# Controller

`RavaraController` is the class that ties everything together. It links the `RecyclerView.Adapter` to the internal ViewHolders of the `RavaraCells` you define.

## Create a controller

Using the `RavaraControllerBuilder` class you can construct a controller object for your list. It defines three methods:

- `useCells`: Use this method to pass an `Array` of `RavaraCell` objects. This is where you specify what type of cells you'd want to use.
- `loadData`: Load a list of initial data for your data models. The list is of type `RavaraBaseItem` so you can mix and match your data models in a single call to `loadData.` It also gets a second `shouldClear = true` parameter that specifies whether or not the previous data models should be deleted before loading the new ones.
- `onConflict` : Provide conflic solvers for your cell. More on this later in the guide.
- `build`: Create the controller. It takes as argument the `recyclerAdapter` for your list. You should use an instance of `RavaraRecyclerViewAdapter` as your `recyclerAdapter`, but later in the guide you'll see how you can define your own


For our basic example we'd create the following controller:

```
// ...

private val listAdapter = RavaraRecyclerViewAdapter()
private lateinit var controller: RavaraController;

// ....

// We could also call `loadData(listOf(SimpleCellModel(...))) to load some initial data in our list

controller = RavaraControllerBuilder().useCells(
            arrayOf(
                SimpleCell()
            )
        ).build(listAdapter)
```



## Conflict Solvers

The reason you pass the class of your data model to the constructor of `RavaraCell` is that the `Class` object is used to identify what Cell to use to construct a ViewHolder based on the type of a data model object. 

Let's take the following example:

```
class CellA: RavaraCell(SomeBinding::inflate, DataModel::class.java){...}
class CellB: RavaraCell(SomeOtherBinding::inflate, DataModel::class.java){...}

data class DataModel(override val id: String, isA: Boolean)

```

Both cells bind to `DataModel` for their data model. But we want A cells only if `dataModel.isA == true`. If we were to create the controller like this:

```
controller = RavaraControllerBuilder().useCells(
            arrayOf(
                CellA(),
                CellB()
            )
        ).build(listAdapter)
  ```
  
  we would get an exception telling us there was no conflic solver defined.
  
To achieve the behaviour we want and to fix the error, we can define a conflict solver. This is a simple function that take as argument a `RavaraBaseItem` and returns a `Class<*>?` object. You should use this callback to try to cast the `RavaraBaseItem` in an item you want to solve the conflic for and return the class of the cell you want for that item, or null if that conflic solver can't handle that type of item. 

So to fix our issues we'd define the following conflict solver and pass it as argument to `onConflict` on the builder:

```
fun ABConflicSolver(item: RavaraBaseItem): Class<*>? {
  return (item as? DataModel)?.let {
    return if(it.isA) { CellA::class.java} else {CellB::class.java} 
   }
}

```

**TODO** Allow conflict solvers to return null


## How to use the controller

## Create a custom RecyclerAdapter


# Sample App

The reposiotry contains a sample app that defines a basic list that defines two cells:
- A simple cell with text
- A spacer cell to add a separator line between items

It also has buttons to:
- Add cells
- Remove cells
- Update cells

https://user-images.githubusercontent.com/38132701/227923547-e7f72a4f-6341-4d49-85c5-f1814b4af898.mov

