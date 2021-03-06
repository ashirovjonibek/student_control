package uz.controlstudentserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionDto {

    private Integer id;
    private String name;
    private Integer countryId;

    public RegionDto(String name, Integer countryId) {
        this.name = name;
        this.countryId = countryId;
    }
}
