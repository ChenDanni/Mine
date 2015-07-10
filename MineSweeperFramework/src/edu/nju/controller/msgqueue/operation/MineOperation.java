package edu.nju.controller.msgqueue.operation;

import java.io.Serializable;

public abstract class MineOperation implements Serializable{
	public abstract boolean setClientSourse(boolean ClientSourse);
	public abstract void execute();
	public abstract boolean getClientSourse();
}
