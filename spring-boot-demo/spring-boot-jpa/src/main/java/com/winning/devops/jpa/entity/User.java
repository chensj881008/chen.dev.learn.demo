package com.winning.devops.jpa.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author chensj
 * @title
 * @project spring-boot-jpa
 * @package com.winning.devops.jpa.entity
 * @date: 2019-02-13 9:33
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @Column(nullable = false)
    @NonNull
    private Integer age;
}
