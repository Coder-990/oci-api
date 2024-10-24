package com.ociapi.OCIAPI.repositories;

import com.ociapi.OCIAPI.repositories.model.Sender;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@Data
public class SenderRepository {

    private List<Sender> senders;

    private SenderRepository() {
        senders = new ArrayList<>();
        senders.add(firstSender());
        senders.add(secondSender());
        senders.add(thirdSender());
    }

    private Sender firstSender() {
        return Sender.builder()
                .id(1L)
                .username(123)
                .password("pass")
                .companyId("CompanyId")
                .companyBu("companyBu")
                .softwareId("SoftwareId")
                .file("file1")
//                .electronicId(321)
//                .statusId(213)
//                .from(LocalDateTime.now())
//                .to(LocalDateTime.now().plusDays(7))
                .build();
    }

    private Sender secondSender() {
        return Sender.builder()
                .id(1L)
                .username(456)
                .password("pass2")
                .companyId("CompanyId2")
                .companyBu("companyBu2")
                .softwareId("SoftwareId2")
                .file("file2")
//                .electronicId(556)
//                .statusId(546)
//                .from(LocalDateTime.now())
//                .to(LocalDateTime.now().plusDays(7))
                .build();
    }

    private Sender thirdSender() {
        return Sender.builder()
                .id(1L)
                .username(789)
                .password("pass3")
                .companyId("CompanyId3")
                .companyBu("companyBu3")
                .softwareId("SoftwareId3")
                .file("file3")
//                .electronicId(879)
//                .statusId(987)
//                .from(LocalDateTime.now())
//                .to(LocalDateTime.now().plusDays(7))
                .build();
    }
}
