package com.bluesky.visualprogramming.remote;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.MessageType;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core.VException;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.utils.Config;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class AbstractProtocolService {
	protected ProtocolType[] supportedTypes = null;

	/**
	 * store address of local object. one object can have many addresses; but
	 * must be unique.
	 * 
	 * client-only aliases does not need to be here.
	 * 
	 * key is address, value is object
	 */
	protected Map<String, _Object> addressObjectMap = new HashMap<String, _Object>();

	protected BidiMap addressConfigMap = new DualHashBidiMap();

	public AbstractProtocolService() {
		super();
	}

	public ProtocolType[] getSupportedTypes() {

		return this.supportedTypes;
	}

	public _Object getLocalObject(String address) {

		return (_Object) addressObjectMap.get(address);
	}

	public String getPrimaryAddress(_Object obj) {
		for (String key : addressObjectMap.keySet()) {
			_Object value = addressObjectMap.get(key);
			if (value == obj)
				return key;

		}
		return null;

	}

	public Config getConfig(String address) {

		return (Config) addressConfigMap.get(address);
	}

	/**
	 * a internal request has been sent to remote server, and now we get the
	 * response. it will send the response to local object.
	 * 
	 * @param orginalSender
	 * @param response
	 */
	public void replySuccessfulInternalRequest(Message requestMessage,
			_Object response) {

		VirtualMachine vm = VirtualMachine.getInstance();

		Message replyMsg = new Message(false, requestMessage.receiver,
				requestMessage.sender, "RE:" + requestMessage.getSubject(),
				response, ParameterStyle.ByName, null, MessageType.SyncReply);

		replyMsg.urgent = true;

		vm.getPostService().sendMessage(replyMsg);

	}

	public void replyFailureInternalRequest(Message requestMessage, Exception ex) {
		if (requestMessage.messageType.isReply()) {
			throw new RuntimeException("assert failed, it should be a request");
		}

		VirtualMachine vm = VirtualMachine.getInstance();
		VException vex = (VException) vm.getObjectRepository().createObject(
				ObjectType.EXCEPTION, ObjectScope.ExecutionContext);

		vex.setMessage(ex.getMessage());
		vex.addTrace(ex);

		Message replyMsg = new Message(false, requestMessage.receiver,
				requestMessage.sender, "RE:" + requestMessage.getSubject(),
				vex, ParameterStyle.ByName, null,
				requestMessage.messageType.getReplyType());

		replyMsg.urgent = true;

		vm.getPostService().sendMessage(replyMsg);
	}

}