package com.example.ruanstaron.mercadinho;

import android.util.Log;

import com.example.ruanstaron.mercadinho.db.DaoMaster;
import com.example.ruanstaron.mercadinho.db.Mercado;
import com.example.ruanstaron.mercadinho.db.Produto;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ruan on 03/11/2017.
 */

public class Cupom {

    DaoMaster master;

    public Cupom(DaoMaster master){
        this.master = master;
    }

    //Retorna um Array com todos os produtos
    public ArrayList<Produto> getProdutos(String qrcode) throws IOException {

        return null;
    }
    //Retorna o html contendo todos os dados da nota
    public Document getHtml(String qrcode) throws IOException {
        Connection.Response conexao = Jsoup.connect(qrcode).method(Connection.Method.GET).execute();
        Document doc = Jsoup.connect(qrcode).cookies(conexao.cookies()).get();
        String end_visualizar_abas = capturaHttp(doc);
        Document doc2 = Jsoup.connect(end_visualizar_abas).cookies(conexao.cookies()).get();
        return doc2;
    }

    //retorna o endereço do "Visualizar por abas" da nota
    public String capturaHttp(Document doc){
        Elements classe = doc.getElementsByClass("notprint");
        Element links = classe.select("a").first();
        return formataEndereco(links.attr("href"));
    }

    //Formata o endereço
    public String formataEndereco(String end){
        String end1 = end.replace("javascript:consultaPorAbas('//","");
        String end2 = end1.replace("')","");
        String end_formatado = "http://"+end2;
        return end_formatado;
    }

    public Mercado getMercado(String qrCode) throws IOException {
        Document doc = getHtml(qrCode);
        long cnpj = getCnpj(doc);
        String razaoSocial = getRazaoSocial(doc);
        long cidadeId = getCidadeId(doc);
        Mercado mercado = new Mercado(cnpj, cidadeId, razaoSocial, true);
        return mercado;
    }

    private long getCidadeId(Document doc) throws IOException {
        Element merc = doc.getElementById("Emitente");
        Element uf = merc.getElementsByTag("span").get(8);
        Element cidade = merc.getElementsByTag("span").get(6);
        String federacao = uf.text();
        String city = formataCidade(cidade.text());
        long cidadeId = new Banco(master.newSession()).getCidadeId(federacao, city);
        return cidadeId;
    }

    private String getRazaoSocial(Document doc) throws IOException {
        Element merc = doc.getElementById("NFe");
        Element razaoSocial = merc.getElementsByClass("col-2").select("span").first();
        return razaoSocial.text();
    }

    private long getCnpj(Document doc) throws IOException {
        Element merc = doc.getElementById("NFe");
        Element cnpj = merc.getElementsByClass("col-5").select("span").first();
        return formataCnpj(cnpj.text());
    }

    private long formataCnpj(String cnpj){
        String cnpj1 = cnpj.replace(".","");
        String cnpj2 = cnpj1.replace("/","");
        String cnpj3 = cnpj2.replace("-","");
        return Long.parseLong(cnpj3);
    }

    private String formataCidade(String cidade){
        String cidade1 = cidade.replaceAll(" - ","");
        String cidade2 = cidade1.replaceAll("[0-9]","");
        return cidade2;
    }
}
