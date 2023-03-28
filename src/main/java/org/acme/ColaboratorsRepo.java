package org.acme;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ColaboratorsRepo {

    ObjectMapper objectMapper = new ObjectMapper();
    List<Colaborator> colaboratorsPack = new ArrayList<>();

    public List<Colaborator> getColaborators() {
        if (colaboratorsPack.isEmpty()) {
            try {
                File file = new File("/home/rafael/MyProject/quarkus-grpc-example/src/main/resources/Colaborators.json");
                CollectionType ct = objectMapper.getTypeFactory().constructCollectionType(List.class, ColaboratorClass.class);
                List<ColaboratorClass> colaborators = objectMapper.readValue(file, ct);
                return colaborators.stream().map(colab ->
                        Colaborator.newBuilder()
                                .setId(colab.id())
                                .setName(colab.name())
                                .setBirthday(colab.birthday())
                                .setCity(colab.city())
                                .setState(colab.state())
                                .setTechStack(colab.techStack())
                                .setMainTribe(colab.mainTribe())
                                .setMainProject(colab.mainProject())
                                .build()
                ).toList();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return colaboratorsPack;
    }

    public void saveColaborator(Colaborator colaborator) {
        colaboratorsPack.add(colaborator);
    }
}
