class DatabaseFilesDoesNotExist(pathToDataBase: String) :
        Exception("Database files does not exist: path is $pathToDataBase")

class DatabaseIsDamaged(str: String) :
        Exception("Database is damaged! : $str")

class WrongPasswords() :
        Exception("All passwords are wrong")

class UnfinishedCheckingPassword() :
        Exception("Checking password is unfinished")

class NoSuchFile(filename: String) :
        Exception("No such a file : $filename")

class NoInput() :
        Exception("No user input")

class NameIsTaken(filename: String) :
        Exception("Name which is needed by a program is taken : filename is $filename")