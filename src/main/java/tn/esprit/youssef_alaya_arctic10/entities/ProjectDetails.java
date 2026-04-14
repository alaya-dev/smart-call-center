package tn.esprit.youssef_alaya_arctic10.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ProjectDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "project_details_gen")
    @TableGenerator(
            name = "project_details_gen",
            table = "id_generator",
            pkColumnName = "gen_name",
            valueColumnName = "gen_value",
            pkColumnValue = "project_details_id",
            allocationSize = 1
    )
    Long detailsId;

    Double budget;

    String client;

    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;

}

