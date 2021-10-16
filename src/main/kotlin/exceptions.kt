class DatabaseFilesDoesNotExist(pathToDataBase: String) :
        Exception("Database files does not exist: path is $pathToDataBase")

class DatabaseIsDamaged(str: String) :
        Exception("Database is damaged! : $str")

class WrongPassword(str: String) :
        Exception("$str is a wrong password")

class UnfinishedCheckingPassword() :
        Exception("Checking password is unfinished")

class NoSuchFile(filename: String) :
        Exception("No such a file : $filename")

class NoInput() :
        Exception("No user input")