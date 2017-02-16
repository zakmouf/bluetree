package com.bluetree.domain;

import com.bluetree.util.MessageUtil;

public class Profile extends Persistable implements Comparable<Profile> {

    private static final long serialVersionUID = 1L;

    private String name;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public int compareTo(Profile other) {
	return name.compareTo(other.getName());
    }

    @Override
    public String toString() {
	return MessageUtil.msg("[{0},{1}]", getId(), name);
    }

}
