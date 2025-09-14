package com.app.cqrs.shared.utils;

import java.util.UUID;

public class IdGenerator {

    public static String Uuid() {
        return UUID.randomUUID().toString();
    }

}
