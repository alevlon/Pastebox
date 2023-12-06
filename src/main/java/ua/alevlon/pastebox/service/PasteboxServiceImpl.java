package ua.alevlon.pastebox.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import ua.alevlon.pastebox.api.request.PasteboxRequest;
import ua.alevlon.pastebox.api.request.PublicStatus;
import ua.alevlon.pastebox.api.response.PasteboxResponse;
import ua.alevlon.pastebox.api.response.PasteboxUrlResponse;
import ua.alevlon.pastebox.repository.PasteBoxEntity;
import ua.alevlon.pastebox.repository.PasteboxRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Setter
@ConfigurationProperties(prefix = "app")
public class PasteboxServiceImpl implements PasteboxService {

    private String host;
    private int publicListSize;

    private final PasteboxRepository repository;
    private AtomicInteger idGenerator = new AtomicInteger(0);

    @Override
    public PasteboxResponse getByHash(String hash) {
        PasteBoxEntity pasteBoxEntity = repository.getByHash(hash);
        return new PasteboxResponse(pasteBoxEntity.getData(), pasteBoxEntity.isPublic());
    }

    @Override
    public List<PasteboxResponse> getFirstPublicPasteboxes() {

        List<PasteBoxEntity> list = repository.getListOfPublicAndAlive(publicListSize);

        return list.stream().map(pasteBoxEntity ->
                        new PasteboxResponse(pasteBoxEntity.getData(), pasteBoxEntity.isPublic()))
                .collect(Collectors.toList());
    }

    @Override
    public PasteboxUrlResponse create(PasteboxRequest request) {

        int hash = generateId();
        PasteBoxEntity pasteBoxEntity = PasteBoxEntity.builder()
                .data(request.getData())
                .id(hash)
                .hash(Integer.toHexString(hash))
                .isPublic(request.getPublicStatus() == PublicStatus.PUBLIC)
                .lifetime(LocalDateTime.now().plusSeconds(request.getExpirationTimeSeconds()))
                .build();

        repository.add(pasteBoxEntity);

        return new PasteboxUrlResponse(host.concat("/").concat(pasteBoxEntity.getHash()));
    }

    private int generateId() {
        return idGenerator.getAndIncrement();
    }
}
