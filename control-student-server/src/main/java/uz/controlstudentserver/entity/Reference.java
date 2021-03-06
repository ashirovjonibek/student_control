package uz.controlstudentserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reference {
    @Id
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Relations> relations;

    private String academicDegree;

    private String awards;

    @ManyToOne(fetch = FetchType.EAGER)
    private Party party;

    private String political_party;

    private String foreignLang;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<LaborActivity> laborActivities;

    @ManyToOne
    private Groups groups;
}
