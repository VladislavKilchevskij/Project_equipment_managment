package com.example.stuffmanagerapp.util.excel.export;

import java.io.File;
import java.io.IOException;

public interface Exporter<T> {
    File export(Integer docNum, T dataContainer) throws IOException;
}
