/**
 * This sample code and information are provided "as is" without warranty of any kind, either expressed or implied, including
 * but not limited to the implied warranties of merchantability and/or fitness for a particular purpose.
 */
package com.mellmo.roambi.cli.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.mellmo.roambi.api.RoambiApiClient;
import com.mellmo.roambi.api.model.ContentItem;
import com.mellmo.roambi.cli.client.RoambiClientUtil;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: ryancamoras
 * Date: 7/30/13
 * Time: 3:52 PM
 * To change this template use File | Settings | File Templates.
 */

@Parameters(separators = "=", commandDescription = "Delete a folder in the Roambi Repository")
public class RemoveFolderCommand extends CommandBase {
    private static Logger logger = Logger.getLogger(CreateFolderCommand.class);
    private final String commandName = "rmdir";

    @Parameter(names="--folder", description="folder to be deleted")
    private String folder=null;

    @Override
    public String getName() {
        return commandName;
    }

    @Override
    public void execute(RoambiApiClient client) throws Exception {
        logger.info("executing: " + commandName);
        logger.info("folder: " + folder);

        client.currentUser();

        ContentItem item = RoambiClientUtil.getContentItem(folder, client);
        client.deleteFolder(item.getUid());
    }
}
