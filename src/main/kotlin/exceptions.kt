class DatabaseFilesDoesNotExist(pathToDataBase: String) :
        Exception("Database files does not exist: path is $pathToDataBase")