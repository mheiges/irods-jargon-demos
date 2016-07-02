package org.apidb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.connection.IRODSServerProperties;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.EnvironmentalInfoAO;
import org.irods.jargon.core.pub.IRODSFileSystem;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.connection.AuthScheme;
import org.irods.jargon.core.pub.io.IRODSFileFactory;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.utils.MiscIRODSUtils;
import org.apache.commons.lang3.StringUtils;

public class Mkdir {

  private static final Logger logger = LoggerFactory.getLogger(Mkdir.class.getName());
  private static final AuthScheme authenticationScheme = AuthScheme.STANDARD;

  public static void main(String[] args) throws JargonException {

    String host        = "ies.vm";
    int port           = 1247;
    String username    = args.length > 1 ? args[0] : "rods";
    String password    = args.length > 1 ? args[1] : "rods";
    String defaultZone = "tempZone";
    String home        = "/" + defaultZone + "/home/" + username;
    String defaultResc = "demoResc";

    IRODSFileSystem irodsFileSystem = IRODSFileSystem.instance(); 

    IRODSAccount irodsAccount = IRODSAccount.instance( 
      host, port,
      username, password,
      home, defaultZone, defaultResc,
      authenticationScheme
    ); 

    IRODSAccessObjectFactory accessObjectFactory = 
      irodsFileSystem.getIRODSAccessObjectFactory(); 
    EnvironmentalInfoAO environmentalInfoAO = 
      accessObjectFactory.getEnvironmentalInfoAO(irodsAccount); 
    
    IRODSServerProperties props = 
      environmentalInfoAO.getIRODSServerPropertiesFromIRODSServer(); 

    logger.info(props.toString());

    String homePath = MiscIRODSUtils
				.buildIRODSUserHomeForAccountUsingDefaultScheme(irodsAccount);

    IRODSFileFactory fileFactory = accessObjectFactory.getIRODSFileFactory(irodsAccount);
    IRODSFile newCollection = fileFactory.instanceIRODSFile(homePath + "/24A98522-A639-4123-9EB6-5F8CBE67B317");

    if (newCollection.exists())
      logger.info(newCollection.getAbsolutePath() + " exists.");
    else
      newCollection.mkdir();

    logger.info("File list: " + 
      StringUtils.join(fileFactory.instanceIRODSFile(homePath).list(), ", ")
    );

    if (newCollection.exists()) {
      logger.info("OK: " + newCollection.getAbsolutePath() + " was created.");
      if (newCollection.deleteWithForceOption()) {
        logger.info("OK: " + newCollection.getAbsolutePath() + " was deleted.");
      } else {
        logger.info("ERROR: " + newCollection.getAbsolutePath() + " failed to delete.");
      }
    } else {
      logger.info("ERROR: " + newCollection.getAbsolutePath() + " was not created.");
    }

  }

}
