package br.tabajara.organization.recomendation.service;

import br.tabajara.organization.recomendation.model.Curso;
import br.tabajara.organization.recomendation.model.Usuario;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileLoader {
    // Usar diretório temporário do sistema para armazenar os arquivos
    private static final Path DATA_DIR = Paths.get(System.getProperty("java.io.tmpdir"), "recomendacao");
    private static final Path COURSES_FILE = DATA_DIR.resolve("cursos.csv");
    private static final Path USERS_FILE = DATA_DIR.resolve("usuarios.csv");

    static {
        try {
            // Criar diretório de dados se não existir
            Files.createDirectories(DATA_DIR);

            // Criar arquivos se não existirem
            if (!Files.exists(COURSES_FILE)) {
                Files.createFile(COURSES_FILE);
            }
            if (!Files.exists(USERS_FILE)) {
                Files.createFile(USERS_FILE);
            }

            // Imprimir caminhos para debug
            System.out.println("Diretório de dados: " + DATA_DIR);
            System.out.println("Arquivo de cursos: " + COURSES_FILE);
            System.out.println("Arquivo de usuários: " + USERS_FILE);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao inicializar sistema de arquivos", e);
        }
    }

    public static List<Curso> carregarCursos() {
        List<Curso> cursos = new ArrayList<>();
        try {
            if (Files.size(COURSES_FILE) == 0) {
                // Se o arquivo estiver vazio, retorna lista vazia
                return cursos;
            }

            try (BufferedReader reader = Files.newBufferedReader(COURSES_FILE)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {  // Ignorar linhas vazias
                        String[] data = line.split(",");
                        if (data.length >= 2) {
                            cursos.add(new Curso(data[0].trim(), data[1].trim()));
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar cursos: " + e.getMessage());
        }
        return cursos;
    }

    public static List<Usuario> carregarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            if (Files.size(USERS_FILE) == 0) {
                return usuarios;
            }

            try (BufferedReader reader = Files.newBufferedReader(USERS_FILE)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        String[] data = line.split(";");
                        if (data.length >= 5) {
                            long id = Long.parseLong(data[0].trim());
                            String nome = data[1].trim();
                            String cargo = data[2].trim();
                            String[] interesses = data[3].split(",");
                            List<String> interessesList = new ArrayList<>();
                            for (String interesse : interesses) {
                                if (!interesse.trim().isEmpty()) {
                                    interessesList.add(interesse.trim());
                                }
                            }
                            String nivel = data[4].trim();
                            usuarios.add(new Usuario(id, nome, cargo, interessesList, nivel));
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar usuários: " + e.getMessage());
        }
        return usuarios;
    }

    public static void salvarCursos(List<Curso> cursos) {
        try {
            Files.createDirectories(COURSES_FILE.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(COURSES_FILE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                for (Curso curso : cursos) {
                    writer.write(curso.getNome() + "," + curso.getNivel());
                    writer.newLine();
                }
                writer.flush();
            }
            System.out.println("Cursos salvos com sucesso em: " + COURSES_FILE);
        } catch (IOException e) {
            System.err.println("Erro ao salvar cursos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void salvarUsuarios(List<Usuario> usuarios) {
        try {
            Files.createDirectories(USERS_FILE.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(USERS_FILE,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                for (Usuario usuario : usuarios) {
                    String interesses = String.join(",", usuario.getInteressesCursos());
                    writer.write(String.format("%d;%s;%s;%s;%s",
                            usuario.getId(),
                            usuario.getNome(),
                            usuario.getCargo(),
                            interesses,
                            usuario.getNivelPreferido()));
                    writer.newLine();
                }
                writer.flush();
            }
            System.out.println("Usuários salvos com sucesso em: " + USERS_FILE);
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuários: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void limparArquivos() {
        try {
            Files.deleteIfExists(COURSES_FILE);
            Files.deleteIfExists(USERS_FILE);
            Files.createFile(COURSES_FILE);
            Files.createFile(USERS_FILE);
        } catch (IOException e) {
            System.err.println("Erro ao limpar arquivos: " + e.getMessage());
        }
    }
}