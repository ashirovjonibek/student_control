package uz.controlstudentserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictDto {
    private Integer id;

    private String name;

    private Integer regionId;

    public DistrictDto(String name, Integer regionId) {
        this.name = name;
        this.regionId = regionId;
    }
}
