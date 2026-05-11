package com.gta.dto;

import lombok.Data;

/**
 * 공통 코드 DTO
 */
@Data
public class CommonCodeDto {

    /** 공통코드 ID */
    private Long codeId;

    /** 그룹 코드 */
    private String groupCode;

    /** 코드 값 */
    private String codeValue;

    /** 코드명 */
    private String codeName;

    /** 정렬 순서 */
    private Integer sortOrder;

    /** 사용 여부 */
    private String useYn;
}
