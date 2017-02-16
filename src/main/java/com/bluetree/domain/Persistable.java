package com.bluetree.domain;

import java.io.Serializable;

import com.bluetree.util.MessageUtil;

public abstract class Persistable implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    @Override
    public String toString() {
	return MessageUtil.msg("persistable[{0}]", id);
    }

}
