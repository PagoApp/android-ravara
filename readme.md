# Råvara

Råvara is a lightweight library to augment the Android Recycler View default component for building dynamic ui lists. 

It draws inspiration from Epoxy by abstracting the list adapter letting the user focus on defining the data model and how it binds to UI elements

# Contents
[Components](#Components)

# Componets

Råvara defines a couple of components to build on top of:

- Cell: A cell is where you link the data model to the UI. Using androidX view binding you can define how the data model is bound to the UI elements.
A cell is tied to a specific data model class and a layout view binding.
- Controller: The controller is actually what links the data to the cells. 
It is used to:
  - populate the list with data
  - map the cells to view type ids and their respective data items
  - Resolve conflicts when two cells are bound to the same data model class
- Decorator: A decorator is an optional component that can be used to add extra functionality to the list items. 
It can be used to extract common UI elements to be reused across cells.
- A list adapter: This is used only to bind the Recycler view adapter to the controller

