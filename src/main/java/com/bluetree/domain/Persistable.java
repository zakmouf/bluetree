package com.bluetree.domain;

import java.io.Serializable;

import com.bluetree.util.MessageUtil;

public class Persistable implements Serializable, Comparable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String toString() {
        return MessageUtil.msg("persistable[{0}]", id);
    }

    public int compareTo(Object obj) {
        return id.compareTo(((Persistable) obj).getId());
    }

    public int hashCode() {
        return id.hashCode();
    }

}
