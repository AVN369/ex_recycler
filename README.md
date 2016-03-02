# ex_recycler
A simple example to use recyclerview to display a list of items.

1. Checks if data is present locally

2. If yes -> Loads the data

   Else fetches the data from the given URL (With a loader): 

    a. If successfull -> Saves the data locally (for the next fetch)
                         And loads it into the recyclerview at the same time
               
    b. Else -> Shows the failure message to the user
    
