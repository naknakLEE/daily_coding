package TDD.src;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Hashtable;


class Pair{
    private String from;
    private String to;

    Pair(String from, String to){
        this.from = from;
        this.to = to;
    }

    public boolean equals(Object object){
        Pair pair = (Pair) object;
        return from.equals(pair.from) && to.equals(pair.to);
    }

    public int hashCode(){
        return 0;
    }
}

interface Expression{
    Money reduce(Bank bank, String to);
}

class Bank{
    private Hashtable rates= new Hashtable();

    void addRate(String from, String to, int rate){
        rates.put(new Pair(from, to), new Integer(rate));
    }
    Money reduce(Expression source, String to){
        return source.reduce(this, to);
    }

    int rate(String from, String to){
        if (from.equals(to)) return 1;
        Integer rate=(Integer) rates.get(new Pair(from, to));
        return rate.intValue();
    }
}



class Sum implements Expression{
    Money augend;
    Money addend;
    Sum(Money augend, Money addend){
        this.augend = augend;
        this.addend = addend;
        
    }
    public Money reduce(Bank bank, String to){
        int amount = augend.amount + addend.amount;
        return new Money(amount, to);
    }
}

class Money implements Expression{
    protected String currency;
    protected int amount;

    Money(int amount, String currency){
        this.amount = amount;
        this.currency = currency;

    }

    public Money reduce(Bank bank, String to){
        int rate = bank.rate(currency, to);
        return new Money(amount/rate, to);
    }

    Money times(int multiplier){
        return new Money(amount * multiplier, currency);
    }

    String currency(){
        return currency;
    }

    Expression plus (Money addend) {
        return new Sum(this, addend);
    }

    static Money dollar(int amount){
        return new Money(amount, "USD");
    }

    static Money franc(int amount){
        return new Money(amount, "CHF");
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

    @Test
    public void testSimpleAddition(){
        // Money sum = Money.dollar(5).plus(Money.dollar(5));
        Money five = Money.dollar(5);
        Expression sum = five.plus(five);
        Bank bank = new Bank();
        Money reduced = bank.reduce(sum, "USD");
        assertEquals(Money.dollar(10), reduced);
    }

    @Test
    public void testPlusReturnsSum(){
        Money five = Money.dollar(5);
        Expression result = five.plus(five);
        Sum sum = (Sum) result;
        assertEquals(five, sum.augend);
        assertEquals(five, sum.addend);
    }

    @Test
    public void testReduceSum(){
        Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
        Bank bank = new Bank();
        Money result = bank.reduce(sum, "USD");
        assertEquals(Money.dollar(7), result);
    }

    @Test
    public void testReduceMoney(){
        Bank bank= new Bank();
        Money reeult = bank.reduce(Money.dollar(1), "USD");
        assertEquals(Money.dollar(1), reeult);
    }

    @Test
    public void testIdentityRaty(){
        assertEquals(1, new Bank().rate("USD", "USD"));
    }

    @Test
    public void testReduceMoneyDifferentCurrenty(){
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(Money.franc(2), "USD");
        assertEquals(Money.dollar(1), result);
    }

    @Test
    public void testArrayEquals(){
        assertEquals(new Object[] {"abc"}, new Object[] {"abc"});
    }
    public static void main(String[] args){
        System.out.println("hello world");
    }
}