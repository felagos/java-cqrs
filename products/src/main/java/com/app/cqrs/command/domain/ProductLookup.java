package com.app.cqrs.command.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductLookup {
    private String id;
    private String title;
}
