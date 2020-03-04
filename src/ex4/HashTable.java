package ex4;

// Original source code: https://gist.github.com/amadamala/3cdd53cb5a6b1c1df540981ab0245479
// Modified by Fernando Porrino Serrano for academic purposes.

import java.util.ArrayList;

/**
 * Una hashTable se utiliza para acceder a los valores de dicha tabla  de una forma rapida y sencilla
 */

public class HashTable {
    private int INITIAL_SIZE = 16;
    private int size = 0;
    private HashEntry[] entries = new HashEntry[INITIAL_SIZE];

    /**
     * @return devuelve el tamaño de los valores añadidos a dicha tabla.
     */
    public int size(){
        return this.size;
    }

    /**
     * @return Devuelve el tamaño fijo de dicha tabla, en este caso siempre sera 16.
     */
    public int realSize(){
        return this.INITIAL_SIZE;
    }

    /**
     * @param key Genera una clave con su valor propio
     * @param value Esta variable sirve para asignarle un valor a una clave.
     * Este metodo put sirve para añadir nuevos elementos a la tabla de hash
     */
    public void put(String key, String value) {
        int hash = getHash(key);
        final HashEntry hashEntry = new HashEntry(key, value);

        if(entries[hash] == null) {
            entries[hash] = hashEntry;
            //Aumento del tamaño de los valores añadidos
            size++;
        }
        else {
            HashEntry temp = entries[hash];
            while(temp.next != null)
                temp = temp.next;

            temp.next = hashEntry;
            hashEntry.prev = temp;
        }
        //Incremento de la variable al añadir un elemento
        size++;
    }

    /**
     * @param key Devuelve el valor de una clave.
     * * Este metodo sirve para obtener el valor asociado a una clave en la tabla de hash.
     */
    public String get(String key) {
        int hash = getHash(key);
        if(entries[hash] != null) {
            HashEntry temp = entries[hash];

            while( !temp.key.equals(key))
                temp = temp.next;

            return temp.value;
        }

        return null;
    }

    /**
     * El método sirve para eliminar el elemento con el valor de una clave especificada de la tabla de hash.
     * @param key Se le asigna el valor de la clave que quieras eliminar.
     */
    public void drop(String key) {
        int hash = getHash(key);
        if(entries[hash] != null) {

            HashEntry temp = entries[hash];
            while( !temp.key.equals(key))
                temp = temp.next;

            if(temp.prev == null) entries[hash] = null;             //esborrar element únic (no col·lissió)
            else{
                if(temp.next != null) temp.next.prev = temp.prev;   //esborrem temp, per tant actualitzem l'anterior al següent
                temp.prev.next = temp.next;                         //esborrem temp, per tant actualitzem el següent de l'anterior
            }

            size--;
        }
    }

    /**
     * El método coge el valor del hash de la clave y hace el modulo entre el valor inicial de la tabla de hash (16) y lo retorna.
     */
    private int getHash(String key) {
        // piggy backing on java string
        // hashcode implementation.
        return key.hashCode() % INITIAL_SIZE;
    }

    /**
     * Esta clase contiene un contructor que guarda las variables valor y clave.
     */
    private class HashEntry {
        String key;
        String value;

        // Linked list of same hash entries.
        HashEntry next;
        HashEntry prev;

        public HashEntry(String key, String value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
        }

        /**
         * @return devuelve las claves y los valores creados.
         */
        @Override
        public String toString() {
            return "[" + key + ", " + value + "]";
        }
    }


    /**
     * @return devuelve la tabla creada por los valores y las claves.
     */
    @Override
    public String toString() {
        int bucket = 0;
        StringBuilder hashTableStr = new StringBuilder();
        for (HashEntry entry : entries) {
            if(entry == null) {
                bucket++;
                continue;
            }
            hashTableStr.append("\n bucket[")
                    .append(bucket)
                    .append("] = ")
                    .append(entry.toString());
            bucket++;
            HashEntry temp = entry.next;
            while(temp != null) {
                hashTableStr.append(" -> ");
                hashTableStr.append(temp.toString());
                temp = temp.next;
            }
        }
        return hashTableStr.toString();
    }

    public ArrayList<String> getCollisionsForKey(String key) {
        return getCollisionsForKey(key, 1);
    }

    /**
     * @param key
     * @param quantity
     * @return
     */
    public ArrayList<String> getCollisionsForKey(String key, int quantity){
        /*
          Main idea:
          alphabet = {0, 1, 2}
          Step 1: "000"
          Step 2: "001"
          Step 3: "002"
          Step 4: "010"
          Step 5: "011"
           ...
          Step N: "222"
          All those keys will be hashed and checking if collides with the given one.
        * */

        final char[] alphabet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        ArrayList<Integer> newKey = new ArrayList();
        ArrayList<String> foundKeys = new ArrayList();

        newKey.add(0);
        int collision = getHash(key);
        int current = newKey.size() -1;

        while (foundKeys.size() < quantity){
            //building current key
            String currentKey = "";
            for(int i = 0; i < newKey.size(); i++)
                currentKey += alphabet[newKey.get(i)];

            if(!currentKey.equals(key) && getHash(currentKey) == collision)
                foundKeys.add(currentKey);

            //increasing the current alphabet key
            newKey.set(current, newKey.get(current)+1);

            //overflow over the alphabet on current!
            if(newKey.get(current) == alphabet.length){
                int previous = current;
                do{
                    //increasing the previous to current alphabet key
                    previous--;
                    if(previous >= 0)  newKey.set(previous, newKey.get(previous) + 1);
                }
                while (previous >= 0 && newKey.get(previous) == alphabet.length);

                //cleaning
                for(int i = previous + 1; i < newKey.size(); i++)
                    newKey.set(i, 0);

                //increasing size on underflow over the key size
                if(previous < 0) newKey.add(0);

                current = newKey.size() -1;
            }
        }

        return  foundKeys;
    }

    /**
     * @param msg Variable de los mensajes creados
     * El metodo muestra los mensajes creados.
     */
    private static void log(String msg) {
        System.out.println(msg);
    }
}
