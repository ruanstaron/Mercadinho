package tech.pucci.mercadinho.db;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "SITUACAO".
 */
@Entity
public class Situacao {

    @Id
    private Long id;
    private String descricao;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public Situacao() {
    }

    public Situacao(Long id) {
        this.id = id;
    }

    @Generated
    public Situacao(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}