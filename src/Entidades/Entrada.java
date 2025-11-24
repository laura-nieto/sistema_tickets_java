package Entidades;

import java.sql.Timestamp;

public class Entrada {
    private Integer id;
    private String document;
    private String buyerName;
    private Espectaculo espectaculo;
    private Usuario vendedor;
    private Timestamp soldAt;


    public Entrada(String doc, String name, Espectaculo espectaculo, Usuario vendedor, Timestamp vendido) {
        this.document = doc;
        this.buyerName = name;
        this.espectaculo = espectaculo;
        this.vendedor = vendedor;
        this.soldAt = vendido;
    }

    public Entrada(Integer id, String doc, String name, Espectaculo espectaculo, Usuario vendedor, Timestamp vendido) {
        this.id = id;
        this.document = doc;
        this.buyerName = name;
        this.espectaculo = espectaculo;
        this.vendedor = vendedor;
        this.soldAt = vendido;
    }

    public Integer getId() {
        return id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Espectaculo getEspectaculo() {
        return espectaculo;
    }

    public void setEspectaculo(Espectaculo espectaculo) {
        this.espectaculo = espectaculo;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Timestamp getSoldAt() {
        return soldAt;
    }

    public void setSoldAt(Timestamp soldAt) {
        this.soldAt = soldAt;
    }
}
