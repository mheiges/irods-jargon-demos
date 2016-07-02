/**
Authenticate and print path for the user's home collection.
Username and password can be passed as args[0] and args[1].
Username and password defaults to "rods" and "rods"
**/
package org.apidb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.IRODSFileSystem;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.io.IRODSFileFactory;
import org.irods.jargon.core.pub.io.IRODSFile;
import java.io.IOException;

public class AuthSimple {

  private static final Logger logger = LoggerFactory.getLogger(AuthSimple.class.getName());

  public static void main(String[] args) throws JargonException, IOException {

    String username    = args.length > 1 ? args[0] : "rods";
    String password    = args.length > 1 ? args[1] : "rods";
    String defaultZone = "tempZone";
    String home = "/" + defaultZone + "/home/" + username;
    String defaultResc = "demoResc";
    String host = "localhost";
    int port = 1247;

    IRODSAccount irodsAccount = IRODSAccount.instance(
      host, port,
      username, password,
      home,
      defaultZone,
      defaultResc
    );

    IRODSFileSystem irodsFileSystem = IRODSFileSystem.instance();

    IRODSAccessObjectFactory accessObjectFactory =
      irodsFileSystem.getIRODSAccessObjectFactory();

    IRODSFileFactory fileFactory =
      accessObjectFactory.getIRODSFileFactory(irodsAccount);

    IRODSFile irodsFile = fileFactory.instanceIRODSFile(home);

    logger.info("Home URI: " + irodsFile.toURI());
    logger.info("Home Path: " + irodsFile.getCanonicalPath());

  }

}
