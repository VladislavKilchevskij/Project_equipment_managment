package com.example.stuffmanagerapp.util.excel.entity.descendants;

import com.example.stuffmanagerapp.util.excel.entity.Document;
import lombok.Data;

@Data
public class WriteoffDocument extends Document {
    private String comissionMemberPosition1;
    private String comissionMemberName1;
    private String comissionMemberPosition2;
    private String comissionMemberName2;
}
