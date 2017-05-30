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
        produtos.addIdProperty();
        Property listaId = produtos.addLongProperty("listaId").getProperty();
        produtos.addIntProperty("cod_barras");
        produtos.addStringProperty("descricao");
        produtos.addIntProperty("quantidade");
        produtos.addDoubleProperty("valor");

        produtos.addToOne(lista, listaId);

        DaoGenerator dg = new DaoGenerator();

        dg.generateAll(schema, "./app/src/main/java");
    }
}