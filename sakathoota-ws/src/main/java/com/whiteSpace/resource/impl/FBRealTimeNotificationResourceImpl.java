/**
 * 
 */
package com.whiteSpace.resource.impl;

import javax.ws.rs.core.Response;

import com.whiteSpace.da.iface.TxtWebDAO;
import com.whiteSpace.domain.common.types.Notification;
import com.whiteSpace.domain.common.types.TxtWebPhone;
import com.whiteSpace.resource.iface.FBRealTimeNotificationResource;
import com.whiteSpace.resource.iface.UserResource;
import com.whiteSpace.resource.json.types.FBNotification;
import com.whiteSpace.ws.commons.FB2NativeMapper;
import com.whiteSpace.ws.commons.TxtWebPush;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shivakumar N
 *
 * @since Jan 9, 2013 10:11:33 PM
 */
public class FBRealTimeNotificationResourceImpl implements FBRealTimeNotificationResource {
	private static final Logger logger = Logger.getLogger(FBRealTimeNotificationResourceImpl.class);

	@Autowired
	private TxtWebDAO txtWebDAO;
    
    private FBDataAccess fbDataAccess;
    
	public void setFbDataAccess(FBDataAccess fbDataAccess) {
		this.fbDataAccess = fbDataAccess;
	}


	public Response postCallBackUrl(FBNotification notification) {
		TxtWebPush txtWebPush = new TxtWebPush();
		List<Notification> notifications = new ArrayList<Notification>();
		notifications.add(FB2NativeMapper.getFBNotification(notification));
		List<TxtWebPhone> phones = txtWebDAO.getActivePhones();
		for (TxtWebPhone txtWebPhone : phones) {
			txtWebPush.processRequest(notifications, txtWebPhone.getEncodedNumber());
		}
		return null;
	}

	
	public Response getCallBackUrl(String verifyToken, String challenge,
			String mode) {
		logger.info(verifyToken);
		//FIXME: add logic to verify the token
		return Response.ok(challenge).build();
	}

}
