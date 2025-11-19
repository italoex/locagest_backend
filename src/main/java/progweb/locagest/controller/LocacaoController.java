package progweb.locagest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// Adicione esta importação
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import progweb.locagest.dto.LocacaoRequestDTO;
import progweb.locagest.model.Locacao;
import progweb.locagest.service.LocacaoException;
import progweb.locagest.service.LocacaoService;

@RestController
@RequestMapping("/api/locacoes")
@CrossOrigin(origins = "*")
public class LocacaoController {

    @Autowired
    private LocacaoService locacaoService;

    @PostMapping("/iniciar")
    public ResponseEntity<?> iniciarLocacao(@RequestBody LocacaoRequestDTO dto) {
        try {
            Locacao novaLocacao = locacaoService.iniciarLocacao(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaLocacao);

        } catch (LocacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao processar a locação: " + e.getMessage());
        }
    }
}