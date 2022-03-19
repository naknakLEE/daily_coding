package TDD.src;

import org.junit.Test;
import static org.junit.Assert.*;

abstract class Money{
    abstract Money times(int multiplier);
    protected int amount;

    static Dollar dollar(int amount){
        return new Dollar(amount);
    }

    public boolean equals(Object object){
        Money money = (Money) object;
        return amount == money.amount
            && getClass().equals(money.getClass());
    }

}

class Dollar extends Money{
    Dollar(int amount){
        this.amount = amount;
    }
    Money times(int multiplier){
        return new Dollar(amount * multiplier);
    }
}

class Franc extends Money{
    Franc(int amount){
        this.amount = amount;
    }
    Money times(int multiplier){
        return new Franc(amount * multiplier);
    }
}
public class TDD {

    @Test
    public void testMultiplication() {
        Money five= Money.dollar(5);
        assertEquals(new Dollar(10), five.times(2));
        assertEquals(new Dollar(15), five.times(3));
        
    }

    @Test
    public void testFrancMultiplication() {
        Franc five= new Franc(5);
        assertEquals(new Franc(10), five.times(2));
        assertEquals(new Franc(15), five.times(3));
        
    }

    
    @Test
    public void testEquality(){
        assertTrue(new Dollar(5).equals(new Dollar(5)));
        assertFalse(new Dollar(5).equals(new Dollar(6)));
        assertTrue(new Franc(5).equals(new Franc(5)));
        assertFalse(new Franc(5).equals(new Franc(6)));
        assertFalse(new Franc(5).equals(new Dollar(5)));
    }

    public static void main(String[] args){
        System.out.println("hello world");
    }
}