package org.acme;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.Optional;
import javax.inject.Inject;

@GrpcService
public class ColaboratosService implements Colaborators {

    @Inject
    ColaboratorsRepo colaboratorsRepo;

    @Override
    public Uni<Colaborator> findColaborator(ColaboratorFind request) {
        Optional<Colaborator> colaborator = colaboratorsRepo.getColaborators().stream().filter(colab -> colab.getId().equals(request.getId())).findFirst();
        if (colaborator.isPresent()) {
            return Uni.createFrom().item(colaborator.get());
        } else {
            return Uni.createFrom().failure(new Throwable("Colaborator " + request.getId() + " not found."));
        }
    }

    @Override
    public Multi<Colaborator> findColaborators(Empty request) {
        return Multi.createFrom().iterable(colaboratorsRepo.getColaborators());
    }

    @Override
    public Uni<SaveStatus> saveColaborators(Multi<Colaborator> request) {
        request.log().onItem()
                .invoke(item -> System.out.println("Colaborator: " + item))
                .subscribe()
                .with(item -> colaboratorsRepo.saveColaborator(item));
        return null;
    }

    @Override
    public Multi<Colaborator> saveAndFindColaborators(Multi<Colaborator> request) {
        request.log().onItem()
                .invoke(item -> System.out.println("Colaborator: " + item))
                .subscribe()
                .with(item -> colaboratorsRepo.saveColaborator(item));
        return Multi.createFrom().iterable(colaboratorsRepo.getColaborators());
    }
}
