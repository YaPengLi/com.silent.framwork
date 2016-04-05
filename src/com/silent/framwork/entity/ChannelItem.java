package com.silent.framwork.entity;

import java.io.Serializable;

/** 
 * ITEM的对应可序化队列属性
 *  */
public class ChannelItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6465237897027410019L;
	/** 
	 * 栏目对应ID
	 *  */
	public Integer channelid;
	/** 
	 * 栏目对应NAME
	 *  */
	public String channelname;
	/** 
	 * 栏目在整体中的排序顺序  rank
	 *  */
	public Integer orderId;
	/** 
	 * 栏目是否选中
	 *  */
	public Integer selected;
	public Integer parentId;
	public ChannelItem() {
	}

	public ChannelItem(int id, String name, int orderId,int selected) {
		this.channelid = Integer.valueOf(id);
		this.channelname = name;
		this.orderId = Integer.valueOf(orderId);
		this.selected = Integer.valueOf(selected);
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public int getId() {
		return this.channelid.intValue();
	}

	public String getName() {
		return this.channelname;
	}

	public int getOrderId() {
		return this.orderId.intValue();
	}

	public Integer getSelected() {
		return this.selected;
	}

	public void setId(int paramInt) {
		this.channelid = Integer.valueOf(paramInt);
	}

	public void setName(String paramString) {
		this.channelname = paramString;
	}

	public void setOrderId(int paramInt) {
		this.orderId = Integer.valueOf(paramInt);
	}

	public void setSelected(Integer paramInteger) {
		this.selected = paramInteger;
	}

	public String toString() {
		return "ChannelItem [id=" + this.channelid + ", name=" + this.channelname
				+ ", selected=" + this.selected + "]";
	}
}