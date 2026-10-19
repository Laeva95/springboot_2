package net.skhu.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professorId")
    private Professor professor;

    private Integer year;

    @Column(length = 45)
    private String semester;

    @Column(length = 45)
    private String room;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "lecture")
    private List<Sugang> sugangList = new ArrayList<>();
}