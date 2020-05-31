package com.tacbin.town.common.entity;

import lombok.Getter;

@Getter
public enum Role {
    SUPER_ADMIN("至高无上的权力-皇帝", 1), ADMIN("一人之下-丞相", 10), SUPER_MANAGER("正一品-大学士", 20), MANAGER("从一品-都统", 30);

    private String description;
    private int queue;

    Role(String description, int queue) {
        this.description = description;
        this.queue = queue;
    }
}
