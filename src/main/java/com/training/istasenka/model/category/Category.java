package com.training.istasenka.model.category;

import com.training.istasenka.validator.category.CategoryMatch;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "CATEGORIES")
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Category implements Serializable {
    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    private Long id;

    @NaturalId
    @CategoryMatch
    private String name;

    @Override
    public String toString() {
        return "Category{" +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category cachedCategory = (Category) o;
        return name.equals(cachedCategory.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
