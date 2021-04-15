package com.applause.model.batch;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugDTO {

    private Long id;
    private Long deviceId;
    private Long testerId;

}
