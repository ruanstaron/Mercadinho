package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Property;
import org.greenrobot.greendao.generator.Schema;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1,"com.example.ruanstaron.mercadinho.db");

        Entity lista = schema.addEntity("Lista");
        lista.addIdProperty();
        lista.addStringProperty("descricao");

        Entity produtos = schema.addEntity("Produtos");
        produtos.addLongProperty("cod_barras");
        produtos.addStringProperty("descricao");
        produtos.addBooleanProperty("Manual");

        Entity compras = schema.addEntity("Compras");
        compras.addIdProperty();
        Property listaId = compras.addLongProperty("listaId").getProperty();
        Property cod_barras = compras.addLongProperty("cod_barras").getProperty();
        compras.addIntProperty("quantidade");
        compras.addDoubleProperty("valor");
        compras.addDoubleProperty("valorTotal");
        compras.addToMany(lista, listaId);
        compras.addToMany(produtos, cod_barras);

        DaoGenerator dg = new DaoGenerator();

        dg.generateAll(schema, "./app/src/main/java");
    }
}