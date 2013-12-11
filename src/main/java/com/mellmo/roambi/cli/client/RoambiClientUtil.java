/**
 * This sample code and information are provided "as is" without warranty of any kind, either expressed or implied, including
 * but not limited to the implied warranties of merchantability and/or fitness for a particular purpose.
 */
package com.mellmo.roambi.cli.client;

import com.mellmo.roambi.api.RoambiApiClient;
import com.mellmo.roambi.api.exceptions.ApiException;
import com.mellmo.roambi.api.exceptions.PortalContentNotFoundException;
import com.mellmo.roambi.api.model.ContentItem;
import com.mellmo.roambi.api.model.ContentResult;
import org.apache.log4j.Logger;
import org.apache.commons.codec.binary.Base64;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ryancamoras
 * Date: 6/12/13
 * Time: 3:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class RoambiClientUtil {

    private static Logger log = Logger.getLogger(RoambiClientUtil.class);

    //cheesy check to see if we are a UID or a path
    public static boolean isUIDValue(String value){
        if(value == null || value.startsWith("/") || value.length()!=24) {
            return false;
        }
        return true;

    }

    public static ContentItem getContentItem(String item, RoambiApiClient client) {
        if(isUIDValue(item)){
            return new ContentItem(item, "");
        }
        else {
            try {
                ContentResult result = getContentAndFolderByPath("rfs", item, client);
                if (result.getContent() != null) {
                    return result.getContent();
                }
            } catch (PortalContentNotFoundException e) {
                log.error("Portal not found.");

            } catch (FileNotFoundException e) {
                log.error("File not found.");
            }
            return null;
        }
    }

    private static ContentItem getContentItemFromPath(String path, RoambiApiClient client) {
        String uid="", name="";
        return new ContentItem(uid, name);
    }

    private static ContentResult getContentAndFolderByPath(String portalUid, String destinationPath, RoambiApiClient client) throws PortalContentNotFoundException, FileNotFoundException {
        if(destinationPath.startsWith("/")) {
            destinationPath = destinationPath.replaceFirst("/", "");
        }
        String[] pathElements = destinationPath.split("/");

        ContentResult result = new ContentResult();
        ContentItem folder = null;
        String fileName = pathElements[pathElements.length - 1];

        try {
            for (int i=0; i<pathElements.length - 1; i++) {
                List<ContentItem> contents = client.getPortalContents(portalUid, folder == null ? null : folder.getUid());

                folder = null;
                for (ContentItem item : contents) {
                    if (item.getName().equals(pathElements[i])) {
                        folder = item;
                    }
                }
                if (folder == null) {
                    throw new PortalContentNotFoundException("The specified folder '" + destinationPath + "' could not be found on portal '" + portalUid + "'");
                }
            }
        } catch (ApiException apiEx) {
            log.info("ApiException while finding folder: " + apiEx.getMessage());
        }

        if (pathElements.length > 1 && folder == null) {
            throw new PortalContentNotFoundException("The specified parent folder '" + destinationPath + "' could not be found on portal '" + portalUid + "'");
        }
        else {
            ContentItem file = null;
            log.debug("Finding file '" + fileName + "' " + (folder == null ? "" : " in folder '" + folder.getName() + "'"));
            try {
                List<ContentItem> contents = client.getPortalContents(portalUid, folder == null ? null : folder.getUid());
                for (ContentItem item : contents) {
                    if (item.getName().equals(fileName)) {
                        file = item;
                    }
                }
            } catch (ApiException apiEx) {
                log.info("ApiException while finding file '" + fileName + "': " + apiEx.getMessage());
            }

            result.setContent(file);
            result.setParentFolder(folder);
        }

        return result;
    }
}
