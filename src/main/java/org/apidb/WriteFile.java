/**
write to file (create if needed), close, reopen, append, close. Read file.
**/
package org.apidb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.connection.IRODSServerProperties;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.IRODSFileSystem;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.connection.AuthScheme;
import org.irods.jargon.core.pub.io.IRODSFileFactory;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.packinstr.DataObjInp.OpenFlags;
import org.irods.jargon.core.pub.io.IRODSFileInputStream;
import org.irods.jargon.core.pub.io.IRODSFileOutputStream;
import org.irods.jargon.core.utils.MiscIRODSUtils;
import java.io.IOException;

public class WriteFile {

  private static final Logger logger = LoggerFactory.getLogger(WriteFile.class.getName());
  private static final AuthScheme authenticationScheme = AuthScheme.STANDARD;

  public static void main(String[] args) throws JargonException, IOException, Exception {

    String host        = "ies.vm";
    int port           = 1247;
    String username    = args.length > 1 ? args[0] : "rods";
    String password    = args.length > 1 ? args[1] : "rods";
    String defaultZone = "tempZone";
    String home        = "/" + defaultZone + "/home/" + username;
    String defaultResc = "demoResc";

    String newFileName = "CD5F2563-B6A3-46E2-A923-BF7DCE90CD51";
    String string1 = "The lazy fox\n";
    String string2 = "jumps over the brown dog\n\n";

    IRODSFileSystem irodsFileSystem = IRODSFileSystem.instance(); 

    IRODSAccount irodsAccount = IRODSAccount.instance( 
      host, port,
      username, password,
      home, defaultZone, defaultResc,
      authenticationScheme
    ); 

    IRODSAccessObjectFactory accessObjectFactory = 
      irodsFileSystem.getIRODSAccessObjectFactory(); 

    String homePath = MiscIRODSUtils
				.buildIRODSUserHomeForAccountUsingDefaultScheme(irodsAccount);

    IRODSFileFactory fileFactory = accessObjectFactory.getIRODSFileFactory(irodsAccount);
    IRODSFile irodsFile = fileFactory.instanceIRODSFile(homePath + "/" + newFileName);

    IRODSFileOutputStream irodsFileOutputStream =
      fileFactory.instanceIRODSFileOutputStream(
        irodsFile, OpenFlags.READ_WRITE_CREATE_IF_NOT_EXISTS
    );

    irodsFileOutputStream.write(string1.getBytes());
    irodsFileOutputStream.close();

    irodsFileOutputStream = fileFactory.instanceIRODSFileOutputStream(
      irodsFile, OpenFlags.READ_WRITE
    );

    irodsFileOutputStream.write(string2.getBytes());
    irodsFileOutputStream.close();

    IRODSFileInputStream irodsFileInputStream =
      fileFactory.instanceIRODSFileInputStream(irodsFile);
    String contents = MiscIRODSUtils.convertStreamToString(irodsFileInputStream);
    irodsFileInputStream.close();

    logger.info(contents);

  }

}
