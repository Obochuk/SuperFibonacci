import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;

/*F(2n) = F(n+1)^2 - F(n-1)^2
* F(2n+1) = F(n)^2 + F(n+1)^2*/

public class Fibonacci {

    private static Map<Integer, BigInteger> fibValues = new TreeMap<Integer, BigInteger>();

    public static BigInteger fib(BigInteger n) {
        int num = Math.abs(n.intValue());
        if (num == 0)
            return BigInteger.ZERO;
        boolean negate = (n.compareTo(BigInteger.ZERO) < 0 && n.mod(BigInteger.valueOf(2)) == BigInteger.ZERO);
        createTree(num);
        return (negate)? calculateTree(num).negate(): calculateTree(num);
    }

    private static void createTree(int n) {
        if (fibValues.containsKey(n))
            return;
        addElementsFor(n);
    }

    private static void addElementsFor(int n) {
        if (n == 0 || fibValues.containsKey(n))
            return;
        fibValues.put(n, null);
        int newN = n / 2;
        if (n % 2 == 0)
            addElementsForPair(newN + 1, newN - 1);
        else
            addElementsForPair(newN, newN + 1);
    }

    private static void addElementsForPair(int n1, int n2) {
        addElementsFor(n1);
        addElementsFor(n2);
    }

    private static BigInteger calculateTree(int n){
        for (Map.Entry<Integer, BigInteger> entry: fibValues.entrySet()){
            if (entry.getValue() == null)
                entry.setValue(calculateFib(entry));
        }
        return fibValues.get(n);
    }

    private static BigInteger calculateFib(Map.Entry<Integer, BigInteger> entry){
        int numOfFib = entry.getKey();
        if (numOfFib < 3)
            return BigInteger.ONE;
        int n = numOfFib / 2;
        if (numOfFib % 2 == 0)
            return fibValues.get(n + 1).pow(2).subtract(fibValues.get(n - 1).pow(2)); //F(2n) = F(n+1)^2 - F(n-1)^2
        else
            return fibValues.get(n).pow(2).add(fibValues.get(n + 1).pow(2)); //F(2n+1) = F(n)^2 + F(n+1)^2
    }
}
