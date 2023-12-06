package ua.alevlon.pastebox;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.alevlon.pastebox.api.response.PasteboxResponse;
import ua.alevlon.pastebox.exception.NotFoundEntityException;
import ua.alevlon.pastebox.repository.PasteBoxEntity;
import ua.alevlon.pastebox.repository.PasteboxRepository;
import ua.alevlon.pastebox.service.PasteboxService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PasteboxServiceTest {
    @Autowired
    private PasteboxService pasteboxService;

    @MockBean
    private PasteboxRepository pasteboxRepository;

    @Test
    public void notExistHash(){
        when(pasteboxRepository.getByHash(anyString())).thenThrow(NotFoundEntityException.class);
        assertThrows(NotFoundEntityException.class, () -> pasteboxService.getByHash("dslfkjlsdkjf"));
    }

    @Test
    public void getExistHash(){
        PasteBoxEntity entity = PasteBoxEntity.builder()
                .hash("1")
                .data("11")
                .isPublic(true)
                .build();


        when(pasteboxRepository.getByHash("1")).thenReturn(entity);

        PasteboxResponse expected = new PasteboxResponse("11", true);
        PasteboxResponse actual = pasteboxService.getByHash("1");

        assertEquals(expected, actual);
    }

}
