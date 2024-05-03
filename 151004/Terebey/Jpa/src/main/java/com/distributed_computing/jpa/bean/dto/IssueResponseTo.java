package com.distributed_computing.jpa.bean.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueResponseTo {
    int id;
    int creatorId;
    String title;
    String content;
}