package com.example.stuffmanagerapp.util.excel.entity;

import lombok.Data;

@Data
public abstract class Document {
    private String organisationName;
    private Integer unp;
    private String chiefPosition;
    private String chiefName;
    private String departmentName;
}
