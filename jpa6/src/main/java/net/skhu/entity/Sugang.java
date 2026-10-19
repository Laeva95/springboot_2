package net.skhu.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Sugang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lectureId")
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId")
    private Student student;

    private Boolean repeated;

    private Boolean cancel;

    @Column(length = 45)
    private String grade;
}