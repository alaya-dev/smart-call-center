package tn.esprit.youssef_alaya_arctic10.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectsDTO {
    private long projectId;
    private  String projectName;
    private  String clientName;
}
