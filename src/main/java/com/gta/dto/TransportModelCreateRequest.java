package com.gta.dto;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 이동수단 모델 등록 요청 DTO
 */
@Getter
@Setter
public class TransportModelCreateRequest {

    /** 제조사 */
    @NotBlank(message = "manufacturer는 필수입니다.")
    private String manufacturer;

    /** 모델명 */
    @NotBlank(message = "name은 필수입니다.")
    private String name;

    /** 이동수단 분류 */
    @NotBlank(message = "transportCategory는 필수입니다.")
    private String transportCategory;

    /** 개조 유형 */
    private String upgradeType;

    /** 개조 위치 */
    private String upgradeLocation;

    /** 랩타임 */
    private Integer lapTime;

    /** 최고속도 */
    private BigDecimal topSpeed;

    /** 가격 */
    private Long price;

    /** 출시일 */
    private Date releaseDate;

    /** 획득처 */
    private String source;

    /** 차량 무게 */
    private BigDecimal weight;

    /** 기어 수 */
    private Integer driveGears;

    /** 구동 방식 */
    private String driveTrain;

    /** 좌석 수 */
    private Integer seats;

    /** 성능 */
    private String performance;

    /** 특징 */
    private String features;
}
