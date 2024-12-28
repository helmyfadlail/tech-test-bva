package helmyfadlail.technicaltestbva.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponse {

    private String id;

    private String name;

    private String position;

    private String superior;

    private String pictureUrl;

}