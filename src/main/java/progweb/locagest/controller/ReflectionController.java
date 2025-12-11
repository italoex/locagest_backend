package progweb.locagest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progweb.locagest.util.ReflectionValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller para demonstrar o uso de Reflexão.
 * Endpoints que validam objetos dinamicamente usando reflexão.
 */
@RestController
@RequestMapping("/api/reflexao")
public class ReflectionController {

    /**
     * Endpoint que valida qualquer objeto JSON usando reflexão.
     * Inspeciona os campos e retorna erros de validação.
     * 
     * Exemplo: POST /api/reflexao/validar
     * Body: { "nome": "", "email": "invalid" }
     */
    @PostMapping("/validar")
    public ResponseEntity<Map<String, Object>> validarObjeto(@RequestBody Map<String, Object> objeto) {
        Map<String, Object> response = new HashMap<>();
        response.put("objeto_recebido", objeto);
        response.put("mensagem", "Use POST /api/reflexao/validar-usuario ou /api/reflexao/validar-veiculo");
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint que demonstra validação com reflexão de um usuário.
     * Analisa os campos da classe e valida com base nas anotações.
     */
    @PostMapping("/info-classe")
    public ResponseEntity<Map<String, Object>> obterInfoClasse(
            @RequestParam String classe) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Usar reflexão para carregar a classe
            Class<?> clazz = Class.forName("progweb.locagest.model." + classe);
            Object instance = clazz.getDeclaredConstructor().newInstance();

            // Obter informações usando ReflectionValidator
            String info = ReflectionValidator.getClassInfo(instance);

            response.put("sucesso", true);
            response.put("classe", classe);
            response.put("info", info);
        } catch (ClassNotFoundException e) {
            response.put("sucesso", false);
            response.put("erro", "Classe não encontrada: " + classe);
        } catch (Exception e) {
            response.put("sucesso", false);
            response.put("erro", "Erro ao inspecionar classe: " + e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint educativo que explica como a reflexão funciona.
     */
    @GetMapping("/explicacao")
    public ResponseEntity<Map<String, Object>> explicarReflexao() {
        Map<String, Object> response = new HashMap<>();
        response.put("titulo", "Reflexão em Java");
        response.put("definicao",
                "Reflexão é a capacidade de inspecionar e modificar estruturas de programa em tempo de execução");
        response.put("usos", new String[] {
                "Inspecionar campos de uma classe dinamicamente",
                "Invocar métodos em tempo de execução",
                "Acessar informações sobre construtores, campos e métodos",
                "Criar instâncias de classes dinamicamente",
                "Validar anotações em campos"
        });
        response.put("implementacao_neste_projeto", new String[] {
                "ReflectionValidator.validate(obj) - valida campos com anotações",
                "ReflectionValidator.getClassInfo(obj) - retorna informações sobre os campos",
                "ReflectionController.obterInfoClasse() - usa Class.forName() para carregar classes dinamicamente"
        });
        return ResponseEntity.ok(response);
    }
}
