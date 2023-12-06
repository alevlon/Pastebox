package ua.alevlon.pastebox.repository;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PasteBoxEntity {
    private int id;
    private String data;
    private String hash;
    private LocalDateTime lifetime;
    private boolean isPublic;
}
