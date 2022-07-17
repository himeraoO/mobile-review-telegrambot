package com.github.himeraoO.mrtb.mrtbclient.dto;

import lombok.Data;
import lombok.ToString;

/**
 * DTO, which represents group information.
 */
@Data
@ToString
public class GroupInfo {
    private Integer id;
    private String title;
    private String url;
}
