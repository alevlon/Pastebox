package ua.alevlon.pastebox.service;

import ua.alevlon.pastebox.api.request.PasteboxRequest;
import ua.alevlon.pastebox.api.response.PasteboxResponse;
import ua.alevlon.pastebox.api.response.PasteboxUrlResponse;

import java.util.List;

public interface PasteboxService {
    PasteboxResponse getByHash(String hash);
    List<PasteboxResponse> getFirstPublicPasteboxes();
    PasteboxUrlResponse create(PasteboxRequest request);
}
