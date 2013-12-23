roambi-script
=============

RoambiScript is a command line interface to the Roambi API Java Client.  It allows the user another way to invoke api calls to Roambi from the terminal.

Requirements:
* roambi-api-cli.jar
* Roambi account (credentials and account)
* Roambi API Client information
* Java

You may compile the roambi-api-cli.jar manually in bash:

    #!/bin/sh
    cd /tmp/
    git clone https://github.com/Roambi/roambi-java-sdk
    cd roambi-java-sdk/roambi-api-java-client/
    mvn package
    _api_client_jar=${PWD}/target/api-client-0.0.1-SNAPSHOT.jar
    mvn install:install-file -DgroupId=com.mellmo.roambi.api \
    -DartifactId=api-client -Dversion=0.0.1-SNAPSHOT -Dfile=${_api_client_jar} -Dpackaging=jar
    #_api_client_jar=/tmp/roambi-java-sdk/roambi-api-java-client/target/api-client-0.0.1-SNAPSHOT.jar
    cd ../../
    git clone https://github.com/Roambi/roambi-script
    cd roambi-script/
    mvn package



The client supports the following functions:

* create - upload a new source file to the library
* update - update an existing source file in the library
* refresh - create a new document (RBI) with a template and source file


Account Properties
You need to contain your account and client info in a (plain text) .properties file.  Here is an example of the contents:

* server.url=https://api.roambi.com
* consumer.key=3d7c8sdf1316a8cd5bac0d87
* consumer.secret=73252asdfbc9e3291066df92756a62bbb760238d6
* redirect.uri=roambi-api://client.roambi.com/authorize
* username=someone@yourdomain.com
* password=mypassword

You pass this to the jar using the -props option:

java -jar roambi-api-cli.jar -props=path/to/my/file.properties

Usage: <main class> [options] [command] [command options]
  Options:
        --help
       Shows help
       Default: false
    -props
       Property file location

  Commands:

    mkdir      Create a folder in the Roambi Repository
      Usage: mkdir [options]
        Options:
              --folder
             parent folder
              --title
             title of the new folder

    rmdir      Delete a folder in the Roambi Repository
      Usage: rmdir [options]
        Options:
              --folder
             folder to be deleted

    create      Upload and create a file in the Roambi Repository
       sage: create [options]
        Options:
              --file
             locale file you with to upload
              --folder
             remote folder destination
              --title
             title of the new file

    addPermission      add permissions to a file
      Usage: addPermission [options]
        Options:
              --groupIds
             group ids
              --target
             target file
              --userIds
             user ids

    refresh      Refresh a Roambi document
      Usage: refresh [options]
        Options:
              --folder
             remote folder destination
              --source
             remote source file
              --template
             template rbi
              --title
             title of the new document

    delete      Usage: delete [options]
        Options:
              --file
             file to be deleted

    removePermission      remove permissions to a file
      Usage: removePermission [options]
        Options:
              --groupIds
             group ids
              --target
             target file
              --userIds
             user ids

 update      Upload and update a file in the Roambi Repository
      Usage: update [options]
        Options:
              --file
             locale file you with to upload
              --target
             target file uid


Notes
All RFS Paths should be prepended with “/”.  This is a hack to differentiate them from UIDs.





