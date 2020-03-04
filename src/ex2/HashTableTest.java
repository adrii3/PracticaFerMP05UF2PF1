package ex2;


import ex1.HashTable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class HashTableTest {

    /*
    Casos:
        1. Añadir un elemento y comprobar que este añadido
        2. Añadir un elemento existente, si este ya existe unicamente actualizara su valor
        3. Añadimos un valor nuevo a mitad de tabla.
        4.Comprobamos si un segundo elemento de la linkedlist puede ser actualizado
     */
    @org.junit.jupiter.api.Test
    void put() {
        HashTable hashTable = new HashTable();
        //Al introducir un valor este deberia guardarse en la hashtable
        hashTable.put("1", "Paco");
        assertEquals(1, hashTable.size());
        System.out.println(hashTable);

        // Al introducir un nuevo valor en la misma key, este deberia reemplazarse por el valor nuevo.
        hashTable.put("1", "Mariano");
        assertEquals("Mariano", hashTable.get("1"));
        System.out.println(hashTable);

        // Vamos a proceder a insertar un valor a mitad de la tabla
        hashTable.put("8", "Flan");
        assertEquals("Flan", hashTable.get("8"));
        System.out.println(hashTable);

        //Vamos a forzar una colision para comprobar si el segundo elemento de la linkedlist se actualizaria
        hashTable.put("34","Letrero");
        System.out.println(hashTable);
        hashTable.put("34","Neymar");
        assertEquals("Neymar",hashTable.get("34"));
        System.out.println(hashTable);

    }

    /*
    Casos:
        1. Intentamos obtener un valor de la tabla de hash que no existe
        2. Intentamos obtener un elemento que no existe de la LinkedList.
        3. Obtenemos el valor existente de una determinada posicion.
        4. Obtenemos el valor que existe de la LinkedList.
     */
    @org.junit.jupiter.api.Test
    void get() {
        HashTable hashTable = new HashTable();
        // Intentamos coger un valor inexistente
        assertNull(hashTable.get("0"));
        System.out.println(hashTable);

        // Pedimos un elemento que no exista en la LinkedList
        assertNull(hashTable.get("58"));
        System.out.println(hashTable);
        // Pedimos un elemento existente
        hashTable.put("1","Paquito");
        assertEquals("Paquito", hashTable.get("1"));
        System.out.println(hashTable);
        // Pedimos un valor que ha colisionado y existe en la LinkedList
        hashTable.put("34","Willyrex");
        assertEquals("Willyrex", hashTable.get("34"));
        System.out.println(hashTable);
    }

    /*
    Casos:
        1. Borramos un valor inexistente y comprobamos que el tamaño de la tabla de hash se mantiene igual.
        2. Borramos un valor de la tabla.
        3. Borramos un valor de la LinkedList
        4.Borramos el primer valor existente en una linked list
     */
    @org.junit.jupiter.api.Test
    void drop() {
        HashTable hashTable = new HashTable();

        // Comprobamos que el tamaño no disminuyese al intentar eliminar un valor que nunca existió
        hashTable.put("1","Messi");
        hashTable.put("8","Tenedor");
        hashTable.drop("2");
        assertEquals(2, hashTable.size());
        System.out.println(hashTable);

        // Borramos un elemento la tabla
        hashTable.drop("8");
        assertEquals(1, hashTable.size());
        System.out.println(hashTable);

        //Borramos un valor existente en la linkedlist
        hashTable.put("34","DomiArmer");
        System.out.println(hashTable);
        hashTable.drop("34");
        assertNull(hashTable.get("34"));
        System.out.println(hashTable);

        //Borramos el primer valor que existe en una linked list
        hashTable.put("34","Persiana");
        System.out.println(hashTable);
        hashTable.drop("1");
        System.out.println(hashTable);


}
    /*
    Casos:
        1. Añadimos un valor para comprobar que no aumenta el tamaño real.
        2. Cogemos un valor de la tabla para comprobar que no aumenta el tamaño real.
        3.Colisionamos un valor para comprobar que no aumenta el tamaño real
        4. Actualizamos un valor para ver que no aumenta el realSize.
        5. Borramos un valor para ver que no aumenta el realSize.
     */
    @org.junit.jupiter.api.Test
    void realSize() {

        // 1. Añadimos un valor para comprobar que no aumenta el tamaño real.
        HashTable hashTable = new HashTable();
        hashTable.put("1", "Paco");
        assertEquals(16, hashTable.realSize());
        System.out.println(hashTable);

        // 2. Cogemos un valor de la tabla para comprobar que no aumenta el tamaño real.
        hashTable.get("1");
        assertEquals(16, hashTable.realSize());
        System.out.println( hashTable);

        //3.Colisionamos un valor para comprobar que no aumenta el tamaño real
        hashTable.put("34","Estefania");
        assertEquals(16, hashTable.realSize());
        System.out.println(hashTable);

        // 4. Actualizamos un valor para ver que no aumenta el realSize.
        hashTable.put("1","Merienda");
        assertEquals(16, hashTable.realSize());
        System.out.println( hashTable);

        // 5. Borramos un valor para ver que no aumenta el realSize.
        hashTable.drop("1");
        assertEquals(16, hashTable.realSize());
        System.out.println(hashTable);

    }
}