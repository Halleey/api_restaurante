package card.cardapio.dto.comments;

public class ComentarioRequestDto {

    private Long usuarioId;
    private String texto;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getTexto() {
        return texto;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public ComentarioRequestDto(Long usuarioId, String texto) {
        this.usuarioId = usuarioId;
        this.texto = texto;
    }
}
