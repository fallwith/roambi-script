/**
 * This sample code and information are provided "as is" without warranty of any kind, either expressed or implied, including
 * but not limited to the implied warranties of merchantability and/or fitness for a particular purpose.
 */
package com.mellmo.roambi.cli.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.mellmo.roambi.api.RoambiApiClient;
import com.mellmo.roambi.api.model.ContentItem;
import com.mellmo.roambi.api.model.Group;
import com.mellmo.roambi.api.model.User;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ryancamoras
 * Date: 6/21/13
 * Time: 10:06 AM
 * To change this template use File | Settings | File Templates.
 */

@Parameters(separators="=", commandDescription="remove permissions to a file")
public class RemovePermissionCommand extends CommandBase {
    private static Logger logger = Logger.getLogger(RemovePermissionCommand.class);
    protected String name = "removePermission";

    @Parameter(names="--target", description="target file")
    private String remoteUid;

    @Parameter(names="--groupIds", variableArity = true, description="group ids")
    private List<String> groupIds;

    @Parameter(names="--userIds", variableArity = true, description = "user ids")
    private List<String> userIds;


    @Override
    public String getName() {
        return name;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void execute(RoambiApiClient client) throws Exception {
        client.currentUser();
        client.removePermission(new ContentItem(remoteUid, ""), groupIds, userIds);

    }
}