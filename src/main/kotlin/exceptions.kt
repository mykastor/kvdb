class DatabaseFilesDoesNotExist(pathToDataBase: String) :
        Exception("Database files does not exist: path is $pathToDataBase")

class DatabaseIsDamaged(str: String) :
        Exception("Database is damaged! : $str")