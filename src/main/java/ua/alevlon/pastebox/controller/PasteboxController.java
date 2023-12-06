package ua.alevlon.pastebox.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.alevlon.pastebox.api.request.PasteboxRequest;
import ua.alevlon.pastebox.api.response.PasteboxResponse;
import ua.alevlon.pastebox.api.response.PasteboxUrlResponse;
import ua.alevlon.pastebox.service.PasteboxService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class PasteboxController {
    private final PasteboxService pasteboxService;

    @GetMapping("/")
    public Collection<PasteboxResponse> getPublicPasteList() {
        return pasteboxService.getFirstPublicPasteboxes();
    }

    @GetMapping("/{hash}")
    public PasteboxResponse getByHash(@PathVariable String hash) {
        return pasteboxService.getByHash(hash);
    }

    @PostMapping("/")
    public PasteboxUrlResponse add(@RequestBody PasteboxRequest request){
        return pasteboxService.create(request);
    }
}
