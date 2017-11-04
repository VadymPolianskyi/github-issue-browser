package com.piedpiper.gib.protocol.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public @Data
class LabelDao implements Serializable{
    private String url;
    private String name;
    private String color;
}

