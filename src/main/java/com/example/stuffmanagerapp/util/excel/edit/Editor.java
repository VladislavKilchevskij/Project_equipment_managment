package com.example.stuffmanagerapp.util.excel.edit;


import com.example.stuffmanagerapp.util.excel.entity.Document;

public interface Editor<T extends Document> {
    void updateDocument(T document);
    T getDocument();
}
