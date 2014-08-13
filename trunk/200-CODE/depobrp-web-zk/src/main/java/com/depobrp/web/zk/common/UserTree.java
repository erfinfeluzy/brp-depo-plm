package com.depobrp.web.zk.common;

import org.zkoss.zul.AbstractTreeModel;

import com.depobrp.model.user.User;

public class UserTree extends AbstractTreeModel<User> {

	private User root;
	
	public UserTree(User root) {
		super(root);
		this.root = root;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8573207488535983143L;

	@Override
	public User getChild(User parent, int index) {
		return parent.getSubOrdinates().get(index);
	}

	@Override
	public int getChildCount(User parent) {
		return parent.getSubOrdinates().size();
	}

	@Override
	public boolean isLeaf(User user) {
		return user.getSubOrdinates().size() == 0;
	}

	public User getRoot() {
		return root;
	}

	public void setRoot(User root) {
		this.root = root;
	}

	
	
}
