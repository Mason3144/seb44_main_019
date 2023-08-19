package com.shellwe.back.utils.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class MemberRemoveEvent {

    private final long Id;
    private final List<Long> shellIds;
}
