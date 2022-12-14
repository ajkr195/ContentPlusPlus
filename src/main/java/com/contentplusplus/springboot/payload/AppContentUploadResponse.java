package com.contentplusplus.springboot.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppContentUploadResponse {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

}
