

## Compile

    mvn install -Dmaven.test.skip=true


## Run

    java -cp .:target:target/irods-jargon-demos-1.0-SNAPSHOT-jar-with-dependencies.jar org.apidb.AuthSimple

Where `org.apidb.AuthSimple` can be substituted with other main() classes.

### org.apidb.AuthSimple [username password]

Authenticate with standard authentication scheme and print path for the
user's home collection.

Username and password can be passed as args[0] and args[1].
Username and password defaults to "rods" and "rods"

### org.apidb.Mkdir [username password]

Example working with
[`IRODSFile`](https://github.com/DICE-UNC/jargon/blob/master/jargon-core/src/main/java/org/irods/jargon/core/pub/io/IRODSFile.java)
and `IRODSServerProperties`.



Make a collection (directory) in user's home. Print files in user's
home. Delete the collection just made.

### org.apidb.WriteFile [username password]

write to file (create if needed), close, reopen, append, close. Read file.

## Logging

Edit `target/log4j.properties`

