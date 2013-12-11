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

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: ryancamoras
 * Date: 7/30/13
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */

@Parameters(separators = "=", commandDescription = "Create a folder in the Roambi Repository")
public class CreateFolderCommand extends CommandBase {
    private static Logger logger = Logger.getLogger(CreateFolderCommand.class);
    private final String commandName = "mkdir";

    @Parameter(names="--folder", description="parent folder")
    private String parentFolder=null;

    @Parameter(names="--title", description="title of the new folder")
    private String title;

    @Override
    public String getName() {
        return commandName;
    }

    @Override
    public void execute(RoambiApiClient client) throws Exception {
        logger.info("executing: " + commandName);
        logger.info("folder: " + parentFolder);
        logger.info("title: " + title);

        client.currentUser();
        client.createFolder(parentFolder==null?null:RoambiClientUtil.getContentItem(parentFolder, client), title);
    }
}
