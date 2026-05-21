# Matriz de Rastreabilidade de Requisitos (RTM)

| ID | Requisito Funcional | Caso de Teste Associado | Arquivo | Status |
|----|---------------------|--------------------------|---------|--------|
| RF01 | Cadastro de Livro | `shouldSaveBook` | BookRepositoryTest | OK |
| RF02 | Buscar Livro | `shouldFindBookById` | BookRepositoryTest | OK |
| RF03 | Validação de E-mail de Bruxo | `shouldValidateInvalidEmails` | WizardServiceParameterizedTest | OK |
| RF04 | Cadastro e Criação de Bruxos | `shouldCreateDifferentWizards` | WizardServiceParameterizedTest | OK |
| RF05 | Listagem e Acesso Seguro de Bruxos | `shouldAccessWizardsRoute` | WizardControllerTest | OK |
| RF06 | Integração de Endereço via ViaCEP | `deveConsultarCepComSucesso` | ViaCepVcrTest | OK |
| RF07 | Fluxo Completo de Web (Registro, Lista) | `fluxoCompletoWebWizard_DeveCadastrarListarEDeletar` | WizardE2ETest | OK |

---

## Diagrama de Sequência: Fluxo de Cadastro e Integração ViaCEP

Abaixo está o mapeamento do fluxo do RF06 e RF07, demonstrando a interação do usuário até a gravação no MongoDB após a consulta externa.

```mermaid
sequenceDiagram
    participant U as Usuário (Navegador)
    participant C as WizardController
    participant S as WizardService
    participant V as ViaCepService
    participant API as API ViaCEP
    participant DB as MongoDB (Testcontainers/Embed)
    
    U->>C: POST /wizards (name, email, cep)
    C->>S: saveWizard(wizard)
    
    Note over S: Validação Regex de E-mail
    
    S->>V: consultarCep(wizard.getCep())
    V->>API: GET /ws/{cep}/json/
    
    Note over V,API: Em testes, o VCR intercepta aqui e devolve o cassete JSON
    
    API-->>V: JSON Response (Logradouro, Bairro, UF)
    V-->>S: Dados mapeados (ViaCepResponse)
    
    S->>S: Popula entidade Wizard com endereço
    S->>DB: save(wizard)
    DB-->>S: Wizard salvo com ID
    
    S-->>C: Retorna controle
    C-->>U: 302 Redirect (/login)