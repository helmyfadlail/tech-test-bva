package helmyfadlail.technicaltestbva.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchMemberRequest {

    private String name;

    private String position;

    private String superior;

    @NotNull
    private Integer page;

    @NotNull
    private Integer size;

}
