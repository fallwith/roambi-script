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
import com.mellmo.roambi.api.model.RoambiFilePermission;
import com.mellmo.roambi.api.model.User;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ryancamoras
 * Date: 6/19/13
 * Time: 1:52 PM
 * To change this template use File | Settings | File Templates.
 */
@Parameters(separators="=", commandDescription="add permissions to a file")
public class AddPermissionCommand extends CommandBase {
    private static Logger logger = Logger.getLogger(AddPermissionCommand.class);
    protected String name = "addPermission";

    @Parameter(names="--target", description="target file")
    private String remoteUid;

    @Parameter(names="--groupIds", variableArity = true, description="group ids")
    private List<String> groupIds;

    @Parameter(names="--userIds", variableArity = true, description = "user ids")
    private List<String> userIds;

    //@Parameter(names="--access", description = "'view' or 'publish'")
    private String mode = "read";

    @Override
    public String getName() {
        return name;  //To change body of implemented methods use File | Settings | File Templates.
    }

    protected RoambiFilePermission getPermission(String p) {
        return "write".equals(mode)? RoambiFilePermission.WRITE:RoambiFilePermission.READ;
    }

    @Override
    public void execute(RoambiApiClient client) throws Exception {
        client.currentUser();
        client.addPermission(new ContentItem(remoteUid, ""), groupIds, userIds, getPermission(mode));

    }
}
