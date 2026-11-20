# Guia de publicação no GitHub

## O que foi feito nesta cópia

- Removidos `target/` (arquivos compilados), arquivos locais de IDE e arquivos ZIP pequenos de exportação de interface que estavam dentro dos recursos estáticos.
- Mantida a organização padrão Maven: `src/main/java` e `src/main/resources`.
- Criados `README.md`, `.gitignore`, `.env.example` e documentação de arquitetura.
- Configurações de RabbitMQ e chave JWT passaram a depender de variáveis de ambiente, em vez dos valores incluídos no material original.
- Preservadas as rotas, os nomes do código-fonte e os caminhos de imagens, para minimizar o risco de quebrar a interface.

## Antes de publicar

1. **Troque** quaisquer senhas e chaves usadas anteriormente, sobretudo se já apareceram em ZIPs, mensagens ou commits públicos.
2. Crie `.env` somente no seu computador. Nunca o envie ao GitHub.
3. Faça build e teste a aplicação em sua máquina: `docker compose up --build`.
4. Confirme login, cadastro, lista de cursos, ações de CRUD, monitoramento e imagens.
5. Não publique cadastros reais, informações pessoais ou bases de dados privadas.
6. Se o projeto já foi publicado com segredos, apenas removê-los de commits futuros não limpa o histórico; considere revogá-los e revisar o histórico do repositório.
7. O projeto foi feito em grupo: confirme com os colegas a publicação pública do código e dos créditos.

## Limitações de segurança observadas no código original

Estas questões **não foram modificadas nesta etapa**, que se concentrou em organização e documentação:

- A rota de monitoramento é pública, mas os eventos podem incluir endereços de e-mail.
- A política CORS está permissiva (`*`), devendo ser restringida para um domínio específico em implantação pública.
- Os eventos do monitoramento são mantidos em uma lista estática sem sincronização, o que pode causar problemas com requisições concorrentes.
- `monitor.js` insere eventos usando `innerHTML`, o que exige escape do conteúdo ou uso de `textContent` para evitar injeção de HTML.
- Há dados de sessão no `localStorage`; a estratégia de armazenamento do token exige avaliação para uso em produção.
- O H2 funciona em memória e os dados não são persistidos entre reinicializações.

## Publicação recomendada

- Nome do repositório: `conexao-edu`
- Descrição: `Plataforma educacional em Java e Spring Boot, com autenticação JWT, CRUD REST, RabbitMQ e Docker.`
- Tópicos: `java`, `spring-boot`, `rabbitmq`, `docker`, `rest-api`, `jwt`, `projeto-academico`
- Faça o commit inicial da versão organizada **sem** o arquivo `.env`.

**Obs.:** esta cópia não foi publicada automaticamente em sua conta GitHub.
