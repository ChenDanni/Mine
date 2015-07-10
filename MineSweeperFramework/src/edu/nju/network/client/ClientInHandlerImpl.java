package edu.nju.network.client;

import java.util.Observable;

import edu.nju.network.TransformObject;

/**从服务器端
 * 获得传来的TransformObject，将其中的UpdateMessage传给对应的Proxy.
 */
public class ClientInHandlerImpl extends Observable implements ClientInHandler{

	@Override
	public void inputHandle(Object data) {
		// TODO Auto-generated method stub
		//understand data and handle them;
		
		TransformObject obj = (TransformObject) data;
			
		this.setChanged();
		this.notifyObservers(obj);
		
//		System.out.println("here!!!!!!!!!!!!!!!!!!!!!!!!!!!!!2");
		
	}

}
