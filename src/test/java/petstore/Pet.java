// 1 - Pacote
package petstore;

// 2 - Bibliotecas

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.contains;

// 3 - Classes
public class Pet {
    // 3.1 - Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; //endereço da entidade pet

    // 3.2 - Métodos e Funções
    public String lerJson(String caminhoJson) throws IOException {  //le o arquivo de onde ele vai tirar os dados
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Incluir - Create - Post

    // uma classe void(nao retorna nada) executa sem retorno
    @Test(priority = 1) //identifica o método ou função como um teste para o TestNG
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("dbJson/pet1.json"); //chama a função que vai ler, passando o caminho do arquivo

        // Sintaxe Gherkin
        // Dado - Quando - Então
        // Given - When - Then

        given() //dado - pré condição
                .contentType("application/json") //comum em API REST - antigas era "text/xml"
                .log().all() //pede pra logar - envio - ida
                .body(jsonBody) //fala a informação que vai ser transmitida
        .when() //quando
                .post(uri)
        .then() //entao
                .log().all() //volta
                .statusCode(200) //verificar se a transação foi e voltou - so significa que a mensagem foi e voltou
                .body("name",  is("Maggie"))
                .body("status", is("available"))
                .body("category.name", is("XEKAOI")) //quando tenho informação sem colchetes, eu posso utilizar o "is"
                .body("tags.name", contains("sta")) //se tiver informação dentro de colchetes, entao utilizar "contains"
        ;

    }
    @Test(priority = 2)
    public void consultarPet(){
        String petId = "3010199400";
        String token =

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is ("Maggie"))
                .body("category.name", is("XEKAOI"))
                .body("status", is("available"))
        .extract()
                .path("category.name")

        ;

        System.out.print("O token é " + token);

    }
    @Test(priority = 3)
    public void alterarPet() throws IOException {
        String jsonbody = lerJson("dbJson/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonbody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Maggie"))
                .body("status", is("sold"))
        ;
    }

}
