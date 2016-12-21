package org.example.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.ebean.annotation.Where;

@Entity
@Table(name = "ownerTwo")
public class Owner2 {
    @Id
    private UUID id;
    private String text;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumns(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), value = {
	    @JoinColumn(insertable = false, updatable = false, name = "owner_id", referencedColumnName = "id"),
    })
    @Where(clause = "owner_type = 2")
    private List<Owned> owneds;

    public List<Owned> getOwneds() {
	if (owneds == null) {
	    owneds = new ArrayList<>();
	}
	return owneds;
    }

    public void setOwneds(final List<Owned> owneds) {
	this.owneds = owneds;
    }

    public UUID getId() {
	return id;
    }

    public void setId(final UUID id) {
	this.id = id;
    }

    public String getText() {
	return text;
    }

    public void setText(final String text) {
	this.text = text;
    }
}
