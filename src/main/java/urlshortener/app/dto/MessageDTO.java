package urlshortener.app.dto;

import lombok.Data;

@Data
public class MessageDTO {
    private String localURL;
    private String shortURL;
}
