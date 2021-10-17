# Overview

Key-value database, which supports basic(add, find, remove) database operations.

# Usage

```add KEY VAOUE``` - adds an element to the database, if it already exists, replaces it with a new value.  
```find KEY``` - find an element by key.  
```remove KEY``` - remove an element by key, if there is no such element nothing will happen.  
```clean!!``` - clean the database.  
```changepath PATH``` - change the path to database(use ```changepath -d``` to set default path)  
```path?``` - print the current path to database  
```set PASSWORD``` - set password" 
```all``` - print first 100 elemenets in database
```rmpwd``` - remove password
Notice that ```KEY```, ```VALUE```, ```PATH``` and ```PASSWORD``` are strings without whitespaces.  