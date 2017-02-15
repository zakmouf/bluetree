package com.bluetree.domain;

import com.bluetree.util.MessageUtil;

public class Profile extends Persistable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return MessageUtil.msg("[{0},{1}]", getId(), name);
    }

    public int compareTo(Object other) {
        return name.compareTo(((Profile) other).getName());
    }

    public int hashCode() {
        return name.hashCode();
    }

}
