/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package List;

import invanders.Enemy;

/**
 *
 * @author yenma
 */
public class main {
    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        SimpleList lista = new SimpleList();
        
        System.out.println("<<-- Ejemplo de lista simple -->>\n");
        Enemy enemy = new Enemy(58,58,2,2,null);
        Enemy enemy1 = new Enemy(58,58,2,2,null);
        Enemy enemy2 = new Enemy(58,58,2,2,null);
        
 

        lista.add(enemy);
        lista.add(enemy1);
        lista.add(enemy2);
        System.out.println("<<-- Lista -->>");
       // lista.listar();
        
        System.out.println("\n\n<<-- Tamaño -->");
        System.out.println(lista.getSize());
        
    
        System.out.println("\nElimina el nodo en la posición 1");
        lista.getInPosition(1);        
        //lista.listar();
        System.out.print(" | Tamaño: ");
        System.out.println(lista.getSize());
        

        System.out.println("\nConsulta si la lista está vacia");
        System.out.println(lista.esVacia());
        
        System.out.println("\n\n<<-- Fin de ejemplo lista simple -->>");
    }   
}
