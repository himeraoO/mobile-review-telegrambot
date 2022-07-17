package com.github.himeraoO.mrtb.mrtbclient.dto;

import lombok.Data;
import lombok.ToString;

/**
 * DTO, which represents post information.
 */
@Data
@ToString
public class PostInfo {
    private Integer id;
    private String title;
    private String description;
    private String link;
}
