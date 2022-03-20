package TDD.src;

import org.junit.Test;
import static org.junit.Assert.*;

class Money{
    protected String currency;
    protected int amount;

    Money(int amount, String currency){
        this.amount = amount;
        this.currency = currency;

    }

    Money times(int multiplier){
        return new Money(amount * multiplier, currency);
    }

    String currency(){
        return currency;
    }

    static Money dollar(int amount){
        return new Dollar(amount, "USD");
    }

    static Money franc(int amount){
        return new Franc(amount, "CHF");
    }

    public boolean equals(Object object){
        Money money = (Money) object;
        return amount == money.amount
            && currency().equals(money.currency());
    }

    public String toString(){
        return amount + "  " + currency;
    }

    

}

class Dollar extends Money{
    Dollar(int amount, String currency){
        super(amount, currency);
    }
    
}

class Franc extends Money{
    Franc(int amount, String currency){
        super(amount, currency);
    }
}
public class TDD {

    @Test
    public void testMultiplication() {
        Money five= Money.dollar(5);
        assertEquals(Money.dollar(10), five.times(2));
        assertEquals(Money.dollar(15), five.times(3));
        
    }

    @Test
    public void testFrancMultiplication() {
        Money five= Money.franc(5);
        assertEquals(Money.franc(10), five.times(2));
        assertEquals(Money.franc(15), five.times(3));
        
    }

    
    @Test
    public void testEquality(){
        assertTrue(Money.dollar(5).equals(Money.dollar(5)));
        assertFalse(Money.dollar(5).equals(Money.dollar(6)));
        assertTrue(Money.franc(5).equals(Money.franc(5)));
        assertFalse(Money.franc(5).equals(Money.franc(6)));
        assertFalse(Money.franc(5).equals(Money.dollar(5)));
    }

    @Test
    public void testCurrency(){
        assertEquals("USD", Money.dollar(1).currency());
        assertEquals("CHF", Money.franc(1).currency());
    }

    @Test
    public void testDifferentClassEquality(){
        assertTrue(new Money(10, "CHF").equals(
            new Franc(10, "CHF")
        ));
    }

    public static void main(String[] args){
        System.out.println("hello world");
    }
}