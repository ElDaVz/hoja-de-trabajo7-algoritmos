import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BinaryTreeTest {

    // =========================================================================
    // PRUEBAS PARA: insert
    // =========================================================================

    @Test
    @DisplayName("insert: árbol vacío antes de insertar")
    void testInsert_TreeIsEmptyInitially() {
        BinaryTree<Association<String, String>> tree = new BinaryTree<>();
        assertTrue(tree.isEmpty());
    }

    @Test
    @DisplayName("insert: árbol no está vacío después de insertar uno")
    void testInsert_OneElement_TreeNotEmpty() {
        BinaryTree<Association<String, String>> tree = new BinaryTree<>();
        tree.insert(new Association<>("house", "casa"));
        assertFalse(tree.isEmpty());
    }

    @Test
    @DisplayName("insert: raíz insertada es búscable y tiene valor correcto")
    void testInsert_RootIsSearchable() {
        BinaryTree<Association<String, String>> tree = new BinaryTree<>();
        tree.insert(new Association<>("house", "casa"));

        Association<String, String> found = tree.search(new Association<>("house", ""));
        assertNotNull(found);
        assertEquals("casa", found.getValue());
    }

    @Test
    @DisplayName("insert: múltiples elementos son encontrados")
    void testInsert_MultipleElements_AllFound() {
        BinaryTree<Association<String, String>> tree = new BinaryTree<>();
        tree.insert(new Association<>("house", "casa"));
        tree.insert(new Association<>("dog", "perro"));
        tree.insert(new Association<>("homework", "tarea"));
        tree.insert(new Association<>("woman", "mujer"));
        tree.insert(new Association<>("town", "pueblo"));
        tree.insert(new Association<>("yes", "sí"));

        assertNotNull(tree.search(new Association<>("house", "")));
        assertNotNull(tree.search(new Association<>("dog", "")));
        assertNotNull(tree.search(new Association<>("homework", "")));
        assertNotNull(tree.search(new Association<>("woman", "")));
        assertNotNull(tree.search(new Association<>("town", "")));
        assertNotNull(tree.search(new Association<>("yes", "")));
    }

    @Test
    @DisplayName("insert: duplicado no rompe el árbol ni sobreescribe")
    void testInsert_DuplicateKey_NotDuplicated() {
        BinaryTree<Association<String, String>> tree = new BinaryTree<>();
        tree.insert(new Association<>("dog", "perro"));
        tree.insert(new Association<>("dog", "can"));

        Association<String, String> found = tree.search(new Association<>("dog", ""));
        assertNotNull(found);
        assertEquals("perro", found.getValue());
    }

    @Test
    @DisplayName("insert: orden ascendente sigue siendo búscable")
    void testInsert_AscendingOrder_AllSearchable() {
        BinaryTree<Association<String, String>> tree = new BinaryTree<>();
        tree.insert(new Association<>("apple", "manzana"));
        tree.insert(new Association<>("banana", "plátano"));
        tree.insert(new Association<>("cherry", "cereza"));

        assertNotNull(tree.search(new Association<>("apple", "")));
        assertNotNull(tree.search(new Association<>("banana", "")));
        assertNotNull(tree.search(new Association<>("cherry", "")));
    }

    @Test
    @DisplayName("insert: orden descendente sigue siendo búscable")
    void testInsert_DescendingOrder_AllSearchable() {
        BinaryTree<Association<String, String>> tree = new BinaryTree<>();
        tree.insert(new Association<>("zebra", "cebra"));
        tree.insert(new Association<>("monkey", "mono"));
        tree.insert(new Association<>("ant", "hormiga"));

        assertNotNull(tree.search(new Association<>("zebra", "")));
        assertNotNull(tree.search(new Association<>("monkey", "")));
        assertNotNull(tree.search(new Association<>("ant", "")));
    }

    // =========================================================================
    // PRUEBAS PARA: search
    // =========================================================================

    @Test
    @DisplayName("search: árbol vacío devuelve null")
    void testSearch_EmptyTree_ReturnsNull() {
        BinaryTree<Association<String, String>> tree = new BinaryTree<>();
        Association<String, String> result = tree.search(new Association<>("house", ""));
        assertNull(result);
    }

    @Test
    @DisplayName("search: elemento existente devuelve valor correcto")
    void testSearch_ExistingElement_ReturnsCorrectValue() {
        BinaryTree<Association<String, String>> tree = new BinaryTree<>();
        tree.insert(new Association<>("town", "pueblo"));

        Association<String, String> result = tree.search(new Association<>("town", ""));
        assertNotNull(result);
        assertEquals("pueblo", result.getValue());
    }

    @Test
    @DisplayName("search: elemento no existente devuelve null")
    void testSearch_NonExistingElement_ReturnsNull() {
        BinaryTree<Association<String, String>> tree = new BinaryTree<>();
        tree.insert(new Association<>("house", "casa"));
        tree.insert(new Association<>("dog", "perro"));

        Association<String, String> result = tree.search(new Association<>("cat", ""));
        assertNull(result);
    }

    @Test
    @DisplayName("search: raíz encontrada correctamente")
    void testSearch_RootElement_Found() {
        BinaryTree<Association<String, String>> tree = new BinaryTree<>();
        tree.insert(new Association<>("house", "casa"));
        tree.insert(new Association<>("dog", "perro"));
        tree.insert(new Association<>("woman", "mujer"));

        Association<String, String> result = tree.search(new Association<>("house", ""));
        assertNotNull(result);
        assertEquals("casa", result.getValue());
    }

    @Test
    @DisplayName("search: elemento en subárbol izquierdo")
    void testSearch_LeftSubtreeElement_Found() {
        BinaryTree<Association<String, String>> tree = new BinaryTree<>();
        tree.insert(new Association<>("house", "casa"));
        tree.insert(new Association<>("dog", "perro"));

        Association<String, String> result = tree.search(new Association<>("dog", ""));
        assertNotNull(result);
        assertEquals("perro", result.getValue());
    }

    @Test
    @DisplayName("search: elemento en subárbol derecho")
    void testSearch_RightSubtreeElement_Found() {
        BinaryTree<Association<String, String>> tree = new BinaryTree<>();
        tree.insert(new Association<>("house", "casa"));
        tree.insert(new Association<>("woman", "mujer"));

        Association<String, String> result = tree.search(new Association<>("woman", ""));
        assertNotNull(result);
        assertEquals("mujer", result.getValue());
    }

    @Test
    @DisplayName("search: nodo hoja encontrado")
    void testSearch_LeafElement_Found() {
        BinaryTree<Association<String, String>> tree = new BinaryTree<>();
        tree.insert(new Association<>("house", "casa"));
        tree.insert(new Association<>("dog", "perro"));
        tree.insert(new Association<>("homework", "tarea"));

        Association<String, String> result = tree.search(new Association<>("homework", ""));
        assertNotNull(result);
        assertEquals("tarea", result.getValue());
    }

    @Test
    @DisplayName("search: case-sensitive no encuentra clave distinta")
    void testSearch_CaseSensitive_NotFound() {
        BinaryTree<Association<String, String>> tree = new BinaryTree<>();
        tree.insert(new Association<>("yes", "sí"));

        Association<String, String> result = tree.search(new Association<>("Yes", ""));
        assertNull(result);
    }

    @Test
    @DisplayName("search: clave en minúscula encontrada correctamente")
    void testSearch_LowercaseKey_Found() {
        BinaryTree<Association<String, String>> tree = new BinaryTree<>();
        tree.insert(new Association<>("yes", "sí"));

        Association<String, String> result = tree.search(new Association<>("yes", ""));
        assertNotNull(result);
        assertEquals("sí", result.getValue());
    }
}