package com.ociapi.OCIAPI.repositories.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sender {

    private Long id;
    private int username;
    private String password;
    private String companyId;
    private String companyBu;
    private String softwareId;
    private String file;
}
