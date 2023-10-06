package com.shellwe.back.entity.type;

import com.shellwe.back.entity.ShellCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "CATEGORY")
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "SHELL_CATEGORY", unique = true)
    private ShellCategory shellCategory;

    public void setShellCategory(ShellCategory shellCategory) {
        this.shellCategory = shellCategory;
    }
}
