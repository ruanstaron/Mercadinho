package tech.pucci.mercadinho.db;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Ruan on 14/01/2018.
 */

public class AtributosDao {

    public static void insereDados(Database db) {
        db.execSQL("insert or ignore into SITUACAO (_id, descricao) VALUES (1, \"ok\"), (2, \"divergencia\"), (3, \"compra\"), (4, \"comprado\");");
        db.execSQL("INSERT INTO Estado (descricao, sigla) VALUES ('Acre', 'AC'), ('Alagoas', 'AL'), ('Amazonas', 'AM'), ('Bahia', 'BA'), ('Ceará', 'CE'), ('Distrito Federal', 'DF'), ('Espírito Santo', 'ES'), ('Goiás', 'GO'), ('Maranhão', 'MA'), ('Mato Grosso', 'MG'), ('Mato Grosso do Sul', 'MS'), ('Minas Gerais', 'MG'), ('Pará', 'PA'), ('Paraíba', 'PB'), ('Paraná', 'PR'), ('Pernambuco', 'PE'), ('Piauí', 'PI'), ('Rio de Janeiro', 'RJ'), ('Rio Grande do Norte', 'RN'), ('Rio Grande do Sul', 'RS'), ('Rondônia', 'RO'), ('Roraima', 'RR'), ('Santa Catarina', 'SC'), ('São Paulo', 'SP'), ('Sergipe', 'SE'), ('Tocantins', 'TO');");
        db.execSQL("INSERT INTO Cidade (estado_id, descricao) VALUES (15, \"Abatiá\"),\n" +
                "(15, \"Adrianópolis\"),\n" +
                "(15, \"Agudos do Sul\"),\n" +
                "(15, \"Almirante Tamandaré\"),\n" +
                "(15, \"Altamira do Paraná\"),\n" +
                "(15, \"Alto Paraíso\"),\n" +
                "(15, \"Alto Paraná\"),\n" +
                "(15, \"Alto Piquiri\"),\n" +
                "(15, \"Altônia\"),\n" +
                "(15, \"Alvorada do Sul\"),\n" +
                "(15, \"Amaporã\"),\n" +
                "(15, \"Ampére\"),\n" +
                "(15, \"Anahy\"),\n" +
                "(15, \"Andirá\"),\n" +
                "(15, \"Ângulo\"),\n" +
                "(15, \"Antonina\"),\n" +
                "(15, \"Antônio Olinto\"),\n" +
                "(15, \"Apucarana\"),\n" +
                "(15, \"Arapongas\"),\n" +
                "(15, \"Arapoti\"),\n" +
                "(15, \"Arapuã\"),\n" +
                "(15, \"Araruna\"),\n" +
                "(15, \"Araucária\"),\n" +
                "(15, \"Ariranha do Ivaí\"),\n" +
                "(15, \"Assaí\"),\n" +
                "(15, \"Assis Chateaubriand\"),\n" +
                "(15, \"Astorga\"),\n" +
                "(15, \"Atalaia\"),\n" +
                "(15, \"Balsa Nova\"),\n" +
                "(15, \"Bandeirantes\"),\n" +
                "(15, \"Barbosa Ferraz\"),\n" +
                "(15, \"Barra do Jacaré\"),\n" +
                "(15, \"Barracão\"),\n" +
                "(15, \"Bela Vista da Caroba\"),\n" +
                "(15, \"Bela Vista do Paraíso\"),\n" +
                "(15, \"Bituruna\"),\n" +
                "(15, \"Boa Esperança\"),\n" +
                "(15, \"Boa Esperança do Iguaçu\"),\n" +
                "(15, \"Boa Ventura de São Roque\"),\n" +
                "(15, \"Boa Vista da Aparecida\"),\n" +
                "(15, \"Bocaiúva do Sul\"),\n" +
                "(15, \"Bom Jesus do Sul\"),\n" +
                "(15, \"Bom Sucesso\"),\n" +
                "(15, \"Bom Sucesso do Sul\"),\n" +
                "(15, \"Borrazópolis\"),\n" +
                "(15, \"Braganey\"),\n" +
                "(15, \"Brasilândia do Sul\"),\n" +
                "(15, \"Cafeara\"),\n" +
                "(15, \"Cafelândia\"),\n" +
                "(15, \"Cafezal do Sul\"),\n" +
                "(15, \"Califórnia\"),\n" +
                "(15, \"Cambará\"),\n" +
                "(15, \"Cambé\"),\n" +
                "(15, \"Cambira\"),\n" +
                "(15, \"Campina da Lagoa\"),\n" +
                "(15, \"Campina do Simão\"),\n" +
                "(15, \"Campina Grande do Sul\"),\n" +
                "(15, \"Campo Bonito\"),\n" +
                "(15, \"Campo do Tenente\"),\n" +
                "(15, \"Campo Largo\"),\n" +
                "(15, \"Campo Magro\"),\n" +
                "(15, \"Campo Mourão\"),\n" +
                "(15, \"Cândido de Abreu\"),\n" +
                "(15, \"Candói\"),\n" +
                "(15, \"Cantagalo\"),\n" +
                "(15, \"Capanema\"),\n" +
                "(15, \"Capitão Leônidas Marques\"),\n" +
                "(15, \"Carambeí\"),\n" +
                "(15, \"Carlópolis\"),\n" +
                "(15, \"Cascavel\"),\n" +
                "(15, \"Castro\"),\n" +
                "(15, \"Catanduvas\"),\n" +
                "(15, \"Centenário do Sul\"),\n" +
                "(15, \"Cerro Azul\"),\n" +
                "(15, \"Céu Azul\"),\n" +
                "(15, \"Chopinzinho\"),\n" +
                "(15, \"Cianorte\"),\n" +
                "(15, \"Cidade Gaúcha\"),\n" +
                "(15, \"Clevelândia\"),\n" +
                "(15, \"Colombo\"),\n" +
                "(15, \"Colorado\"),\n" +
                "(15, \"Congonhinhas\"),\n" +
                "(15, \"Conselheiro Mairinck\"),\n" +
                "(15, \"Contenda\"),\n" +
                "(15, \"Corbélia\"),\n" +
                "(15, \"Cornélio Procópio\"),\n" +
                "(15, \"Coronel Domingos Soares\"),\n" +
                "(15, \"Coronel Vivida\"),\n" +
                "(15, \"Corumbataí do Sul\"),\n" +
                "(15, \"Cruz Machado\"),\n" +
                "(15, \"Cruzeiro do Iguaçu\"),\n" +
                "(15, \"Cruzeiro do Oeste\"),\n" +
                "(15, \"Cruzeiro do Sul\"),\n" +
                "(15, \"Cruzmaltina\"),\n" +
                "(15, \"Curitiba\"),\n" +
                "(15, \"Curiúva\"),\n" +
                "(15, \"D[editar | editar código-fonte]\"),\n" +
                "(15, \"Diamante d'Oeste\"),\n" +
                "(15, \"Diamante do Norte\"),\n" +
                "(15, \"Diamante do Sul\"),\n" +
                "(15, \"Dois Vizinhos\"),\n" +
                "(15, \"Douradina\"),\n" +
                "(15, \"Doutor Camargo\"),\n" +
                "(15, \"Doutor Ulysses\"),\n" +
                "(15, \"Enéas Marques\"),\n" +
                "(15, \"Engenheiro Beltrão\"),\n" +
                "(15, \"Entre Rios do Oeste\"),\n" +
                "(15, \"Esperança Nova\"),\n" +
                "(15, \"Espigão Alto do Iguaçu\"),\n" +
                "(15, \"Farol\"),\n" +
                "(15, \"Faxinal\"),\n" +
                "(15, \"Fazenda Rio Grande\"),\n" +
                "(15, \"Fênix\"),\n" +
                "(15, \"Fernandes Pinheiro\"),\n" +
                "(15, \"Figueira\"),\n" +
                "(15, \"Flor da Serra do Sul\"),\n" +
                "(15, \"Floraí\"),\n" +
                "(15, \"Floresta\"),\n" +
                "(15, \"Florestópolis\"),\n" +
                "(15, \"Flórida\"),\n" +
                "(15, \"Formosa do Oeste\"),\n" +
                "(15, \"Foz do Iguaçu\"),\n" +
                "(15, \"Foz do Jordão\"),\n" +
                "(15, \"Francisco Alves\"),\n" +
                "(15, \"Francisco Beltrão\"),\n" +
                "(15, \"General Carneiro\"),\n" +
                "(15, \"Godoy Moreira\"),\n" +
                "(15, \"Goioerê\"),\n" +
                "(15, \"Goioxim\"),\n" +
                "(15, \"Grandes Rios\"),\n" +
                "(15, \"Guaíra\"),\n" +
                "(15, \"Guairaçá\"),\n" +
                "(15, \"Guamiranga\"),\n" +
                "(15, \"Guapirama\"),\n" +
                "(15, \"Guaporema\"),\n" +
                "(15, \"Guaraci\"),\n" +
                "(15, \"Guaraniaçu\"),\n" +
                "(15, \"Guarapuava\"),\n" +
                "(15, \"Guaraqueçaba\"),\n" +
                "(15, \"Guaratuba\"),\n" +
                "(15, \"Honório Serpa\"),\n" +
                "(15, \"Ibaiti\"),\n" +
                "(15, \"Ibema\"),\n" +
                "(15, \"Ibiporã\"),\n" +
                "(15, \"Icaraíma\"),\n" +
                "(15, \"Iguaraçu\"),\n" +
                "(15, \"Iguatu\"),\n" +
                "(15, \"Imbaú\"),\n" +
                "(15, \"Imbituva\"),\n" +
                "(15, \"Inácio Martins\"),\n" +
                "(15, \"Inajá\"),\n" +
                "(15, \"Indianópolis\"),\n" +
                "(15, \"Ipiranga\"),\n" +
                "(15, \"Iporã\"),\n" +
                "(15, \"Iracema do Oeste\"),\n" +
                "(15, \"Irati\"),\n" +
                "(15, \"Iretama\"),\n" +
                "(15, \"Itaguajé\"),\n" +
                "(15, \"Itaipulândia\"),\n" +
                "(15, \"Itambaracá\"),\n" +
                "(15, \"Itambé\"),\n" +
                "(15, \"Itapejara d'Oeste\"),\n" +
                "(15, \"Itaperuçu\"),\n" +
                "(15, \"Itaúna do Sul\"),\n" +
                "(15, \"Ivaí\"),\n" +
                "(15, \"Ivaiporã\"),\n" +
                "(15, \"Ivaté\"),\n" +
                "(15, \"Ivatuba\"),\n" +
                "(15, \"Jaboti\"),\n" +
                "(15, \"Jacarezinho\"),\n" +
                "(15, \"Jaguapitã\"),\n" +
                "(15, \"Jaguariaíva\"),\n" +
                "(15, \"Jandaia do Sul\"),\n" +
                "(15, \"Janiópolis\"),\n" +
                "(15, \"Japira\"),\n" +
                "(15, \"Japurá\"),\n" +
                "(15, \"Jardim Alegre\"),\n" +
                "(15, \"Jardim Olinda\"),\n" +
                "(15, \"Jataizinho\"),\n" +
                "(15, \"Jesuítas\"),\n" +
                "(15, \"Joaquim Távora\"),\n" +
                "(15, \"Jundiaí do Sul\"),\n" +
                "(15, \"Juranda\"),\n" +
                "(15, \"Jussara\"),\n" +
                "(15, \"Kaloré\"),\n" +
                "(15, \"Lapa\"),\n" +
                "(15, \"Laranjal\"),\n" +
                "(15, \"Laranjeiras do Sul\"),\n" +
                "(15, \"Leópolis\"),\n" +
                "(15, \"Lidianópolis\"),\n" +
                "(15, \"Lindoeste\"),\n" +
                "(15, \"Loanda\"),\n" +
                "(15, \"Lobato\"),\n" +
                "(15, \"Londrina\"),\n" +
                "(15, \"Luiziana\"),\n" +
                "(15, \"Lunardelli\"),\n" +
                "(15, \"Lupionópolis\"),\n" +
                "(15, \"Mallet\"),\n" +
                "(15, \"Mamborê\"),\n" +
                "(15, \"Mandaguaçu\"),\n" +
                "(15, \"Mandaguari\"),\n" +
                "(15, \"Mandirituba\"),\n" +
                "(15, \"Manfrinópolis\"),\n" +
                "(15, \"Mangueirinha\"),\n" +
                "(15, \"Manoel Ribas\"),\n" +
                "(15, \"Marechal Cândido Rondon\"),\n" +
                "(15, \"Maria Helena\"),\n" +
                "(15, \"Marialva\"),\n" +
                "(15, \"Marilândia do Sul\"),\n" +
                "(15, \"Marilena\"),\n" +
                "(15, \"Mariluz\"),\n" +
                "(15, \"Maringá\"),\n" +
                "(15, \"Mariópolis\"),\n" +
                "(15, \"Maripá\"),\n" +
                "(15, \"Marmeleiro\"),\n" +
                "(15, \"Marquinho\"),\n" +
                "(15, \"Marumbi\"),\n" +
                "(15, \"Matelândia\"),\n" +
                "(15, \"Matinhos\"),\n" +
                "(15, \"Mato Rico\"),\n" +
                "(15, \"Mauá da Serra\"),\n" +
                "(15, \"Medianeira\"),\n" +
                "(15, \"Mercedes\"),\n" +
                "(15, \"Mirador\"),\n" +
                "(15, \"Miraselva\"),\n" +
                "(15, \"Missal\"),\n" +
                "(15, \"Moreira Sales\"),\n" +
                "(15, \"Morretes\"),\n" +
                "(15, \"Munhoz de Melo\"),\n" +
                "(15, \"Nossa Senhora das Graças\"),\n" +
                "(15, \"Nova Aliança do Ivaí\"),\n" +
                "(15, \"Nova América da Colina\"),\n" +
                "(15, \"Nova Aurora\"),\n" +
                "(15, \"Nova Cantu\"),\n" +
                "(15, \"Nova Esperança\"),\n" +
                "(15, \"Nova Esperança do Sudoeste\"),\n" +
                "(15, \"Nova Fátima\"),\n" +
                "(15, \"Nova Laranjeiras\"),\n" +
                "(15, \"Nova Londrina\"),\n" +
                "(15, \"Nova Olímpia\"),\n" +
                "(15, \"Nova Prata do Iguaçu\"),\n" +
                "(15, \"Nova Santa Bárbara\"),\n" +
                "(15, \"Nova Santa Rosa\"),\n" +
                "(15, \"Nova Tebas\"),\n" +
                "(15, \"Novo Itacolomi\"),\n" +
                "(15, \"Ortigueira\"),\n" +
                "(15, \"Ourizona\"),\n" +
                "(15, \"Ouro Verde do Oeste\"),\n" +
                "(15, \"Paiçandu\"),\n" +
                "(15, \"Palmas\"),\n" +
                "(15, \"Palmeira\"),\n" +
                "(15, \"Palmital\"),\n" +
                "(15, \"Palotina\"),\n" +
                "(15, \"Paraíso do Norte\"),\n" +
                "(15, \"Paranacity\"),\n" +
                "(15, \"Paranaguá\"),\n" +
                "(15, \"Paranapoema\"),\n" +
                "(15, \"Paranavaí\"),\n" +
                "(15, \"Pato Bragado\"),\n" +
                "(15, \"Pato Branco\"),\n" +
                "(15, \"Paula Freitas\"),\n" +
                "(15, \"Paulo Frontin\"),\n" +
                "(15, \"Peabiru\"),\n" +
                "(15, \"Perobal\"),\n" +
                "(15, \"Pérola\"),\n" +
                "(15, \"Pérola d'Oeste\"),\n" +
                "(15, \"Piên\"),\n" +
                "(15, \"Pinhais\"),\n" +
                "(15, \"Pinhal de São Bento\"),\n" +
                "(15, \"Pinhalão\"),\n" +
                "(15, \"Pinhão\"),\n" +
                "(15, \"Piraí do Sul\"),\n" +
                "(15, \"Piraquara\"),\n" +
                "(15, \"Pitanga\"),\n" +
                "(15, \"Pitangueiras\"),\n" +
                "(15, \"Planaltina do Paraná\"),\n" +
                "(15, \"Planalto\"),\n" +
                "(15, \"Ponta Grossa\"),\n" +
                "(15, \"Pontal do Paraná\"),\n" +
                "(15, \"Porecatu\"),\n" +
                "(15, \"Porto Amazonas\"),\n" +
                "(15, \"Porto Barreiro\"),\n" +
                "(15, \"Porto Rico\"),\n" +
                "(15, \"Porto Vitória\"),\n" +
                "(15, \"Prado Ferreira\"),\n" +
                "(15, \"Pranchita\"),\n" +
                "(15, \"Presidente Castelo Branco\"),\n" +
                "(15, \"Primeiro de Maio\"),\n" +
                "(15, \"Prudentópolis\"),\n" +
                "(15, \"Quarto Centenário\"),\n" +
                "(15, \"Quatiguá\"),\n" +
                "(15, \"Quatro Barras\"),\n" +
                "(15, \"Quatro Pontes\"),\n" +
                "(15, \"Quedas do Iguaçu\"),\n" +
                "(15, \"Querência do Norte\"),\n" +
                "(15, \"Quinta do Sol\"),\n" +
                "(15, \"Quitandinha\"),\n" +
                "(15, \"Ramilândia\"),\n" +
                "(15, \"Rancho Alegre\"),\n" +
                "(15, \"Rancho Alegre d'Oeste\"),\n" +
                "(15, \"Realeza\"),\n" +
                "(15, \"Rebouças\"),\n" +
                "(15, \"Renascença\"),\n" +
                "(15, \"Reserva\"),\n" +
                "(15, \"Reserva do Iguaçu\"),\n" +
                "(15, \"Ribeirão Claro\"),\n" +
                "(15, \"Ribeirão do Pinhal\"),\n" +
                "(15, \"Rio Azul\"),\n" +
                "(15, \"Rio Bom\"),\n" +
                "(15, \"Rio Bonito do Iguaçu\"),\n" +
                "(15, \"Rio Branco do Ivaí\"),\n" +
                "(15, \"Rio Branco do Sul\"),\n" +
                "(15, \"Rio Negro\"),\n" +
                "(15, \"Rolândia\"),\n" +
                "(15, \"Roncador\"),\n" +
                "(15, \"Rondon\"),\n" +
                "(15, \"Rosário do Ivaí\"),\n" +
                "(15, \"Sabáudia\"),\n" +
                "(15, \"Salgado Filho\"),\n" +
                "(15, \"Salto do Itararé\"),\n" +
                "(15, \"Salto do Lontra\"),\n" +
                "(15, \"Santa Amélia\"),\n" +
                "(15, \"Santa Cecília do Pavão\"),\n" +
                "(15, \"Santa Cruz de Monte Castelo\"),\n" +
                "(15, \"Santa Fé\"),\n" +
                "(15, \"Santa Helena\"),\n" +
                "(15, \"Santa Inês\"),\n" +
                "(15, \"Santa Isabel do Ivaí\"),\n" +
                "(15, \"Santa Izabel do Oeste\"),\n" +
                "(15, \"Santa Lúcia\"),\n" +
                "(15, \"Santa Maria do Oeste\"),\n" +
                "(15, \"Santa Mariana\"),\n" +
                "(15, \"Santa Mônica\"),\n" +
                "(15, \"Santa Tereza do Oeste\"),\n" +
                "(15, \"Santa Terezinha de Itaipu\"),\n" +
                "(15, \"Santana do Itararé\"),\n" +
                "(15, \"Santo Antônio da Platina\"),\n" +
                "(15, \"Santo Antônio do Caiuá\"),\n" +
                "(15, \"Santo Antônio do Paraíso\"),\n" +
                "(15, \"Santo Antônio do Sudoeste\"),\n" +
                "(15, \"Santo Inácio\"),\n" +
                "(15, \"São Carlos do Ivaí\"),\n" +
                "(15, \"São Jerônimo da Serra\"),\n" +
                "(15, \"São João\"),\n" +
                "(15, \"São João do Caiuá\"),\n" +
                "(15, \"São João do Ivaí\"),\n" +
                "(15, \"São João do Triunfo\"),\n" +
                "(15, \"São Jorge d'Oeste\"),\n" +
                "(15, \"São Jorge do Ivaí\"),\n" +
                "(15, \"São Jorge do Patrocínio\"),\n" +
                "(15, \"São José da Boa Vista\"),\n" +
                "(15, \"São José das Palmeiras\"),\n" +
                "(15, \"São José dos Pinhais\"),\n" +
                "(15, \"São Manoel do Paraná\"),\n" +
                "(15, \"São Mateus do Sul\"),\n" +
                "(15, \"São Miguel do Iguaçu\"),\n" +
                "(15, \"São Pedro do Iguaçu\"),\n" +
                "(15, \"São Pedro do Ivaí\"),\n" +
                "(15, \"São Pedro do Paraná\"),\n" +
                "(15, \"São Sebastião da Amoreira\"),\n" +
                "(15, \"São Tomé\"),\n" +
                "(15, \"Sapopema\"),\n" +
                "(15, \"Sarandi\"),\n" +
                "(15, \"Saudade do Iguaçu\"),\n" +
                "(15, \"Sengés\"),\n" +
                "(15, \"Serranópolis do Iguaçu\"),\n" +
                "(15, \"Sertaneja\"),\n" +
                "(15, \"Sertanópolis\"),\n" +
                "(15, \"Siqueira Campos\"),\n" +
                "(15, \"Sulina\"),\n" +
                "(15, \"Tamarana\"),\n" +
                "(15, \"Tamboara\"),\n" +
                "(15, \"Tapejara\"),\n" +
                "(15, \"Tapira\"),\n" +
                "(15, \"Teixeira Soares\"),\n" +
                "(15, \"Telêmaco Borba\"),\n" +
                "(15, \"Terra Boa\"),\n" +
                "(15, \"Terra Rica\"),\n" +
                "(15, \"Terra Roxa\"),\n" +
                "(15, \"Tibagi\"),\n" +
                "(15, \"Tijucas do Sul\"),\n" +
                "(15, \"Toledo\"),\n" +
                "(15, \"Tomazina\"),\n" +
                "(15, \"Três Barras do Paraná\"),\n" +
                "(15, \"Tunas do Paraná\"),\n" +
                "(15, \"Tuneiras do Oeste\"),\n" +
                "(15, \"Tupãssi\"),\n" +
                "(15, \"Turvo\"),\n" +
                "(15, \"Ubiratã\"),\n" +
                "(15, \"Umuarama\"),\n" +
                "(15, \"União da Vitória\"),\n" +
                "(15, \"Uniflor\"),\n" +
                "(15, \"Uraí\"),\n" +
                "(15, \"Ventania\"),\n" +
                "(15, \"Vera Cruz do Oeste\"),\n" +
                "(15, \"Verê\"),\n" +
                "(15, \"Virmond\"),\n" +
                "(15, \"Vitorino\");");
    }
}
