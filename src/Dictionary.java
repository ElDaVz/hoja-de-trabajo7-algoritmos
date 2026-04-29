import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Dictionary {

    private final BinaryTree<Association<String, String>> tree;

    public Dictionary() {
        tree = new BinaryTree<>();
    }

    public static void main(String[] args) {

        String dictFile = "diccionario.txt";
        String textFile = "texto.txt";

        // Permitir pasar rutas por argumento
        if (args.length >= 2) {
            dictFile = args[0];
            textFile = args[1];
        } else if (args.length == 1) {
            dictFile = args[0];
        }

        Dictionary dict = new Dictionary();

        // Cargar diccionario
        try {
            dict.loadDictionary(dictFile);
            System.out.println("Diccionario cargado desde: " + dictFile);
        } catch (IOException e) {
            System.err.println("Error al leer el diccionario: " + e.getMessage());
            System.exit(1);
        }

        // Imprimir in-order
        dict.printInOrder();

        // Traducir texto
        try {
            dict.translateFile(textFile);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de texto: " + e.getMessage());
            System.exit(1);
        }
    }

    public void loadDictionary(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            // Parsear formato: (word, traduccion)
            Association<String, String> assoc = parseLine(line);
            if (assoc != null) {
                tree.insert(assoc);
            }
        }

        reader.close();
    }

    /**
     * Parsea una línea con formato (english, español) y devuelve una Association.
     * Retorna null si el formato es inválido.
     */
    private Association<String, String> parseLine(String line) {
        // Eliminar paréntesis externos
        if (!line.startsWith("(") || !line.endsWith(")")) {
            return null;
        }

        String content = line.substring(1, line.length() - 1); // quitar ( y )
        int commaIndex = content.indexOf(',');
        if (commaIndex == -1) {
            return null;
        }

        String english = content.substring(0, commaIndex).trim().toLowerCase();
        String spanish = content.substring(commaIndex + 1).trim();

        return new Association<>(english, spanish);
    }

    /**
     * Imprime todas las asociaciones en orden alfabético (in-order del BST).
     */
    public void printInOrder() {
        System.out.println("=== Diccionario (In-Order) ===");
        tree.inOrder();
    }


    public void translateFile(String filename) throws IOException {
        System.out.println("\n=== Traducción ===");

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        StringBuilder output = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            if (!output.isEmpty()) {
                output.append("\n");
            }
            output.append(translateLine(line));
        }

        reader.close();
        System.out.println(output);
    }

    /**
     * Traduce una línea de texto.
     * Cada token (palabra + posible puntuación final) es buscado en el BST.
     */
    private String translateLine(String line) {
        StringBuilder result = new StringBuilder();
        // Separar por espacios conservando tokens
        String[] tokens = line.split(" ");

        for (int i = 0; i < tokens.length; i++) {
            if (i > 0) result.append(" ");

            String token = tokens[i];
            // Separar puntuación al final (. , ! ? ;)
            String punctuation = "";
            String word = token;

            if (!token.isEmpty()) {
                char lastChar = token.charAt(token.length() - 1);
                if (!Character.isLetterOrDigit(lastChar)) {
                    punctuation = String.valueOf(lastChar);
                    word = token.substring(0, token.length() - 1);
                }
            }

            String translated = lookupWord(word);
            result.append(translated).append(punctuation);
        }

        return result.toString();
    }

    /**
     * Busca una palabra en el BST y retorna su traducción.
     * Si no se encuentra, retorna la palabra original entre asteriscos.
     *
     * @param word Palabra a buscar (puede ser mayúscula o minúscula)
     * @return Traducción o *word* si no existe
     */
    private String lookupWord(String word) {
        if (word.isEmpty()) return word;

        // Normalizar a minúsculas para la búsqueda (case-insensitive)
        String normalizedWord = word.toLowerCase();

        // Crear una association temporal solo con la clave para buscar
        Association<String, String> query = new Association<>(normalizedWord, "");
        Association<String, String> found = tree.search(query);

        if (found != null) {
            return found.getValue();
        } else {
            return "*" + word + "*";
        }
    }

}