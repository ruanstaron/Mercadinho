package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Property;
import org.greenrobot.greendao.generator.Schema;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1,"com.example.ruanstaron.mercadinho.db");
        schema.enableKeepSectionsByDefault();

        Entity produto = schema.addEntity("Produto");
        produto.addLongProperty("cod_barras").primaryKey();
        produto.addStringProperty("descricao");
        produto.addStringProperty("descricao_usuario");
        produto.addBooleanProperty("recente");

        Entity lista = schema.addEntity("Lista");
        lista.addIdProperty();
        lista.addStringProperty("descricao");
        lista.addBooleanProperty("recente");

        Entity situacao = schema.addEntity("Situacao");
        situacao.addIdProperty();
        situacao.addStringProperty("descricao");

        Entity estado = schema.addEntity("Estado");
        estado.addIdProperty();
        estado.addStringProperty("descricao");
        estado.addStringProperty("sigla");

        Entity cidade = schema.addEntity("Cidade");
        cidade.addIdProperty();
        Property estadoId = cidade.addLongProperty("estadoId").getProperty();
        cidade.addStringProperty("descricao");
        cidade.addToOne(estado, estadoId);

        Entity mercado = schema.addEntity("Mercado");
        mercado.addLongProperty("cnpj").primaryKey();
        Property cidadeId = mercado.addLongProperty("cidadeId").getProperty();
        mercado.addStringProperty("descricao");
        mercado.addBooleanProperty("recente");
        mercado.addToOne(cidade, cidadeId);

        Entity lista_de_produtos = schema.addEntity("Lista_de_produtos");
        lista_de_produtos.addIdProperty();
        Property cod_barras = lista_de_produtos.addLongProperty("cod_barras").getProperty();
        Property listaId = lista_de_produtos.addLongProperty("listaId").getProperty();
        Property cnpj = lista_de_produtos.addLongProperty("cnpj").getProperty();
        Property situacaoId = lista_de_produtos.addLongProperty("situacaoId").getProperty();
        lista_de_produtos.addFloatProperty("quantidade");
        lista_de_produtos.addFloatProperty("valor");
        lista_de_produtos.addDateProperty("data_da_compra");
        lista_de_produtos.addBooleanProperty("recente");
        lista_de_produtos.addToMany(produto, cod_barras);
        lista_de_produtos.addToOne(lista, listaId);
        lista_de_produtos.addToMany(mercado, cnpj);
        lista_de_produtos.addToMany(situacao, situacaoId);

        DaoGenerator dg = new DaoGenerator();

        dg.generateAll(schema, "./app/src/main/java");
    }
}