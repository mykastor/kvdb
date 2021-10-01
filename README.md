# Overview

Key-value database, which supports basic(add, find, remove) database operations.

# Usage

```add key value``` - adds an element to the database, if it already exists, replaces it with a new value.  
```find key``` - find an element by key.  
```remove key``` - remove an element by key, if there is no such element nothing will happen.  
```clean!!``` - clean the database.
```changepath path``` - change the path to database(use ```changepath -d``` to set default path)
```path?``` - print the current path to database
Notice that ```key``` and ```value``` are strings without whitespaces.  