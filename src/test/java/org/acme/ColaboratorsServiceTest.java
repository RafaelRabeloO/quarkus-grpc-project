package org.acme;

import io.quarkus.grpc.GrpcService;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@QuarkusTest
class ColaboratorsServiceTest {

    @Inject
    @GrpcService
    ColaboratosService colaboratosService;


    @Test
    void testFindColaborator() {
        Uni<Colaborator> colaborator = colaboratosService.findColaborator(ColaboratorFind.newBuilder().setId("6419edd98b192788db47d59b").build());
        Colaborator colab = colaborator.await().indefinitely();
        Assertions.assertEquals("6419edd98b192788db47d59b", colab.getId());
        Assertions.assertEquals("Allison Crane", colab.getName());
        Assertions.assertEquals("Tue Mar 21 2023 14:48:09 GMT-0300 (Horário Padrão de Brasília)", colab.getBirthday());
        Assertions.assertEquals("São Gonçalo", colab.getCity());
        Assertions.assertEquals("District Of Columbia", colab.getState());
        Assertions.assertEquals("DataScience", colab.getTechStack());
        Assertions.assertEquals("Ritchie", colab.getMainTribe());
        Assertions.assertEquals("Banco", colab.getMainProject());
    }

}
