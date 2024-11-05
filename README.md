# Sistema de Recomendação de Cursos usando Apache Mahout

Sistema que gera trilhas de aprendizado personalizadas baseadas no perfil e interesses do usuário.

## Tecnologias Utilizadas

* Java 11
* Maven
* Apache Mahout
* SLF4J/Logback

## Funcionalidades

* Recomendação de cursos baseada em perfil
* Gestão de cursos e usuários
* Trilhas de aprendizado personalizadas
* Diferentes níveis: iniciante, intermediário e avançado

## Como Executar

```bash
# Compilar o projeto
mvn clean compile

# Executar
mvn exec:java
```

## Estrutura do Código

```
src/
├── main/
│   ├── java/
│   │   └── com/recomendacao/
│   │       ├── application/
│   │       │   └── Main.java
│   │       ├── controller/
│   │       │   └── RecomendacaoController.java
│   │       ├── model/
│   │       │   ├── Curso.java
│   │       │   └── Usuario.java
│   │       ├── service/
│   │       │   └── RecomendacaoService.java
│   │       ├── util/
│   │       │   └── FileLoader.java
│   │       └── view/
│   │           └── RecomendacaoView.java
│   └── resources/
       └── data/
           ├── cursos.csv
           └── usuarios.csv
```

## Uso do Sistema

### Adicionar Curso
```java
controller.adicionarCurso("JavaScript Fundamentos", "iniciante");
```

### Adicionar Usuário
```java
controller.adicionarUsuario(
    1L,
    "Marina Silva",
    "Desenvolvedora Frontend Júnior",
    Arrays.asList("JavaScript Fundamentos", "React.js Essencial"),
    "iniciante"
);
```

### Gerar Recomendações
```java
view.mostrarTrilha(userId, 3);
```

## Exemplos de Cursos

- JavaScript Fundamentos (iniciante)
- React.js Essencial (intermediário)
- Arquitetura de Microsserviços (avançado)
- Python para Data Science (intermediário)
- Java Spring Boot (intermediário)
- AWS Cloud Practitioner (iniciante)
- DevOps & CI/CD (avançado)
- SQL para Análise de Dados (iniciante)
- Machine Learning com Python (avançado)
- Docker Fundamentals (intermediário)

## Perfis de Usuários

1. **Desenvolvedor Frontend Júnior**
   - Nível: Iniciante
   - Interesses: JavaScript, React

2. **Desenvolvedor Backend Pleno**
   - Nível: Intermediário
   - Interesses: Java Spring, Docker

3. **Iniciante em Data Science**
   - Nível: Iniciante
   - Interesses: Python, SQL

4. **Especialista DevOps**
   - Nível: Avançado
   - Interesses: Docker, CI/CD

5. **Cientista de Dados**
   - Nível: Intermediário
   - Interesses: Machine Learning, Python

## Configuração

O sistema utiliza arquivos temporários em:
```
/tmp/recomendacao/
├── cursos.csv
├── usuarios.csv
└── preferencias.csv
```

## Formato dos Arquivos

### cursos.csv
```
nome_do_curso,nivel
```

### usuarios.csv
```
id;nome;cargo;interesses;nivel
```

## Saída Exemplo

```
=== Catálogo de Cursos ===
- JavaScript Fundamentos (iniciante)
- React.js Essencial (intermediario)
...

=== Recomendações Personalizadas ===
Recomendações para Marina Silva (Desenvolvedora Frontend Júnior):
- Node.js do Zero
- UX/UI Design
- React.js Essencial
```

## Melhorias Planejadas

- [ ] Interface web
- [ ] Persistência em banco de dados
- [ ] Mais critérios de recomendação
- [ ] API REST
- [ ] Sistema de avaliação
- [ ] Histórico de cursos

## Licença

MIT
