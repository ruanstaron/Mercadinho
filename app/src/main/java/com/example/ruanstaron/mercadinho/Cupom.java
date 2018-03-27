package com.example.ruanstaron.mercadinho;

import android.util.Log;

import com.example.ruanstaron.mercadinho.db.DaoMaster;
import com.example.ruanstaron.mercadinho.db.Lista_de_produtos;
import com.example.ruanstaron.mercadinho.db.Mercado;
import com.example.ruanstaron.mercadinho.db.Produto;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruan on 03/11/2017.
 */

public class Cupom {

    DaoMaster master;

    public Cupom(DaoMaster master){
        this.master = master;
    }

    public List<Produto> getDescricaoProdutoDoCupomFiscal(String qrCode) throws IOException {
        List<Produto> produtos = new ArrayList<>();
        Document doc = getHtml(qrCode);
        Element prod = doc.getElementById("Prod");
        Elements cod_barras = prod.getElementsByClass("col-3");
        Elements descricoes = prod.getElementsByClass("fixo-prod-serv-descricao");
        List<Long> cod_barrasProdutos = getCodBarras(cod_barras);
        List<String> descricoesProdutos = getDescrição(descricoes);
        for (int i = 0; i<cod_barrasProdutos.size(); i++){
            Produto produt = new Produto();
            produt.setCod_barras(cod_barrasProdutos.get(i));
            produt.setDescricao(descricoesProdutos.get(i));
            produtos.add(produt);
        }
        return produtos;
    }
    //Retorna um Array com todos os produtos
    public List<Lista_de_produtos> getProdutosDoCupomFiscal(String qrCode) throws IOException {
        List<Lista_de_produtos> lista_de_produtos = new ArrayList<>();
        Document doc = getHtml(qrCode);
        Element prod = doc.getElementById("Prod");
        Elements cod_barras = prod.getElementsByClass("col-3");
        Elements quantidade = prod.getElementsByClass("fixo-prod-serv-qtd");
        Elements valor = prod.getElementsByClass("fixo-prod-serv-vb");
        Elements desconto = prod.getElementsByClass("toggable box");
        List<Long> cod_barrasProdutos = getCodBarras(cod_barras);
        List<Float> quantidadeProdutos = getQuantidade(quantidade);
        List<Float> valorProdutos = getValor(valor, desconto);
        for(int i = 0; i < cod_barrasProdutos.size(); i++){
            Lista_de_produtos lista = new Lista_de_produtos();
            lista.setCod_barras(cod_barrasProdutos.get(i));
            lista.setQuantidade(quantidadeProdutos.get(i));
            lista.setValor(valorProdutos.get(i));
            lista_de_produtos.add(lista);
        }
        return lista_de_produtos;
    }

    private List<String> getDescrição(Elements descricoes){
        descricoes.remove(0);
        List<String> produtos = new ArrayList<>();
        for (Element e : descricoes) {
            produtos.add(e.text());
        }
        return produtos;
    }

    private List<Long> getCodBarras(Elements cod_barras){
        List<Long> barras = new ArrayList<>();
        for (int i = 0; i<cod_barras.size(); i++) {
            String a = cod_barras.get(i).select("span").get(0).text();
            if(!a.matches("^[0-9]*$")){
                cod_barras.remove(i);
            }
        }
        for (Element i : cod_barras) {
            if(!i.select("span").get(0).text().isEmpty()){
                barras.add(Long.parseLong(i.select("span").get(0).text()));
            }
        }
        return barras;
    }

    private List<Float> getQuantidade(Elements qtd){
        qtd.remove(0);
        List<Float> quantidade = new ArrayList<>();
        for (Element e : qtd) {
            String s = e.text().replace(",", ".");
            quantidade.add(Float.parseFloat(s));
        }
        return quantidade;
    }
    private List<Float> getValor(Elements vlr, Elements desc){
        vlr.remove(0);
        List<Float> valor = new ArrayList<>();
        for (int i = 0; i<vlr.size(); i++) {
            String valorString = vlr.get(i).text().replace(",", ".");
            float valorProd = Float.parseFloat(valorString);
            float descontoProd = verificaDesconto(desc.get(i));
            valor.add(valorProd-descontoProd);
        }
        return valor;
    }

    private float verificaDesconto(Element desc){
        if(desc.select("span").get(6).text() == ""){
            return 0;
        }else{
            String descontoString = desc.select("span").get(6).text().replace(",", ".");
            return Float.parseFloat(descontoString);
        }
    }
    //Retorna o html contendo todos os dados da nota
    private Document getHtml(String qrcode) throws IOException {
        Connection.Response conexao = Jsoup.connect(qrcode).method(Connection.Method.GET).execute();
        Document doc = Jsoup.connect(qrcode).cookies(conexao.cookies()).get();
        String end_visualizar_abas = capturaHttp(doc);
        Document doc2 = Jsoup.connect(end_visualizar_abas).cookies(conexao.cookies()).get();
        return doc2;
    }

    //retorna o endereço do "Visualizar por abas" da nota
    private String capturaHttp(Document doc){
        Elements classe = doc.getElementsByClass("notprint");
        Element links = classe.select("a").first();
        return formataEndereco(links.attr("href"));
    }

    //Formata o endereço
    private String formataEndereco(String end){
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
